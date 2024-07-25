package com.fit.controller;

import com.fit.base.BaseController;
import com.fit.base.R;
import com.fit.config.SnowFlake;
import com.fit.config.qr.QrImageTool;
import com.fit.entity.Coupons;
import com.fit.entity.Goods;
import com.fit.entity.Orders;
import com.fit.entity.Pays;
import com.fit.service.*;
import com.fit.util.BeanUtils;
import com.fit.util.ConverterUtils;
import com.fit.util.DateUtils;
import com.pay.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
public class OrderController extends BaseController {

    @Autowired
    private GoodsService goodService;
    @Autowired
    private OrdersService orderService;
    @Autowired
    private ZOrderService zOrderService;

    private Pattern pattern = Pattern.compile("\\[(.*?)\\]", Pattern.MULTILINE);

    /**
     * 下单支付页面
     */
    @PostMapping("/bill")
    public String bill(HttpServletRequest request, Model model) {
        Map<String, Object> paramsMap = getRequestParamsMap(request);
        String inCoupons = paramsMap.get("coupon").toString();
        try {
            Orders bean = BeanUtils.map2Bean(Orders.class, paramsMap);
            bean.setBuyIp(HttpUtil.getRemoteIp(request));
            zOrderService.bill(bean, inCoupons);
            // 支付信息
            Pays pays = this.zOrderService.getPayById(bean.getPayId());
            model.addAttribute("order", bean);
            model.addAttribute("coupon", bean.getCouponId() != null ? inCoupons : "");
            model.addAttribute("payName", pays.getPayName());
            model.addAttribute("info", pays);
        } catch (Exception e) {
            log.error("下单异常", e);
            model.addAttribute("tips", "下单异常,请重新下单");
        }
        return "bill";
    }

    private String getInfo(String info) {
        StringBuffer result = new StringBuffer();
        if (isNotEmpty(info)) {
            Matcher matcher = pattern.matcher(info);
            while (matcher.find()) {
                String str = matcher.group(1);
                if (str != null && !str.isEmpty()) {
                    matcher.appendReplacement(result, "");
                }
            }
            matcher.appendTail(result);
        }
        return result.toString();
    }

    /**
     * 购买页面
     *
     * @param request
     * @param model
     * @param id      商品ID
     * @return
     */
    @GetMapping("/buy/{id}")
    public String buy(HttpServletRequest request, Model model, @PathVariable("id") Long id) {
        Map<String, Object> paramsMap = getRequestParamsMap(request);
        paramsMap.put("isOpen", 1);
        Goods goods = this.goodService.get(id);
        model.addAttribute("g", goods);
        model.addAttribute("pays", this.zOrderService.getPays());
        model.addAttribute("is_open_geetest", false);
        model.addAttribute("is_open_search_pwd", false);
        model.addAttribute("qrCode", false);
        return "/buy";
    }

    /**
     * 订单搜索页面
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/order_search")
    public String search(HttpServletRequest request, Model model) {
        Map<String, Object> paramsMap = getRequestParamsMap(request);
        model.addAttribute("is_open_search_pwd", false);
        return "/order_search";
    }

    /**
     * 订单详情页面
     *
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/order_info")
    public String info(HttpServletRequest request, Model model) {
        Map<String, Object> paramsMap = getRequestParamsMap(request);
        List<Orders> list = this.orderService.findList(paramsMap);
        for (Orders order : list) {
            order.setInfo(getInfo(order.getInfo()));
        }
        model.addAttribute("orders", list);
        paramsMap.clear();
        model.addAttribute("pays", this.zOrderService.getPays());
        return "/order_info";
    }

    /**
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/qrpay/{id}")
    public String pay(HttpServletRequest request, Model model, @PathVariable("id") Long id) {
        Orders orders = this.orderService.get(id);
        Pays pay = this.zOrderService.getPayById(orders.getPayId());
        model.addAttribute("isMobile", isMobileDevice(request));
        model.addAttribute("payName", pay.getPayName());
        model.addAttribute("payTime", 5);
        model.addAttribute("actualPrice", orders.getActualPrice());
        model.addAttribute("qrCode", QrImageTool.base64QrImage("orders.getActualPrice()"));

        return "/qrpay";
    }
}
