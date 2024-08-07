package com.fit.controller;

import com.alibaba.fastjson.JSONArray;
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
import com.fit.util.FastJsonUtil;
import com.pay.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
public class OrderController extends BaseController {

    private Pattern pattern = Pattern.compile("\\[(.*?)\\]", Pattern.MULTILINE);
    private final String ORDER_KEY = "unicorn_orders";

    @Autowired
    private GoodsService goodService;
    @Autowired
    private OrdersService orderService;
    @Autowired
    private ZOrderService zOrderService;

    /**
     * 订单搜索页面
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/order_search")
    public String search(HttpServletRequest request, Model model) {
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
    @GetMapping("/order_info")
    public String info(HttpServletRequest request, Model model) {
        Map<String, Object> paramsMap = getRequestParamsMap(request);
        int page = ConverterUtils.toInt(paramsMap.get("page"), 1);
        Cookie[] cookies = request.getCookies();
        List<Orders> list = new ArrayList<>();
        JSONArray array = null;
        int count = 0;
        for (Cookie cookie : cookies) {
            if (ORDER_KEY.equals(cookie.getName())) {
                array = FastJsonUtil.jsonStrParseJsonArray(cookie.getValue());
            }
        }
        if (array != null) {
            list = this.zOrderService.getCookieOrder(array);
        } else {
            if (paramsMap.containsKey("email") || paramsMap.containsKey("orderSn")) {
                count = this.orderService.findCount(paramsMap);
                paramsMap.put("offset", (page - 1) * 2);
                paramsMap.put("limit", 2);
                list = this.orderService.findList(paramsMap);
                for (Orders order : list) {
                    order.setInfo(getInfo(order.getInfo()));
                }
            }
        }

        model.addAttribute("count", count);
        model.addAttribute("page", page);
        model.addAttribute("orders", list);
        model.addAttribute("pays", this.zOrderService.getPays());
        return "/order_info";
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
     * 下单支付页面
     */
    @PostMapping("/bill")
    public String bill(HttpServletRequest request, HttpServletResponse response, Model model) {
        Cookie[] cookies = request.getCookies();
        Map<String, Object> paramsMap = getRequestParamsMap(request);
        String inCoupons = paramsMap.get("coupon").toString();
        try {
            Orders bean = BeanUtils.map2Bean(Orders.class, paramsMap);
            bean.setBuyIp(HttpUtil.getRemoteIp(request));
            zOrderService.creatOrder(bean, inCoupons);
            // 设置cookie查询
            for (Cookie cookie : cookies) {
                if (ORDER_KEY.equals(cookie.getName())) {
                    JSONArray array = FastJsonUtil.jsonStrParseJsonArray(cookie.getValue());
                    array.add(bean.getOrderSn());
                    response.addCookie(new Cookie(ORDER_KEY, array.toJSONString()));
                } else {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.add(bean.getOrderSn());
                    response.addCookie(new Cookie(ORDER_KEY, jsonArray.toJSONString()));
                }
            }
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
        model.addAttribute("pays", this.zOrderService.getListPays());
        model.addAttribute("is_open_geetest", false);
        model.addAttribute("is_open_search_pwd", false);
        model.addAttribute("qrCode", false);
        return "/buy";
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
