package com.fit.controller.admin;

import com.fit.base.BaseController;
import com.fit.base.R;
import com.fit.config.security.utils.SecurityHelper;
import com.fit.entity.SysMenu;
import com.fit.service.SysMenuService;
import com.fit.util.BeanUtils;
import com.fit.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author AIM
 * @Des 资源控制器
 * @DATE 2020/8/13
 */
@Controller
@RequestMapping("/admin/menu")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService service;

    @GetMapping("/list")
    public String menuList() {
        return "admin/sys/menu/list";
    }

    /**
     * 资源列表
     */
    @PostMapping("/list")
    @ResponseBody
    public R menuList(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        List<SysMenu> list = service.findList(map);
//        int count = service.findCount(map);
//        MenuNode tree = BeanUtils.buildTree(list, SysMenu.class);
        return R.tables(list.size(), list);
    }

    @GetMapping("/edit")
    public String menuEdit(Long id, Model model) {
        if (isNotEmpty(id)) {
            SysMenu menu = service.get(id);
            model.addAttribute("menu", menu);
        }
        return "admin/sys/menu/edit";
    }

    @PostMapping("/save")
    @ResponseBody
    public R save(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        try {
            SysMenu menu = BeanUtils.map2Bean(SysMenu.class, map);
            if (isNotEmpty(menu.getId())) {
                menu.setUpdateTime(DateUtils.nowDate());
                menu.setUpdateUser(SecurityHelper.getUserId());
                service.update(menu);
            } else {
                menu.setCreateTime(DateUtils.nowDate());
                menu.setCreateUser(SecurityHelper.getUserId());
                service.save(menu);
            }
            return R.success();
        } catch (Exception e) {
            return R.error();
        }
    }

    /**
     * 修改状态
     */
    @RequestMapping("/setState")
    @ResponseBody
    public R changeState(@RequestParam Long id) {
        SysMenu menu = this.service.get(id);
        if (menu != null) {
            if (menu.getEnabled()) {
                menu.setEnabled(false);
            } else {
                menu.setEnabled(true);
            }
            this.service.update(menu);
            return R.success();
        }
        return R.error("修改状态失败");
    }
}
