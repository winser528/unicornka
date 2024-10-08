package com.pay.wchat.bean;

/**
 * @TITLE RefundOrderRetrunDTO.java
 * @NAME 退款查询返回数据
 * @DATE 2017-1-17
 */
public class RefundOrderRetrunDTO {

	// 协议层
	private String return_code = "";// 返回状态码
	private String return_msg = "";// 返回信息

	// 协议返回的具体数据（以下字段在return_code 为SUCCESS 的时候有返回）
	private String result_code = "";// 业务结果
	private String err_code = ""; // 错误码
	private String err_code_des = "";// 错误描述
	private String appid = "";// 公众账号ID
	private String mch_id = "";// 商户号
	private String device_info = "";// 设备号
	private String nonce_str = "";// 随机字符串
	private String sign = "";// 签名
	private String transaction_id = "";// 微信订单号
	private String out_trade_no = "";// 商户订单号
	private int total_fee = 0;// 订单金额
	private int settlement_total_fee = 0; // 应结订单金额
	private String fee_type = "CNY";// 货币种类
	private int cash_fee = 0;// 现金支付金额
	private int refund_count = 0;// 退款笔数

	// TODO 这里要用对象来装，因为有可能出现多个数据
	private String out_refund_no = "";
	private String refund_id = "";
	private String refund_channel = "";
	private String refund_fee = "";
	private String coupon_refund_fee = "";
	private String refund_status_0 = "";
	private String refund_recv_accout_0 = "";

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

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
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

	public int getTotal_fee() {
		return total_fee;
	}

	public int getSettlement_total_fee() {
		return settlement_total_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public int getCash_fee() {
		return cash_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public void setSettlement_total_fee(int settlement_total_fee) {
		this.settlement_total_fee = settlement_total_fee;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public void setCash_fee(int cash_fee) {
		this.cash_fee = cash_fee;
	}

	public int getRefund_count() {
		return refund_count;
	}

	public void setRefund_count(int refund_count) {
		this.refund_count = refund_count;
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

	public String getRefund_channel() {
		return refund_channel;
	}

	public void setRefund_channel(String refund_channel) {
		this.refund_channel = refund_channel;
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

	public String getRefund_status_0() {
		return refund_status_0;
	}

	public void setRefund_status_0(String refund_status) {
		this.refund_status_0 = refund_status;
	}

	public String getRefund_recv_accout_0() {
		return refund_recv_accout_0;
	}

	public void setRefund_recv_accout_0(String refund_recv_accout_0) {
		this.refund_recv_accout_0 = refund_recv_accout_0;
	}

}
