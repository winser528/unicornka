package com.pay.wchat.services;

import com.pay.utils.HttpUtil;
import com.pay.utils.HttpsUtil;
import com.pay.utils.LogProUtils;
import com.pay.utils.SignUtil;
import com.pay.wchat.bean.OrderBillDTO;
import com.pay.wchat.bean.OrderReturnDTO;
import com.pay.wchat.bean.OrderServiceDTO;
import com.pay.wchat.bean.RefundOrderRetrunDTO;
import com.pay.wchat.bean.RefundOrderServiceDTO;

/**
 * @TITLE WchatOrderQryService.java
 * @NAME 微信订单服务
 * @DATE 2017-1-17
 */
public class WchatOrderService {

    /**
     * 查询订单
     *
     * @param appid          公众号ID
     * @param mch_id         商户号ID
     * @param transaction_id 微信系统为每一笔支付交易分配的订单号
     * @param out_trade_no   订单号
     */
    public OrderReturnDTO queryOrder(String appid, String mch_id, String transaction_id, String out_trade_no) {
        OrderReturnDTO ret = null;
        try {
            // 查询信息
            OrderServiceDTO os = new OrderServiceDTO(appid, mch_id, transaction_id, out_trade_no);
            // 转换为XML
            String xml = HttpsUtil.getReqDateXml(os);
            String url = "https://api.mch.weixin.qq.com/pay/orderquery";
            String returnXml = HttpUtil.sendPost(url, xml);
            LogProUtils.debug("微信查询订单，返回的Xml信息：==>" + returnXml);
            ret = SignUtil.parseXMLToObject(returnXml, OrderReturnDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 关闭订单
     *
     * @param appid          公众号ID
     * @param mch_id         商户号ID
     * @param transaction_id 微信系统为每一笔支付交易分配的订单号
     * @param out_trade_no   订单号
     */
    public String closeOrder(String appid, String mch_id, String transaction_id, String out_trade_no) {
        String ret = null;
        try {
            // 查询信息
            OrderServiceDTO os = new OrderServiceDTO(appid, mch_id, transaction_id, out_trade_no);
            // 转换为XML
            String xml = HttpsUtil.getReqDateXml(os);
            String url = "https://api.mch.weixin.qq.com/pay/closeorder";
            String returnXml = HttpUtil.sendPost(url, xml);
            LogProUtils.debug("微信查询订单，返回的Xml信息：==>" + returnXml);
            OrderReturnDTO orDto = SignUtil.parseXMLToObject(returnXml, OrderReturnDTO.class);
            ret = orDto.getResult_code();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 查询退款订单
     *
     * @param appid          公众号ID
     * @param mch_id         商户号ID
     * @param transaction_id 微信系统为每一笔支付交易分配的订单号
     * @param out_trade_no   订单号
     * @param out_refund_no  商户退款单号
     * @param refund_id      微信退款单号
     */
    public RefundOrderRetrunDTO queryRefundOrder(String appid, String mch_id, String transaction_id, String out_trade_no,
                                                 String out_refund_no, String refund_id) {
        RefundOrderRetrunDTO ret = null;
        try {
            // 查询信息
            RefundOrderServiceDTO os = new RefundOrderServiceDTO(appid, mch_id, transaction_id, out_trade_no, out_refund_no, refund_id);
            // 转换为XML
            String xml = HttpsUtil.getReqDateXml(os);
            String url = "https://api.mch.weixin.qq.com/pay/refundquery";
            String returnXml = HttpUtil.sendPost(url, xml);
            LogProUtils.debug("微信查询退款订单，返回的Xml信息：==>" + returnXml);
            ret = SignUtil.parseXMLToObject(returnXml, RefundOrderRetrunDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 获取微信的对账信息
     *
     * @param appid     公众账号ID
     * @param mch_id    商户号
     * @param bill_date 下载对账单的日期，格式：20140603
     * @param bill_type ALL，返回当日所有订单信息，默认值,SUCCESS，返回当日成功支付的订单，REFUND，返回当日退款订单
     */
    public Object getWchatOrderBill(String appid, String mch_id, String bill_date, String bill_type) {
        Object ret = null;
        try {
            // 查询信息
            OrderBillDTO os = new OrderBillDTO(appid, mch_id, bill_date, bill_type);
            // 转换为XML
            String xml = HttpsUtil.getReqDateXml(os);
            String url = "https://api.mch.weixin.qq.com/pay/downloadbill";
            String returnXml = HttpUtil.sendPost(url, xml);
            LogProUtils.debug("微信对账信息，返回的Xml信息：==>" + returnXml);

            ret = HttpsUtil.XML2Map(returnXml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
