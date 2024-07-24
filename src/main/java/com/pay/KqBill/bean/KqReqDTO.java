package com.pay.KqBill.bean;

import com.pay.utils.PropUtil;

/**
 * @TITLE KqReqDTO.java
 * @NAME 快钱支付请求数据
 * @DATE 2017-1-21
 */
public class KqReqDTO {

	// 字符集:固定选择值：1、2、3 1代表UTF-8; 2代表GBK; 3代表GB2312
	private int inputCharset;
	// 服务器接受支付结果的后台地址
	private String bgUrl;
	// 网关版本：固定值：v2.0 注意为小写字母
	private String version;
	// 网关页面显示语言种类：固定值：1 1代表中文显示
	private int language;
	// 签名类型
	private int signType;
	// 验签信息
	private String signMsg;
	// ########买卖双方信息参数#########
	// 人民币账号
	private String merchantAcctId;
	// 支付人姓名
	private String payerName;
	// 支付人联系方式类型：固定值：1或者2 1代表电子邮件方式；2代表手机联系方式
	private int payerContactType;
	// 支付人联系方式:根据payerContactType的方式填写对应字符，邮箱或者手机号码
	private String payerContact;
	// ########业务参数########
	// 商户订单号
	private String orderId;
	// 商户订单金额：整型数字 ，以分为单位
	private int orderAmount;
	// 商户订单提交时间：一共14位 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
	private String orderTime;
	// 商品名称
	private String productName;
	// 商品数量
	private int productNum;
	// 商品代码
	private String productId;
	// 商品描述
	private String productDesc;
	// 扩展字段1
	private String ext1;
	// 扩展字段2
	private String ext2;
	/*
	 * 支付方式: 00代表显示快钱各支付方式列表（默认开通10、11、12、13四种支付方式）； 10代表只显示银行卡支付方式； 10-1
	 * 代表储蓄卡网银支付；10-2 代表信用卡网银支付 11代表只显示电话银行支付方式； 11-1 代表储蓄卡电话支付；11-2 代表信用卡电话支付
	 * 12代表只显示快钱账户支付方式； 13代表只显示线下支付方式； 14代表显示企业网银支付； 15信用卡无卡支付 17预付卡支付;
	 * 19手机语音支付; 19-1 代表储蓄卡手机语音支付 ；19-2 代表信用卡手机语音支付 21 快捷支付 21-1 代表储蓄卡快捷；21-2
	 * 代表信用卡快捷
	 */
	private String payType;
	// 同一订单禁止重复提交标志：1代表同一订单号只允许提交1次；0表示同一订单号在没有支付成功的前提下可重复提交多次。 默认为0
	private int redoFlag;
	// 合作伙伴在快钱的用户编号
	private String pid;

	/**
	 * @param payerName
	 *            支付人姓名
	 * @param payerContactType
	 *            支付人联系方式类型： 1代表电子邮件方式；2代表手机联系方式
	 * @param payerContact
	 *            支付人联系方式:根据payerContactType的方式填写对应字符，邮箱或者手机号码
	 * @param orderId
	 *            商户订单号
	 * @param orderAmount
	 *            商户订单金额：整型数字 ，以分为单位
	 * @param orderTime
	 *            例如：20071117020101
	 * @param productName
	 *            商品名称
	 * @param productNum
	 *            商品数量
	 * @param productId
	 *            商品代码
	 * @param productDesc
	 *            商品描述
	 * @param ext1
	 *            扩展字段1
	 * @param ext2
	 *            扩展字段2
	 * @param payType
	 *            支付方式: 00代表显示快钱各支付方式列表（默认开通10、11、12、13四种支付方式）；<BR>
	 *            10代表只显示银行卡支付方式； 10-1 代表储蓄卡网银支付；10-2 代表信用卡网银支付 11代表只显示电话银行支付方式；<BR>
	 *            11-1 代表储蓄卡电话支付；11-2 代表信用卡电话支付 <BR>
	 *            12代表只显示快钱账户支付方式； <BR>
	 *            13代表只显示线下支付方式； <BR>
	 *            14代表显示企业网银支付；<BR>
	 *            15信用卡无卡支付 <BR>
	 *            17预付卡支付; <BR>
	 *            19手机语音支付; 19-1 代表储蓄卡手机语音支付 ；19-2 代表信用卡手机语音支付 <BR>
	 *            21 快捷支付 21-1 代表储蓄卡快捷；21-2 代表信用卡快捷
	 * @param pid
	 *            合作伙伴在快钱的用户编号
	 */
	public KqReqDTO(String payerName, int payerContactType, String payerContact, String orderId, int orderAmount, String orderTime,
					String productName, int productNum, String productId, String productDesc, String ext1, String ext2, String payType,
					String pid) {
		setInputCharset(Integer.valueOf(PropUtil.get("kq_Charset")));
		setBgUrl(PropUtil.get("kq_bgUrl"));
		setVersion(PropUtil.get("kq_version"));
		setSignType(Integer.valueOf(PropUtil.get("kq_Charset")));
		setMerchantAcctId(PropUtil.get("kq_merchantAcctId"));
		this.language = 1;
		this.payerName = payerName;
		this.payerContactType = payerContactType;
		this.payerContact = payerContact;
		this.orderId = orderId;
		this.orderAmount = orderAmount;
		this.orderTime = orderTime;
		this.productName = productName;
		this.productNum = productNum;
		this.productId = productId;
		this.productDesc = productDesc;
		this.ext1 = ext1;
		this.ext2 = ext2;
		this.payType = payType;
		setRedoFlag(Integer.valueOf(PropUtil.get("kq_redoFlag")));
		this.pid = pid;
	}

	public int getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(int inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getBgUrl() {
		return bgUrl;
	}

	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	public int getSignType() {
		return signType;
	}

	public void setSignType(int signType) {
		this.signType = signType;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getMerchantAcctId() {
		return merchantAcctId;
	}

	public void setMerchantAcctId(String merchantAcctId) {
		this.merchantAcctId = merchantAcctId;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public int getPayerContactType() {
		return payerContactType;
	}

	public void setPayerContactType(int payerContactType) {
		this.payerContactType = payerContactType;
	}

	public String getPayerContact() {
		return payerContact;
	}

	public void setPayerContact(String payerContact) {
		this.payerContact = payerContact;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getRedoFlag() {
		return redoFlag;
	}

	public void setRedoFlag(int redoFlag) {
		this.redoFlag = redoFlag;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
