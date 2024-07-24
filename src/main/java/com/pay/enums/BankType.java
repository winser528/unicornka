package com.pay.enums;

/**
 * @TITLE BankType.java
 * @NAME 枚举银行类型
 * @DATE 2017-1-13
 */
public enum BankType {

	/** 中国银行 */
	BOCB2C("BOCB2C", "中国银行"),
	/** 中国工商银行 */
	ICBCB2C("ICBCB2C", "中国工商银行"),
	/** 中国工商银行(B2B) */
	ICBCBTB("ICBCBTB", "中国工商银行(B2B)"),
	/** 招商银行 */
	CMB("CMB", "招商银行"),
	/** 中国建设银行 */
	CCB("CCB", "中国建设银行"),
	/** 中国建设银行(B2B) */
	CCBBTB("CCBBTB", "中国建设银行(B2B)"),
	/** 中国农业银行 */
	ABC("ABC", "中国农业银行"),
	/** 中国农业银行(B2B) */
	ABCBTB("ABCBTB", "中国农业银行(B2B)"),
	/** 上海浦东发展银行 */
	SPDB("SPDB", "上海浦东发展银行"),
	/** 上海浦东发展银行(B2B) */
	SPDBB2B("SPDBB2B", "上海浦东发展银行(B2B)"),
	/** 兴业银行 */
	CIB("CIB", "兴业银行"),
	/** 广东发展银行 */
	GDB("GDB", "广东发展银行"),
	/** 深圳发展银行 */
	SDB("SDB", "深圳发展银行"),
	/** 中国民生银行 */
	CMBC("CMBC", "中国民生银行"),
	/** 交通银行 */
	COMM("COMM", "交通银行"),
	/** 中信银行 */
	CITIC("CITIC", "中信银行"),
	/** 光大银行 */
	CEBBANK("CEBBANK", "光大银行"),
	/** 宁波银行 */
	NBBANK("NBBANK", "宁波银行"),
	/** 杭州银行 */
	HZCBB2C("HZCBB2C", "杭州银行"),
	/** 上海银行 */
	SHBANK("SHBANK", "上海银行"),
	/** 平安银行 */
	SPABANK("SPABANK", "平安银行"),
	/** 北京农村商业银行 */
	BJRCB("BJRCB", "北京农村商业银行"),
	/** 富滇银行 */
	fdb101("fdb101", "富滇银行"),
	/** 中国邮政储蓄银行 */
	PSBCDEBIT("PSBC-DEBIT", "中国邮政储蓄银行"),
	/** 北京银行 */
	BJBANK("BJBANK", "北京银行");

	/** 银行编码 */
	String bankCode;
	/** 银行名称 */
	String bankName;

	private BankType(String bankCode, String bankName) {
		this.bankCode = bankCode;
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getBankName() {
		return bankName;
	}

}
