package com.pay.enums;

/**
 * @TITLE PalCurrency.java
 * @NAME 贝宝支持的交易币种及代码
 * @DATE 2017-1-24
 */
public enum PalCurrency {

	/** 澳大利亚元 */
	AUD("澳元", "AUD"),
	/** 巴西雷亚尔 */
	BRL("巴西雷亚尔", "BRL"),
	/** 人民币 */
	CNY("人民币 ", "CNY"),
	/** 加拿大元 */
	CAD("加元", "CAD"),
	/** 瑞士法郎 */
	CHF("瑞士法郎", "CHF"),
	/** 捷克克朗 */
	CZK("捷克克朗", "CZK"),
	/** 丹麦克朗 */
	DKK("丹麦克朗", "DKK"),
	/** 欧元 */
	EUR("欧元", "EUR"),
	/** 英镑 */
	GBP("英镑", "GBP"),
	/** 港币 */
	HKD("港币", "HKD"),
	/** 匈牙利福林 */
	HUF("匈牙利福林", "HUF"),
	/** 以色列谢克尔 */
	ILS("以色列谢克尔", "ILS"),
	/** 日元 */
	JPY("日元", "JPY"),
	/** 墨西哥比索 */
	MXN("墨西哥比索", "MXN"),
	/** 挪威克朗 */
	NOK("挪威克朗", "NOK"),
	/** 新西兰元 */
	NZD("新西兰元", "NZD"),
	/** 菲律宾比索 */
	PHP("菲律宾比索", "PHP"),
	/** 波兰兹罗提 */
	PLN("波兰兹罗提", "PLN"),
	/** 新加坡元 */
	SGD("新加坡元", "SGD"),
	/** 瑞典克朗 */
	SEK("瑞典克朗", "SEK"),
	/** 台湾货币，新台币 */
	TWD("新臺幣", "TWD"),
	/** 泰国泰铢 */
	THB("泰铢", "THB"),
	/** 土耳其里拉 */
	TRY("土耳其里拉", "TRY"),
	/**  */
	USD("美元", "USD");

	/** 货币名称 */
	private String name;
	/** 货币代码值 */
	private String value;

	private PalCurrency(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
}
