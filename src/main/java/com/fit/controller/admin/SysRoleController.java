package com.fit.controller.admin;

import com.fit.base.BaseController;
import com.fit.base.R;
import com.fit.config.security.utils.SecurityHelper;
import com.fit.entity.SysRole;
import com.fit.service.SysRoleService;
import com.fit.util.BeanUtils;
import com.fit.util.ConverterUtils;
import com.fit.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author AIM
 * @Des 角色管理
 * @DATE 2020/8/10
 */
@Controller
@RequestMapping("/admin/role")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService roleService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/list")
    public String roleList() {
        return "admin/sys/role/list";
    }

    /**
     * 用户列表
     */
    @PostMapping("/list")
    @ResponseBody
    public R roleList(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        List<SysRole> userList = roleService.findList(map);
        return R.tables(userList.size(), userList);
    }

    @GetMapping("/edit")
    public String roleEdit(Long id, Model model) {
        if (isNotEmpty(id)) {
            SysRole role = roleService.get(id);
            model.addAttribute("role", role);
        }
        return "admin/sys/role/edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public R roleEdit(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        try {
            SysRole role = BeanUtils.map2Bean(SysRole.class, map);
            if (isNotEmpty(role.getId())) {
                role.setUpdateTime(DateUtils.nowDate());
                role.setUpdateUser(SecurityHelper.getUserId());
                roleService.update(role);
            } else {
                role.setCreateTime(DateUtils.nowDate());
                role.setCreateUser(SecurityHelper.getUserId());
                roleService.save(role);
            }
            return R.success();
        } catch (Exception e) {
            return R.error("操作失败");
        }
    }

    /**
     * 删除
     *
     * @param ids 删除ID集合
     */
    @PostMapping("/del")
    @ResponseBody
    public Object del(String ids) {
        if (isNotEmpty(ids)) {
            List<String> dels = ConverterUtils.list(false, ids.split(","));
            ConverterUtils.listRemove(dels, "1");
            int size = dels.size();
            if (size > 0) {
                String[] strs = dels.toArray(new String[size]);
                this.roleService.batchDelete(strs);
                return R.success();
            } else {
                return R.error("含有不能删角色");
            }
        } else {
            return R.error("参数异常");
        }
    }

    /**
     * 修改状态
     */
    @RequestMapping("/setState")
    @ResponseBody
    public Object changeState(@RequestParam Long userId) {
        SysRole role = this.roleService.get(userId);
        if (role != null) {
            if (role.getEnabled()) {
                role.setEnabled(false);
            } else {
                role.setEnabled(true);
            }
            this.roleService.update(role);
            return R.success();
        }
        return R.error("修改状态失败");
    }

    @GetMapping("/power")
    public String roleAuth(Long id, Model model) {
        if (isNotEmpty(id)) {
            model.addAttribute("roleId", id);
            StringBuffer sb = new StringBuffer("SELECT MENU_ID FROM `sys_role_menu` WHERE `ROLE_ID` = ?");
            List<String> list = this.jdbcTemplate.queryForList(sb.toString(), new Object[]{id}, String.class);
            model.addAttribute("menuIds", ConverterUtils.join(",", list));
        }
        return "admin/sys/role/power";
    }

    /**
     * 保存
     */
    @PostMapping("/savePower")
    @ResponseBody
    public Object saveAssign(final String roleId, String menus) {
        try {
            StringBuffer sb = new StringBuffer("DELETE FROM `sys_role_menu` WHERE ROLE_ID = ?");
            this.jdbcTemplate.update(sb.toString(), new Object[]{roleId});
            sb.setLength(0);
            sb.append("INSERT INTO `sys_role_menu` (`MENU_ID`,`ROLE_ID`) VALUES (?,?)");
            final List<String> list = Arrays.asList(menus.split(","));
            this.jdbcTemplate.batchUpdate(sb.toString(), new BatchPreparedStatementSetter() {
                @Override
                public int getBatchSize() {
                    return list.size();
                }

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1, list.get(i));
                    ps.setObject(2, roleId);
                }
            });
            return R.success();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存权限失败");
        }
    }
}
