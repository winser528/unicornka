package com.pay.wchat.bean;

import com.pay.utils.PayCommonUtil;
import com.pay.utils.SignUtil;
import com.pay.wchat.config.Configure;

/**
 * @TITLE OrderQueryDTO.java
 * @NAME 订单服务请求数据
 * @DATE 2017-1-17
 */
public class OrderServiceDTO {

	private String appid;// 公众账号ID
	private String mch_id;// 商户号
	private String transaction_id = "";// 微信订单号
	private String out_trade_no = "";// 订单号
	private String nonce_str;// 随机字符串
	private String sign;// 签名

	/**
	 * 查询订单
	 *
	 * @param appid
	 *            公众账号ID
	 * @param mch_id
	 *            商户号
	 * @param transaction_id
	 *            微信订单号
	 * @param out_trade_no
	 *            订单号
	 */
	public OrderServiceDTO(String appid, String mch_id, String transaction_id, String out_trade_no) {
		this.appid = appid;
		this.mch_id = mch_id;
		this.transaction_id = transaction_id;
		this.out_trade_no = out_trade_no;
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

	public String getTransaction_id() {
		return transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
