package com.fit.controller.admin;

import com.fit.base.BaseController;
import com.fit.base.PatternAndView;
import com.fit.base.R;
import com.fit.entity.ZMenuNode;
import com.fit.entity.ZTreeNode;
import com.fit.service.ZMenuNodeService;
import com.fit.service.ZtreeNodeService;
import com.fit.util.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @AUTO 后台页面控制器
 * @Author AIM
 * @DATE 2018/10/22
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class SysAdminController extends BaseController {

    @Autowired
    private ZMenuNodeService menuService;
    @Autowired
    private ZtreeNodeService ztreeNodeService;

    @GetMapping(value = {"", "/", "/index"})
    public String admin(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/admin/login?error=true";
        } else {
            List<Long> roles = menuService.getRoles(user.getUsername());
            List<ZMenuNode> menus = menuService.getUserMenuNodes(roles);
            model.addAttribute("menus", menus);
            model.addAttribute("username", user.getUsername());
            return "admin/index";
        }
    }

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request, Model model) {
        model.addAttribute("os", SystemUtil.getOsInfo());
        model.addAttribute("port", Integer.valueOf(request.getServerPort()));
        return "admin/welcome";
    }

    /**
     * @return 返回登陆页
     */
    @GetMapping(value = {"/login", "/login.html"})
    public ModelAndView login(HttpServletRequest request) {
        PatternAndView view = new PatternAndView("/admin/login");
        Map<String, Object> paramsMap = getRequestParamsMap(request);
        if (paramsMap.get("type") != null) {
            view.addObject("tips", "账户或密码错误!");
        } else {
            view.addObject("tips", "");
        }
        return view;
    }

    /**
     * @return 退出, 跳转到登陆页面
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/admin/login";
    }

    /**
     * 获取部门树
     */
    @RequestMapping(value = "/dept/tree")
    @ResponseBody
    public R treeDept() {
        List<ZTreeNode> tree = this.ztreeNodeService.deptZtree();
        tree.add(ZTreeNode.createParent());
        return R.tree(200, "获取部门成功", tree);
    }

    /**
     * 获取角色树
     */
    @RequestMapping(value = "/role/tree")
    @ResponseBody
    public R treeRole() {
        List<ZTreeNode> tree = this.ztreeNodeService.roleZtree();
        tree.add(ZTreeNode.createParent());
        return R.tree(200, "获取角色成功", tree);
    }

    /**
     * 获取菜单树
     */
    @RequestMapping(value = "/menu/tree")
    @ResponseBody
    public R treeMenu() {
        List<ZTreeNode> tree = this.ztreeNodeService.menuZtree();
        tree.add(ZTreeNode.createParent());
        return R.tree(200, "获取菜单成功", tree);
    }
}
