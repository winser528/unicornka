package com.fit.controller;

import com.fit.base.BaseController;
import com.fit.config.qr.QrImageTool;
import com.fit.entity.Coupons;
import com.fit.entity.Goods;
import com.fit.entity.Orders;
import com.fit.entity.Pays;
import com.fit.service.CouponsService;
import com.fit.service.GoodsService;
import com.fit.service.OrdersService;
import com.fit.service.PaysService;
import com.fit.util.BeanUtils;
import com.fit.util.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class OrderController extends BaseController {

    @Autowired
    private CouponsService couponService;
    @Autowired
    private GoodsService goodService;
    @Autowired
    private OrdersService orderService;
    @Autowired
    private PaysService payService;

    private Pattern pattern = Pattern.compile("\\[(.*?)\\]", Pattern.MULTILINE);

    @GetMapping("/bill")
    public String bill(HttpServletRequest request, Model model) {
        Map<String, Object> paramsMap = getRequestParamsMap(request);
        Orders bean = BeanUtils.map2Bean(Orders.class, paramsMap);
        Goods goods = this.goodService.get(bean.getGoodsId());
        Coupons coupons = this.couponService.get(bean.getCouponId());
        Pays pays = this.payService.get(bean.getPayId());
        model.addAttribute("order", bean);
        model.addAttribute("good", goods);
        model.addAttribute("coupon", coupons);
        model.addAttribute("pay", pays);
        model.addAttribute("info", pays);
        return "/bill";
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

    @GetMapping("/buy/{id}")
    public String buy(HttpServletRequest request, Model model, @PathVariable("id") Long id) {
        Map<String, Object> paramsMap = getRequestParamsMap(request);
        paramsMap.put("isOpen", 1);
        Goods goods = this.goodService.get(id);
        model.addAttribute("g", goods);
        List<Pays> pays = this.payService.findList(paramsMap);
        model.addAttribute("pays", pays);
        model.addAttribute("is_open_geetest", false);
        model.addAttribute("is_open_search_pwd", false);
        model.addAttribute("qrCode", false);
        return "/buy";
    }

    @GetMapping("/order_search")
    public String search(HttpServletRequest request, Model model) {
        Map<String, Object> paramsMap = getRequestParamsMap(request);
        model.addAttribute("is_open_search_pwd", false);
        return "/order_search";
    }

    @GetMapping("/order_info")
    public String info(HttpServletRequest request, Model model) {
        Map<String, Object> paramsMap = getRequestParamsMap(request);
        List<Orders> list = this.orderService.findList(paramsMap);
        for (Orders order : list) {
            order.setInfo(getInfo(order.getInfo()));
        }
        model.addAttribute("orders", list);
        paramsMap.clear();
        List<Pays> pays = this.payService.findList(paramsMap);
        model.addAttribute("pays", pays);
        return "/order_info";
    }

    @GetMapping("/qrpay")
    public String pay(HttpServletRequest request, Model model) {
        Map<String, Object> paramsMap = getRequestParamsMap(request);
        Pays pay = this.payService.get(ConverterUtils.toLong(paramsMap.get("payId"), 1));
        model.addAttribute("isMobile", isMobileDevice(request));
        model.addAttribute("payName", pay.getPayName());
        model.addAttribute("payTime", 5);
        model.addAttribute("actualPrice", ConverterUtils.toBigDecimal("10.01"));
        model.addAttribute("qrCode", QrImageTool.base64QrImage("10.01"));

        return "/qrpay";
    }

}
