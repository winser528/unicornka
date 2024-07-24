package com.pay.enums;

/**
 * @TITLE PayType.java
 * @NAME 支付类型的枚举类
 * @DATE 2017-1-13
 */
public enum PayType {

	/** 支付宝支付 */
	alipay("ALIPAY"),
	/** 财付通支付 */
	tenpay("TENPAY"),
	/** 银盛支付 */
	yspay("YSPAY"),
	/** 银联支付 */
	unionpay("UNIONPAY"),
	/** 微信支付 */
	wachat("WEIXINPAY"),
	/** 快钱支付 */
	kqbill("99BILL"),
	/** PayPal支付 */
	paypal("PAYPAL");

	/** 类型值 */
	String value;

	PayType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}