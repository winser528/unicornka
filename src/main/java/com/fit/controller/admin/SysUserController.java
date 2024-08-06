package com.fit.controller.admin;

import com.fit.base.BaseController;
import com.fit.base.R;
import com.fit.config.security.utils.SecurityHelper;
import com.fit.entity.SysUser;
import com.fit.service.SysUserService;
import com.fit.util.BeanUtils;
import com.fit.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @Author AIM
 * @Des 用户管理
 * @DATE 2020/8/10
 */
@Controller
@RequestMapping("/admin/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService userService;

    @GetMapping("/list")
    public String userList() {
        return "admin/sys/user/list";
    }

    /**
     * 用户列表
     */
    @PostMapping("/list")
    @ResponseBody
    public R userList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = getRequestParamsMap(request);
        Object keywords = map.get("keywords");
        if (isNotEmpty(keywords)) {
            map.remove("keywords");
            if (keywords.toString().contains("@")) {
                map.put("email", keywords);
            } else {
                map.put("name", keywords);
            }
        }
        List<SysUser> userList = userService.findList(map);
        return R.tables(userList.size(), userList);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        if (isNotEmpty(id)) {
            SysUser user = userService.get(id);
            model.addAttribute("user", user);
        }
        return "admin/sys/user/edit";
    }

    @PostMapping("/save")
    @ResponseBody
    public R save(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        try {
            SysUser sysUser = BeanUtils.map2Bean(SysUser.class, map);
            if (isNotEmpty(sysUser.getId())) {
                sysUser.setUpdateUser(SecurityHelper.getUserId());
                sysUser.setUpdateTime(DateUtils.nowDate());
                userService.update(sysUser);
            } else {
                sysUser.setCreateUser(SecurityHelper.getUserId());
                sysUser.setCreateTime(DateUtils.nowDate());
                userService.save(sysUser);
            }
            return R.success();
        } catch (Exception e) {
            return R.error();
        }
    }

    @PostMapping("/del")
    @ResponseBody
    public Object userDel(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        SysUser sysUser = BeanUtils.map2Bean(SysUser.class, map);
        int delete = userService.delete(sysUser.getId());
        if (delete > 0) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    /**
     * 详情页面
     */
    @RequestMapping("/detail")
    public String detail(Long id, Model model) {
        if (isNotEmpty(id)) {
            SysUser sysUser = userService.get(id);
            model.addAttribute("user", sysUser);
        }
        return "admin/sys/user/info";
    }

    /**
     * 修改状态
     */
    @RequestMapping("/setState")
    @ResponseBody
    public Object changeState(@RequestParam Long userId) {
        SysUser sysUser = this.userService.get(userId);
        if (sysUser != null) {
            if (sysUser.getEnabled()) {
                sysUser.setEnabled(false);
            } else {
                sysUser.setEnabled(true);
            }
            this.userService.update(sysUser);
            return R.success();
        }
        return R.error("修改失败");
    }

    /**
     * 重置用户的密码
     */
    @RequestMapping("/reset")
    @ResponseBody
    public Object reset(@RequestParam Long userId) throws UnsupportedEncodingException {
        SysUser user = this.userService.get(userId);
        user.setPassword(DigestUtils.md5DigestAsHex("111111".getBytes("UTF-8")));
        this.userService.update(user);
        return R.success();
    }

    /**
     * 设置角色页面
     */
    @RequestMapping("/setRole")
    public String setRoleView(@RequestParam Long id, Model model) {
        SysUser sysUser = userService.get(id);
        model.addAttribute("roleId", sysUser);
        model.addAttribute("userId", id);
        return "admin/sys/user/setRole";
    }

    /**
     * 修改角色
     */
    @RequestMapping("/authRole")
    @ResponseBody
    public Object changeFreeze(@RequestParam Long userId, @RequestParam Long roleId) {
        SysUser sysUser = this.userService.get(userId);
        if (sysUser != null) {
            sysUser.setRoleId(roleId);
            this.userService.update(sysUser);
            return R.success();
        }
        return R.error("修改角色失败");
    }
}
