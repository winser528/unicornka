package com.pay.cardpay.bean;

import com.pay.utils.MD5Util;
import com.pay.utils.PropUtil;

/**
 * @TITLE ChinaBankReqDTO.java
 * @NAME 网银在线支付
 * @DATE 2017-1-23
 */
public class ChinaBankReqDTO {

	private static final String V_MONEYTYPE = "CNY";

	/** 商户编号 */
	private String v_mid;
	/** 订单编号 */
	private String v_oid;
	/** 订单总金额 */
	private int v_amount;
	/** 币种 */
	private String v_moneytype = V_MONEYTYPE;
	/** 消费者完成消费后返回商户的页面地址 */
	private String v_url;
	/** 网银在线的密钥 */
	private String key;
	/** MD5校验码 */
	private String v_md5info;
	/** 备注1，自定义参数 */
	private String remark1;
	/** 备注2,结果通知页面 */
	private String remark2;

	/**
	 * 网银在线支付请求参数
	 *
	 * @param v_oid
	 *            订单编号
	 * @param v_amount
	 *            订单总金额
	 * @param remark1
	 *            自定义参数
	 * @param remark2
	 *            结果通知页面
	 */
	public ChinaBankReqDTO(String v_oid, int v_amount, String remark1, String remark2) {
		setV_mid(PropUtil.get("cb_v_mid"));
		this.v_oid = v_oid;
		this.v_amount = v_amount;
		setV_url(PropUtil.get("cb_v_mid"));
		setKey(PropUtil.get("cb_key"));
		this.v_md5info = getMD5Info();
		this.remark1 = remark1;
		this.remark2 = remark2;
	}

	/**
	 * 获取验签信息
	 */
	private String getMD5Info() {
		String temp = v_amount + V_MONEYTYPE + v_oid + v_mid + v_url + key;
		return MD5Util.md5(temp).toUpperCase();
	}

	public String getV_mid() {
		return v_mid;
	}

	public void setV_mid(String v_mid) {
		this.v_mid = v_mid;
	}

	public String getV_oid() {
		return v_oid;
	}

	public void setV_oid(String v_oid) {
		this.v_oid = v_oid;
	}

	public int getV_amount() {
		return v_amount;
	}

	public void setV_amount(int v_amount) {
		this.v_amount = v_amount;
	}

	public String getV_moneytype() {
		return v_moneytype;
	}

	public void setV_moneytype(String v_moneytype) {
		this.v_moneytype = v_moneytype;
	}

	public String getV_url() {
		return v_url;
	}

	public void setV_url(String v_url) {
		this.v_url = v_url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getV_md5info() {
		return v_md5info;
	}

	public void setV_md5info(String v_md5info) {
		this.v_md5info = v_md5info;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
}
