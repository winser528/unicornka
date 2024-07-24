package com.pay.wchat.listener;

import com.pay.wchat.bean.RefundResMsgDTO;

/**
 * @TITLE RefundResultListener.java
 * @NAME 退款返回信息处理监听
 * @DATE 2017-1-17
 */
public interface RefundResultListener {

	// API返回ReturnCode不合法，支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问
	void onFailByReturnCodeError(RefundResMsgDTO refundResData);

	// API返回ReturnCode为FAIL，支付API系统返回失败，请检测Post给API的数据是否规范合法
	void onFailByReturnCodeFail(RefundResMsgDTO refundResData);

	// 支付请求API返回的数据签名验证失败，有可能数据被篡改了
	void onFailBySignInvalid(RefundResMsgDTO refundResData);

	// 退款失败
	void onRefundFail(RefundResMsgDTO refundResData);

	// 退款成功
	void onRefundSuccess(RefundResMsgDTO refundResData);

}
