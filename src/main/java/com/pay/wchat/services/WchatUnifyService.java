package com.pay.wchat.services;

import java.util.HashMap;
import java.util.Map;

import com.pay.utils.*;
import com.pay.wchat.bean.OrderReturnDTO;
import com.pay.wchat.bean.UnifyReqDTO;
import com.pay.wchat.config.Configure;

/**
 * @TITLE WchatReqService.java
 * @NAME 微信生成支付服务
 * @DATE 2017-1-16
 */
public class WchatUnifyService {

    private static boolean isNotNull(Object s) {
        return (s != null) && (!s.toString().equals(""));
    }

    /**
     * 发送请求获得结果
     *
     * @param out_trade_no
     * @param xml
     */
    private static OrderReturnDTO getOrderRet(String out_trade_no, String xml) {
        OrderReturnDTO orderRet = null;
        try {
            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
            String returnXml = HttpUtil.sendPost(url, xml);
            LogProUtils.debug("微信统一支付，统一下单返回的Xml信息：==>" + returnXml);
            orderRet = SignUtil.parseXMLToObject(returnXml, OrderReturnDTO.class);
            orderRet.setOut_trade_no(out_trade_no);
            LogProUtils.debug("微信统一支付，返回结果：=>" + orderRet.toString());
        } catch (Exception e) {
            LogProUtils.error("发送请求获得结果:异常\n" + e);
        }

        return orderRet;
    }

    /**
     * @param body        弹出显示内容
     * @param total_fee   金额显示到分
     * @param orderFormId 订单ID
     * @param trade_type  下单类型:JSAPI，NATIVE，APP
     * @throws Exception
     * @统一支付 回调地址
     */
    public static OrderReturnDTO unifyPay(String body, int total_fee, String orderFormId, String trade_type) throws Exception {
        if (!isNotNull(body)) {
            body = "您购买的物品需支付的金额";
        }
        // 读取配置文件
        String notify_url = Configure.getNotifyUrl();// 回调地址
        String out_trade_no = orderFormId;
        // 下单信息
        UnifyReqDTO o = new UnifyReqDTO(body, out_trade_no, total_fee, notify_url, trade_type);
        // 转换为XML
        String xml = HttpsUtil.getReqDateXml(o);
        // 统一下单
        OrderReturnDTO orderRet = getOrderRet(out_trade_no, xml);
        return orderRet;
    }

    /**
     * @param appid       商户APPID
     * @param mch_id      商户号
     * @param key         商户密钥
     * @param body        弹出显示内容
     * @param total_fee   金额显示到分
     * @param orderFormId 订单ID
     * @param trade_type  下单类型:JSAPI，NATIVE，APP
     * @普通商户统一下单
     */
    public static OrderReturnDTO unifiedNormalPay(String appid, String mch_id, String key, String body, int total_fee,
                                                  String orderFormId, String trade_type) throws Exception {
        if (!isNotNull(body)) {
            body = "您购买的物品需支付的金额";
        }
        // 读取配置文件
        String notify_url = Configure.getNotifyUrl();// 回调地址
        String out_trade_no = orderFormId;
        // 下单信息
        UnifyReqDTO o = new UnifyReqDTO(appid, mch_id, key, body, out_trade_no, total_fee, notify_url, trade_type);
        // 转换为XML
        String xml = HttpsUtil.getReqDateXml(o);
        // 统一下单
        OrderReturnDTO orderRet = getOrderRet(out_trade_no, xml);
        return orderRet;
    }

    /**
     * @param body        弹出显示内容
     * @param total_fee   金额显示到分
     * @param orderFormId 订单ID
     * @获取支付二维码信息
     * @返回二维码链接 回调地址
     */
    public static Map<String, Object> getWchatQRCode(String appid, String mch_id, String key, String body, int total_fee,
                                                     String orderFormId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            OrderReturnDTO order = unifiedNormalPay(appid, mch_id, key, body, total_fee, orderFormId, "NATIVE");

            String imgPath = PropUtil.getRootPath() + PropUtil.get("imgPath");
//            QRCode q = new QRCode();
//            q.encoderQRCode(order.getCode_url(), imgPath, order.getOut_trade_no().trim() + ".png");
//            map.put("QRCodeImgPath", PropUtil.get("sysPath") + PropUtil.get("imgPath") + order.getOut_trade_no().trim() + ".png");
//            map.put("QRCodeUrl", order.getCode_url());// 二维码链接
//            map.put("retOrder", order);
//            map.put("ouTradeNo", order.getOut_trade_no());
//            map.put("totalFee", order.getTotal_fee());
        } catch (Exception e) {
        }

        return map;
    }

    /**
     * 将微信扫码支付的长URL转换短地址
     *
     * @param appid    公众账号ID
     * @param mch_id   商户号
     * @param long_url 微信二维码长URL链接
     */
    public static String getShortUrl(String appid, String mch_id, String long_url) throws Exception {
        UnifyReqDTO os = new UnifyReqDTO(appid, mch_id, long_url);
        // 转换为XML
        String xml = HttpsUtil.getReqDateXml(os);
        String url = "https://api.mch.weixin.qq.com/tools/shorturl";
        String returnXml = HttpUtil.sendPost(url, xml);
        Map<String, String> map = HttpsUtil.XML2Map(returnXml);
        if ("SUCCESS".equals(map.get("return_code"))) {
            return map.get("short_url");
        }

        return null;
    }

    /**
     * @param body        弹出显示内容
     * @param total_fee   金额显示到分
     * @param orderFormId 订单ID
     * @param trade_type  下单类型:JSAPI，NATIVE，APP
     * @param isNormal    是否普通商户
     * @return 返回预支付交易会话标识prepay_id
     * @微信-APP支付
     */
    public static String getWchatPay(String appid, String mch_id, String key, String body, int total_fee, String orderFormId,
                                     String trade_type, boolean isNormal) {
        try {
            OrderReturnDTO order = null;
            if (isNormal) {
                order = unifiedNormalPay(appid, mch_id, key, body, total_fee, orderFormId, trade_type);
                return order.getPrepay_id();
            }
        } catch (Exception e) {
        }
        return null;
    }
}
