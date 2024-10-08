package com.pay.wchat.bean;

/**
 * @TITLE RefundResMsgDTO.java
 * @NAME 退款返回信息
 * @DATE 2017-1-17
 */
public class RefundResMsgDTO {

	// 协议层
	private String return_code = "";// 返回状态码
	private String return_msg = "";// 返回信息
	// 协议返回的具体数据（以下字段在return_code 为SUCCESS 的时候有返回）
	private String result_code = "";// 业务结果
	private String err_code = "";// 错误代码
	private String err_code_des = "";// 错误代码描述

	private String appid = "";// 公众账号ID
	private String mch_id = "";// 商户号
	private String device_info = "";// 设备号
	private String nonce_str = "";// 随机字符串
	private String sign = "";// 签名
	private String transaction_id = "";// 微信订单号
	private String out_trade_no = "";// 商户订单号
	private String out_refund_no = "";// 商户退款单号
	private String refund_id = "";// 微信退款单号
	private String refund_channel = "";// 退款渠道
	private String refund_fee = "";// 退款金额
	private String coupon_refund_fee = "";// 代金券退款总金额
	private String sub_mch_id = "";// 子商户号

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	public String getRefund_id() {
		return refund_id;
	}

	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

	public String getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(String refund_fee) {
		this.refund_fee = refund_fee;
	}

	public String getCoupon_refund_fee() {
		return coupon_refund_fee;
	}

	public void setCoupon_refund_fee(String coupon_refund_fee) {
		this.coupon_refund_fee = coupon_refund_fee;
	}

	public String getRefund_channel() {
		return refund_channel;
	}

	public void setRefund_channel(String refund_channel) {
		this.refund_channel = refund_channel;
	}

	@Override
	public String toString() {
		return "RefundResMsgDTO [return_code=" + return_code + ", return_msg=" + return_msg + ", result_code=" + result_code
				+ ", err_code=" + err_code + ", err_code_des=" + err_code_des + ", appid=" + appid + ", mch_id=" + mch_id
				+ ", device_info=" + device_info + ", nonce_str=" + nonce_str + ", sign=" + sign + ", transaction_id="
				+ transaction_id + ", out_trade_no=" + out_trade_no + ", out_refund_no=" + out_refund_no + ", refund_id=" + refund_id
				+ ", refund_channel=" + refund_channel + ", refund_fee=" + refund_fee + ", coupon_refund_fee=" + coupon_refund_fee
				+ ", sub_mch_id=" + sub_mch_id + "]";
	}
}
