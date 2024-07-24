package com.pay.wchat.services;

import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;

import com.pay.utils.HttpUtil;
import com.pay.utils.HttpsUtil;
import com.pay.utils.LogProUtils;
import com.pay.wchat.bean.ReportReqMsgDTO;

/**
 * @TITLE WchatReportService.java
 * @NAME 交易保障服务
 * @DATE 2017-1-18
 */
public class WchatReportService {

    private ReportReqMsgDTO reqData;

    /**
     * 请求统计上报API
     *
     * @param deviceInfo      微信支付分配的终端设备号，商户自定义
     * @param interfaceUrl    上报对应的接口的完整URL，类似：
     *                        https://api.mch.weixin.qq.com/pay/unifiedorder
     * @param executeTimeCost 接口耗时情况，单位为毫秒
     * @param returnCode      API返回的对应字段
     * @param returnMsg       API返回的对应字段
     * @param resultCode      API返回的对应字段
     * @param errCode         API返回的对应字段
     * @param errCodeDes      API返回的对应字段
     * @param outTradeNo      API返回的对应字段
     * @param userIp          发起接口调用时的机器IP
     */
    public WchatReportService(String deviceInfo, String interfaceUrl, int executeTimeCost, String returnCode, String returnMsg,
                              String resultCode, String errCode, String errCodeDes, String outTradeNo, String userIp) {
        reqData = new ReportReqMsgDTO(deviceInfo, interfaceUrl, executeTimeCost, returnCode, returnMsg, resultCode, errCode,
                errCodeDes, outTradeNo, userIp);
    }

    /**
     * 请求交易保障获取结果
     */
    public String getReportRet() throws ConnectionPoolTimeoutException, ConnectTimeoutException, SocketTimeoutException, Exception {
        String ret = null;
        try {
            String url = "https://api.mch.weixin.qq.com/payitil/report";
            // 转换为XML
            String xml = HttpsUtil.getReqDateXml(reqData);
            String returnXml = HttpUtil.sendPost(url, xml);
            LogProUtils.debug("微信交易保障，返回的Xml信息：==>" + returnXml);
        } catch (Exception e) {
            LogProUtils.error("微信申请退款，发送请求获得结果:异常\n" + e);
        }
        return ret;
    }

    /**
     * 请求交易保障获取结果
     *
     * @param reportReqData *            这个数据对象里面包含了API要求提交的各种数据字段
     */
    public static String getReportRet(ReportReqMsgDTO reportReqData) {
        String ret = null;
        try {
            String url = "https://api.mch.weixin.qq.com/payitil/report";
            // 转换为XML
            String xml = HttpsUtil.getReqDateXml(reportReqData);
            String returnXml = HttpUtil.sendPost(url, xml);
            LogProUtils.debug("微信交易保障，返回的Xml信息：==>" + returnXml);
        } catch (Exception e) {
            LogProUtils.error("微信申请退款，发送请求获得结果:异常\n" + e);
        }
        return ret;
    }
}
