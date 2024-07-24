package com.pay.tenpay.bean;

/**
 * @TITLE TenpayReqDTO.java
 * @NAME 财付通下单数据
 * @DATE 2017-1-14
 */
public class TenpayReqDTO {

	/** 版本号 */
	private String ver;
	/** 字符集:1 UTF-8, 2 GB2312， 默认为1 */
	private String charset;
	/** 银行类型:填0 */
	private String bank_type;
	/** 商品描述，32个字符以内 */
	private String desc;
	/** 用户(买方)的QQ号码。若商户取不到则此字段可不传 */
	private String purchaser_id;
	/** 描述支付渠道:1 手Q支付 */
	private String pay_channel;
	/** 商户号，由手Q支付统一分配的10位正整数 */
	private String bargainor_id;
	/** 商户订单号，商户系统内部的定单号，32个字符内、可包含字母 */
	private String sp_billno;
	/** 总金额，以分为单位，不允许包含任何字、符号 */
	private int total_fee;
	/** 现金支付币种，目前只支持人民币，默认值是1：人民币 */
	private int fee_type;
	/** 接收手Q支付通知的URL，需给绝对路径，255字符内格式 */
	private String notify_url;
	/** 商户附加信息，可做扩展参数，255字符内 */
	private String attach;
	/** 订单生成时间，格式为yyyymmddhhmmss */
	private String time_start;
	/** 订单失效时间，格式为yyyymmddhhmmss */
	private String time_expire;
	/** MD5签名 */
	private String sign;

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPurchaser_id() {
		return purchaser_id;
	}

	public void setPurchaser_id(String purchaser_id) {
		this.purchaser_id = purchaser_id;
	}

	public String getPay_channel() {
		return pay_channel;
	}

	public void setPay_channel(String pay_channel) {
		this.pay_channel = pay_channel;
	}

	public String getBargainor_id() {
		return bargainor_id;
	}

	public void setBargainor_id(String bargainor_id) {
		this.bargainor_id = bargainor_id;
	}

	public String getSp_billno() {
		return sp_billno;
	}

	public void setSp_billno(String sp_billno) {
		this.sp_billno = sp_billno;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public int getFee_type() {
		return fee_type;
	}

	public void setFee_type(int fee_type) {
		this.fee_type = fee_type;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
