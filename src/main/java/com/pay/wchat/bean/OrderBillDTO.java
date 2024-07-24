package com.pay.wchat.bean;

import com.pay.utils.PayCommonUtil;
import com.pay.utils.SignUtil;
import com.pay.wchat.config.Configure;

/**
 * @TITLE OrderBillDTO.java
 * @NAME 订单对账下载请求
 * @DATE 2017-1-17
 */
public class OrderBillDTO {

	private String appid;// 公众账号ID
	private String mch_id;// 商户号
	private String nonce_str;// 随机字符串
	private String sign;// 签名
	private String bill_date;// 下载对账单的日期，格式：20140603
	private String bill_type = "ALL";// ALL，返回当日所有订单信息，默认值,SUCCESS，返回当日成功支付的订单，REFUND，返回当日退款订单

	/**
	 * 订单对账下载请求
	 *
	 * @param appid
	 *            公众账号ID
	 * @param mch_id
	 *            商户号
	 * @param bill_date
	 *            下载对账单的日期，格式：20140603
	 * @param bill_type
	 *            ALL，返回当日所有订单信息，默认值,SUCCESS，返回当日成功支付的订单，REFUND，返回当日退款订单
	 */
	public OrderBillDTO(String appid, String mch_id, String bill_date, String bill_type) {
		this.appid = appid;
		this.mch_id = mch_id;
		this.bill_date = bill_date;
		// 随机字符串
		setNonce_str(PayCommonUtil.getRandomStringByLength(32));
		// 根据API给的签名规则进行签名
		String sign = SignUtil.TencentSign("utf-8", SignUtil.toMap(this, OrderBillDTO.class), Configure.getKey());
		setSign(sign);// 把签名数据设置到Sign这个属性中
	}

	public String getAppid() {
		return appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public String getBill_date() {
		return bill_date;
	}

	public String getBill_type() {
		return bill_type;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * 下载对账单的日期，格式：20140603
	 */
	public void setBill_date(String bill_date) {
		this.bill_date = bill_date;
	}

	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}

}
