package com.fit.controller.admin;

import com.fit.base.BaseController;
import com.fit.base.R;
import com.fit.config.security.utils.SecurityHelper;
import com.fit.entity.Orders;
import com.fit.entity.Pays;
import com.fit.service.OrdersService;
import com.fit.service.PaysService;
import com.fit.util.BeanUtils;
import com.fit.util.ConverterUtils;
import com.fit.util.DateUtils;
import com.fit.util.FastJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTO
 * @Author AIM
 * @DATE 2024/7/18
 */
@Slf4j
@Controller
@RequestMapping("/admin/order")
public class OrdersController extends BaseController {

    @Autowired
    private OrdersService service;
    @Autowired
    private PaysService pays;

    @GetMapping("/list")
    public String list(Model model) {
        Map<String, Object> map = new HashMap<>();
        map.put("isOpen", true);
        List<Pays> pays1 = pays.findList(map);
        map.clear();
        for (Pays pay : pays1) {
            map.put(pay.getId().toString(), pay.getPayName());
        }
        model.addAttribute("pays1", FastJsonUtil.toJSONString(map));
        return "admin/luna/order/list";
    }

    /**
     * 用户列表
     */
    @PostMapping("/list")
    @ResponseBody
    public R list(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        List<Orders> list = service.findList(map);
        return R.tables(list.size(), list);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        if (isNotEmpty(id)) {
            Orders bean = service.get(id);
            model.addAttribute("bean", bean);
            Pays pays = this.pays.get(bean.getPayId());
            model.addAttribute("payName", pays.getPayName());
        }
        return "admin/luna/order/info";
    }

    @PostMapping("/edit")
    @ResponseBody
    public R save(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        try {
            Orders bean = BeanUtils.map2Bean(Orders.class, map);
            if (isNotEmpty(bean.getId())) {
                bean.setUpdateTime(DateUtils.nowDate());
                bean.setUpdateUser(SecurityHelper.getUserId());
                service.update(bean);
            } else {
                bean.setCreateTime(DateUtils.nowDate());
                bean.setCreateUser(SecurityHelper.getUserId());
                service.save(bean);
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
                this.service.batchDelete(strs);
                return R.success();
            } else {
                return R.error("含有不能删角色");
            }
        } else {
            return R.error("参数异常");
        }
    }
}
