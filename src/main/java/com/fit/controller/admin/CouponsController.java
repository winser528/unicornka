package com.fit.controller.admin;

import com.fit.base.BaseController;
import com.fit.base.R;
import com.fit.config.security.utils.SecurityHelper;
import com.fit.entity.Coupons;
import com.fit.service.CouponsService;
import com.fit.util.BeanUtils;
import com.fit.util.ConverterUtils;
import com.fit.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @AUTO 优惠管理控制器
 * @Author AIM
 * @DATE 2024/7/18
 */
@Slf4j
@Controller
@RequestMapping("/admin/coupon")
public class CouponsController extends BaseController {
    @Autowired
    private CouponsService service;

    @GetMapping("/list")
    public String list() {
        return "admin/luna/coupon/list";
    }

    /**
     * 用户列表
     */
    @PostMapping("/list")
    @ResponseBody
    public R list(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        List<Coupons> list = service.findList(map);
        return R.tables(list.size(), list);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        if (isNotEmpty(id)) {
            Coupons bean = service.get(id);
            model.addAttribute("bean", bean);
        }
        return "admin/luna/coupon/edit";
    }

    @PostMapping("/save")
    @ResponseBody
    public R save(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        try {
            Coupons bean = BeanUtils.map2Bean(Coupons.class, map);
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
