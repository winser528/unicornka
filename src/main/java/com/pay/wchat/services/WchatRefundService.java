package com.pay.wchat.services;

import com.pay.utils.HttpUtil;
import com.pay.utils.HttpsUtil;
import com.pay.utils.LogProUtils;
import com.pay.utils.SignUtil;
import com.pay.wchat.bean.RefundReqMsgDTO;
import com.pay.wchat.bean.RefundResMsgDTO;
import com.pay.wchat.config.Configure;
import com.pay.wchat.listener.RefundResultListener;

/**
 * @TITLE WachatRefundService.java
 * @NAME 微信退款服务
 * @DATE 2017-1-16
 */
public class WchatRefundService {

    /**
     * 申请退款
     */
    public static RefundResMsgDTO getRefundRet(String encoding, String xml, String key) {
        RefundResMsgDTO rrm = null;
        try {
            String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
            String returnXml = HttpUtil.sendPost(url, xml);
            if (!SignUtil.TencentSignCheckIsSignValidFromResponseString(encoding, returnXml, key)) {
                return null;
            }
            LogProUtils.debug("微信申请退款，返回的Xml信息：==>" + returnXml);
            rrm = SignUtil.parseXMLToObject(returnXml, RefundResMsgDTO.class);

        } catch (Exception e) {
            LogProUtils.error("微信申请退款，发送请求获得结果:异常\n" + e);
        }
        return rrm;
    }

    /**
     * 申请退款
     *
     * @param transactionID 是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。
     *                      建议优先使用
     * @param outTradeNo    商户系统内部的订单号,transaction_id 、out_trade_no
     *                      二选一，如果同时存在优先级：transaction_id > out_trade_no
     * @param outRefundNo   商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
     * @param totalFee      订单总金额，单位为分
     * @param refundFee     退款总金额，单位为分,可以做部分退款
     * @param opUserID      操作员帐号, 默认为商户号
     * @param refundFeeType 货币类型，符合ISO 4217标准的三位字母代码，默认为CNY（人民币）
     */
    public static RefundResMsgDTO entryRefund(String encoding, String transactionID, String outTradeNo, String outRefundNo,
                                              int totalFee, int refundFee, String opUserID, String refundFeeType) {
        // 退款信息
        RefundReqMsgDTO r = new RefundReqMsgDTO(transactionID, outTradeNo, outRefundNo, totalFee, refundFee, opUserID, refundFeeType);
        // 转换为XML
        String xml = HttpsUtil.getReqDateXml(r);
        RefundResMsgDTO refundRet = getRefundRet(encoding, xml, Configure.getKey());
        return refundRet;
    }

    public static String entryRefund(String encoding, String transactionID, String outTradeNo, String outRefundNo, int totalFee,
                                     int refundFee, String opUserID, String refundFeeType, RefundResultListener resultListener) {
        RefundResMsgDTO refundResData = entryRefund(encoding, transactionID, outTradeNo, outRefundNo, totalFee, refundFee, opUserID,
                refundFeeType);
        if (refundResData == null || refundResData.getReturn_code() == null) {
            LogProUtils.debug("Case1:退款API请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
            resultListener.onFailByReturnCodeError(refundResData);
            return "onFailByReturnCodeError";
        }
        if (refundResData.getReturn_code().equals("FAIL")) {
            LogProUtils.debug("Case2:退款API系统返回失败，请检测Post给API的数据是否规范合法");
            resultListener.onFailByReturnCodeFail(refundResData);
            return "FAIL";
        } else {
            LogProUtils.debug("退款API系统成功返回数据");
            // --------------------------------------------------------------------
            // 收到API的返回数据的时候得先验证一下数据有没有被第三方篡改，确保安全
            // --------------------------------------------------------------------
            if (refundResData.getResult_code().equals("FAIL")) {
                LogProUtils.debug("出错，错误码：" + refundResData.getErr_code() + "  错误信息：" + refundResData.getErr_code_des());
                // 退款失败时再怎么延时查询退款状态都没有意义，这个时间建议要么再手动重试一次，依然失败的话请走投诉渠道进行投诉
                resultListener.onRefundFail(refundResData);
                return "FAIL";
            } else {
                // 退款成功
                LogProUtils.debug("Case5:【退款成功】");
                resultListener.onRefundSuccess(refundResData);
                return "SUCCESS";
            }
        }
    }

    /**
     * 普通商户申请退款
     *
     * @param appid         公众号ID
     * @param mch_id        商户号ID
     * @param key           商户密钥
     * @param transactionID 是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。
     *                      建议优先使用
     * @param outTradeNo    商户系统内部的订单号,transaction_id 、out_trade_no
     *                      二选一，如果同时存在优先级：transaction_id > out_trade_no
     * @param outRefundNo   商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
     * @param totalFee      订单总金额，单位为分
     * @param refundFee     退款总金额，单位为分,可以做部分退款
     * @param opUserID      操作员帐号, 默认为商户号
     * @param refundFeeType 货币类型，符合ISO 4217标准的三位字母代码，默认为CNY（人民币）
     */
    public static RefundResMsgDTO entryRefundNormal(String encoding, String appid, String mch_id, String key, String transactionID,
                                                    String outTradeNo, String outRefundNo, int totalFee, int refundFee, String opUserID, String refundFeeType) {
        // 退款信息
        RefundReqMsgDTO r = new RefundReqMsgDTO(appid, mch_id, key, transactionID, outTradeNo, outRefundNo, totalFee, refundFee,
                opUserID, refundFeeType);
        // 转换为XML
        String xml = HttpsUtil.getReqDateXml(r);
        RefundResMsgDTO refundRet = getRefundRet(encoding, xml, key);
        return refundRet;
    }
}
