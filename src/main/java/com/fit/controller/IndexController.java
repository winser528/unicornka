package com.fit.controller;

import com.fit.base.BaseController;
import com.fit.entity.Goods;
import com.fit.entity.GoodsGroup;
import com.fit.service.GoodsGroupService;
import com.fit.service.GoodsService;
import com.fit.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @className: IndexController
 * @description: 首页
 * @author: Aim
 * @date: 2023/8/2
 **/
@Controller
public class IndexController extends BaseController {

    @Autowired
    private GoodsService goods;
    @Autowired
    private IndexService index;

    @GetMapping({"", "/", "/index"})
    public ModelAndView index(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        ModelAndView mv = new ModelAndView("index");
        List<Map<String, Object>> groups1 = index.getGroups();
        mv.addObject("groups1", groups1);
        List<Goods> goods1 = goods.findList(map);
        mv.addObject("goods1", goods1);
        return mv;
    }

    //    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        Map<String, Object> paramsMap = getRequestParamsMap(request);
        if (paramsMap.get("type") != null) {
            model.addAttribute("tip", "账户或密码错误!");
        }
        return "/login";
    }
}
