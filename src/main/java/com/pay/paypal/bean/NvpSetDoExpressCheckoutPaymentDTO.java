package com.pay.paypal.bean;

import com.pay.enums.PalCurrency;

/**
 * @TITLE NvpSetDoExpressCheckoutDTO.java
 * @NAME 结帐付款请求
 * @DATE 2017-1-25
 */
public class NvpSetDoExpressCheckoutPaymentDTO {

	// ##### 返回值 #######
	/** SetExpressCheckout响应返回并通过GetExpressCheckoutDetails请求传递的时间戳 */
	private String TOKEN;
	// ###### 必选 #######
	/** API请求参数 */
	private String METHOD = "SetExpressCheckout";
	/** 付款后页面 */
	private String RETURNURL;
	/** 请求失败页面 */
	private String CANCELURL;
	/** 订单金额合计，包括运费、手续费、税金 */
	private String AMT;
	// ###### 可选项的必选 #######
	/** 收货人名称 */
	private String SHIPTONAME;
	/** 收货人街道地址 */
	private String SHIPTOSTREET;
	/** 收货人城市 */
	private String SHIPTOCITY;
	/** 收货人州或省 */
	private String SHIPTOSTATE;
	/** 收货人国家或地区代码 */
	private String SHIPTOCOUNTRYCODE;
	/** 收货人邮编 */
	private String SHIPTOZIP;
	// ###### 可选项 #######
	/**
	 * 获取付款方式：<BR>
	 * 默认，Sale正进行收款的终极销售<BR>
	 * Authorization该付款是通过'PayPal授权'结算的基本授权<BR>
	 * Order该付款是通过'PayPal授权'结算的订单授权
	 */
	private String PAYMENTACTION;
	/** GetExpressCheckoutDetails响应返回的唯一PayPal客户账户识别号 */
	private String PAYERID;
	/** 所购物品的描述 */
	private String DESC;
	/** 自定义字段 */
	private String CUSTOM;
	/** 您自己的账单号或跟踪号 */
	private String INVNUM;
	/** 第三方应用程序用来识别交易的标识码 */
	private String BUTTONSOURCE;
	/** 接收有关交易的即时付款通知(IPN)的URL */
	private String NOTIFYURL;
	/** 所有物品的费用合计 */
	private String ITEMAMT;
	/** 订单的运费总额 */
	private String SHIPPINGAMT;
	/** 订单的手续费总额 */
	private String HANDLINGAMT;
	/** 订单中所有物品的税金合计 */
	private String TAXAMT;
	/** PayPal支持的交易币种 */
	private String CURRENCYCODE = PalCurrency.USD.getValue();
	/** 物品名称 */
	private String L_NAMEn;
	/** 物品号 */
	private String L_NUMBERn;
	/** 物品数量 */
	private String L_QTYn;
	/** 物品营业税 */
	private String L_TAXAMTn;
	/** 物品成本 */
	private String L_AMTn;
	/** 竞拍物品号 */
	private String L_EBAYITEMNUMBERn;
	/** 竞拍交易识别码号 */
	private String L_EBAYITEMAUCTIONTXNIDn;
	/** 竞拍交易识别码号 */
	private String L_EBAYITEMORDERIDn;
	/** 是否需要防欺诈管理过滤器返回的标记：默认0，不接受FMF详情；1：接受FMF详情 */
	private int RETURNFMFDETAILS = 0;
	/** 电话号码 */
	private String SHIPTOPHONENUM;

	/**
	 * 必选项订单创建
	 *
	 * @param rETURNURL
	 *            付款后页面
	 * @param cANCELURL
	 *            请求失败页面
	 * @param aMT
	 *            订单金额合计，包括运费、手续费、税金
	 */
	private NvpSetDoExpressCheckoutPaymentDTO(String rETURNURL, String cANCELURL, String aMT) {
		RETURNURL = rETURNURL;
		CANCELURL = cANCELURL;
		AMT = aMT;
	}

	public String getTOKEN() {
		return TOKEN;
	}

	public String getMETHOD() {
		return METHOD;
	}

	public String getRETURNURL() {
		return RETURNURL;
	}

	public String getCANCELURL() {
		return CANCELURL;
	}

	public String getAMT() {
		return AMT;
	}

	public String getSHIPTONAME() {
		return SHIPTONAME;
	}

	public String getSHIPTOSTREET() {
		return SHIPTOSTREET;
	}

	public String getSHIPTOCITY() {
		return SHIPTOCITY;
	}

	public String getSHIPTOSTATE() {
		return SHIPTOSTATE;
	}

	public String getSHIPTOCOUNTRYCODE() {
		return SHIPTOCOUNTRYCODE;
	}

	public String getSHIPTOZIP() {
		return SHIPTOZIP;
	}

	public String getPAYMENTACTION() {
		return PAYMENTACTION;
	}

	public String getPAYERID() {
		return PAYERID;
	}

	public String getDESC() {
		return DESC;
	}

	public String getCUSTOM() {
		return CUSTOM;
	}

	public String getINVNUM() {
		return INVNUM;
	}

	public String getBUTTONSOURCE() {
		return BUTTONSOURCE;
	}

	public String getNOTIFYURL() {
		return NOTIFYURL;
	}

	public String getITEMAMT() {
		return ITEMAMT;
	}

	public String getSHIPPINGAMT() {
		return SHIPPINGAMT;
	}

	public String getHANDLINGAMT() {
		return HANDLINGAMT;
	}

	public String getTAXAMT() {
		return TAXAMT;
	}

	public String getCURRENCYCODE() {
		return CURRENCYCODE;
	}

	public String getL_NAMEn() {
		return L_NAMEn;
	}

	public String getL_NUMBERn() {
		return L_NUMBERn;
	}

	public String getL_QTYn() {
		return L_QTYn;
	}

	public String getL_TAXAMTn() {
		return L_TAXAMTn;
	}

	public String getL_AMTn() {
		return L_AMTn;
	}

	public String getL_EBAYITEMNUMBERn() {
		return L_EBAYITEMNUMBERn;
	}

	public String getL_EBAYITEMAUCTIONTXNIDn() {
		return L_EBAYITEMAUCTIONTXNIDn;
	}

	public String getL_EBAYITEMORDERIDn() {
		return L_EBAYITEMORDERIDn;
	}

	public int getRETURNFMFDETAILS() {
		return RETURNFMFDETAILS;
	}

	public String getSHIPTOPHONENUM() {
		return SHIPTOPHONENUM;
	}

	public void setTOKEN(String tOKEN) {
		TOKEN = tOKEN;
	}

	public void setMETHOD(String mETHOD) {
		METHOD = mETHOD;
	}

	public void setRETURNURL(String rETURNURL) {
		RETURNURL = rETURNURL;
	}

	public void setCANCELURL(String cANCELURL) {
		CANCELURL = cANCELURL;
	}

	public void setAMT(String aMT) {
		AMT = aMT;
	}

	public void setSHIPTONAME(String sHIPTONAME) {
		SHIPTONAME = sHIPTONAME;
	}

	public void setSHIPTOSTREET(String sHIPTOSTREET) {
		SHIPTOSTREET = sHIPTOSTREET;
	}

	public void setSHIPTOCITY(String sHIPTOCITY) {
		SHIPTOCITY = sHIPTOCITY;
	}

	public void setSHIPTOSTATE(String sHIPTOSTATE) {
		SHIPTOSTATE = sHIPTOSTATE;
	}

	public void setSHIPTOCOUNTRYCODE(String sHIPTOCOUNTRYCODE) {
		SHIPTOCOUNTRYCODE = sHIPTOCOUNTRYCODE;
	}

	public void setSHIPTOZIP(String sHIPTOZIP) {
		SHIPTOZIP = sHIPTOZIP;
	}

	public void setPAYMENTACTION(String pAYMENTACTION) {
		PAYMENTACTION = pAYMENTACTION;
	}

	public void setPAYERID(String pAYERID) {
		PAYERID = pAYERID;
	}

	public void setDESC(String dESC) {
		DESC = dESC;
	}

	public void setCUSTOM(String cUSTOM) {
		CUSTOM = cUSTOM;
	}

	public void setINVNUM(String iNVNUM) {
		INVNUM = iNVNUM;
	}

	public void setBUTTONSOURCE(String bUTTONSOURCE) {
		BUTTONSOURCE = bUTTONSOURCE;
	}

	public void setNOTIFYURL(String nOTIFYURL) {
		NOTIFYURL = nOTIFYURL;
	}

	public void setITEMAMT(String iTEMAMT) {
		ITEMAMT = iTEMAMT;
	}

	public void setSHIPPINGAMT(String sHIPPINGAMT) {
		SHIPPINGAMT = sHIPPINGAMT;
	}

	public void setHANDLINGAMT(String hANDLINGAMT) {
		HANDLINGAMT = hANDLINGAMT;
	}

	public void setTAXAMT(String tAXAMT) {
		TAXAMT = tAXAMT;
	}

	public void setCURRENCYCODE(String cURRENCYCODE) {
		CURRENCYCODE = cURRENCYCODE;
	}

	public void setL_NAMEn(String l_NAMEn) {
		L_NAMEn = l_NAMEn;
	}

	public void setL_NUMBERn(String l_NUMBERn) {
		L_NUMBERn = l_NUMBERn;
	}

	public void setL_QTYn(String l_QTYn) {
		L_QTYn = l_QTYn;
	}

	public void setL_TAXAMTn(String l_TAXAMTn) {
		L_TAXAMTn = l_TAXAMTn;
	}

	public void setL_AMTn(String l_AMTn) {
		L_AMTn = l_AMTn;
	}

	public void setL_EBAYITEMNUMBERn(String l_EBAYITEMNUMBERn) {
		L_EBAYITEMNUMBERn = l_EBAYITEMNUMBERn;
	}

	public void setL_EBAYITEMAUCTIONTXNIDn(String l_EBAYITEMAUCTIONTXNIDn) {
		L_EBAYITEMAUCTIONTXNIDn = l_EBAYITEMAUCTIONTXNIDn;
	}

	public void setL_EBAYITEMORDERIDn(String l_EBAYITEMORDERIDn) {
		L_EBAYITEMORDERIDn = l_EBAYITEMORDERIDn;
	}

	public void setRETURNFMFDETAILS(int rETURNFMFDETAILS) {
		RETURNFMFDETAILS = rETURNFMFDETAILS;
	}

	public void setSHIPTOPHONENUM(String sHIPTOPHONENUM) {
		SHIPTOPHONENUM = sHIPTOPHONENUM;
	}
}
