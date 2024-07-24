package com.pay.paypal.bean;

import java.math.BigDecimal;

import com.pay.enums.PalCurrency;

/**
 * @TITLE NvpRetDoExpressCheckoutPayment.java
 * @NAME 结帐付款响应
 * @DATE 2017-1-24
 */
public class NvpGetDoExpressCheckoutPaymentDTO {

	/** setExpressCheckout响应返回并通过getExpressCheckoutDetails请求床底的时间戳标记只 */
	private String TOKEN;
	/** 付款的唯一交易号 */
	private String TRANSACTIONDI;
	/** 交易类型 */
	private String TRANSACTIONTYPE;
	/** 即时付款还是延迟付款：none、echeck、instant */
	private String PAYMENTTYPE;
	/** 付款的时间/日起戳 */
	private String ORDERTIME;
	/** 客户需承担的交易总费用 */
	private BigDecimal AMT = BigDecimal.ZERO;
	/** 交易币种 */
	private String CURRENCYCODE = PalCurrency.USD.getValue();
	/** 交易收取的PayPal费用 */
	private String FEEAMT;
	/** 币种兑换后存入您账户的金额 */
	private String SETTLEAMT;
	/** 交易收取的税金 */
	private String TAXAMT;
	/** 进行币种兑换时所用的汇率 */
	private String EXCHANGERATE;
	/** 付款状态，completed：付款完成，已到余额，pending：付款待处理 */
	private String PAYMENTSTATUS;
	/**
	 * 付款待处理的原因：<BR>
	 * none：无待处理原因，<BR>
	 * address：客户未提供确认的送货地址，<BR>
	 * echeck：通过电子支票而尚未结清，<BR>
	 * intl：有美国账户但不具备提现功能，<BR>
	 * multi-currency：币种没有余额，<BR>
	 * verify：尚未获得认证，<BR>
	 * other：非上述原因所致
	 */
	private String PENDINGREASON;
	/**
	 * 撤销原因（TransactionType为reversal）<BR>
	 * none:无原因代码，<BR>
	 * chargeback：客户提出的退单，撤销交易，<BR>
	 * guarantee：客户启动退款担保，撤销交易，<BR>
	 * buyer-complaint：客户对交易提出投诉，撤销交易，<BR>
	 * refund：客户退款，撤销交易，<BR>
	 * other：非上述原因所致
	 */
	private String REASONCODE;
	/** 完成交易后是否需要客户跳转回PayPal的标记 */
	private String REDIRECTREQUIRED;
	/** 过滤器号 */
	private String L_FMFfilterlDn;
	/** 过滤器名称 */
	private String L_FMFfilterNAMEn;

	public String getTOKEN() {
		return TOKEN;
	}

	public String getTRANSACTIONDI() {
		return TRANSACTIONDI;
	}

	public String getTRANSACTIONTYPE() {
		return TRANSACTIONTYPE;
	}

	public String getPAYMENTTYPE() {
		return PAYMENTTYPE;
	}

	public String getORDERTIME() {
		return ORDERTIME;
	}

	public BigDecimal getAMT() {
		return AMT;
	}

	public String getCURRENCYCODE() {
		return CURRENCYCODE;
	}

	public String getFEEAMT() {
		return FEEAMT;
	}

	public String getSETTLEAMT() {
		return SETTLEAMT;
	}

	public String getTAXAMT() {
		return TAXAMT;
	}

	public String getEXCHANGERATE() {
		return EXCHANGERATE;
	}

	public String getPAYMENTSTATUS() {
		return PAYMENTSTATUS;
	}

	public String getPENDINGREASON() {
		return PENDINGREASON;
	}

	public String getREASONCODE() {
		return REASONCODE;
	}

	public String getREDIRECTREQUIRED() {
		return REDIRECTREQUIRED;
	}

	public String getL_FMFfilterlDn() {
		return L_FMFfilterlDn;
	}

	public String getL_FMFfilterNAMEn() {
		return L_FMFfilterNAMEn;
	}

	public void setTOKEN(String tOKEN) {
		TOKEN = tOKEN;
	}

	public void setTRANSACTIONDI(String tRANSACTIONDI) {
		TRANSACTIONDI = tRANSACTIONDI;
	}

	public void setTRANSACTIONTYPE(String tRANSACTIONTYPE) {
		TRANSACTIONTYPE = tRANSACTIONTYPE;
	}

	public void setPAYMENTTYPE(String pAYMENTTYPE) {
		PAYMENTTYPE = pAYMENTTYPE;
	}

	public void setORDERTIME(String oRDERTIME) {
		ORDERTIME = oRDERTIME;
	}

	public void setAMT(BigDecimal aMT) {
		AMT = aMT;
	}

	public void setCURRENCYCODE(String cURRENCYCODE) {
		CURRENCYCODE = cURRENCYCODE;
	}

	public void setFEEAMT(String fEEAMT) {
		FEEAMT = fEEAMT;
	}

	public void setSETTLEAMT(String sETTLEAMT) {
		SETTLEAMT = sETTLEAMT;
	}

	public void setTAXAMT(String tAXAMT) {
		TAXAMT = tAXAMT;
	}

	public void setEXCHANGERATE(String eXCHANGERATE) {
		EXCHANGERATE = eXCHANGERATE;
	}

	public void setPAYMENTSTATUS(String pAYMENTSTATUS) {
		PAYMENTSTATUS = pAYMENTSTATUS;
	}

	public void setPENDINGREASON(String pENDINGREASON) {
		PENDINGREASON = pENDINGREASON;
	}

	public void setREASONCODE(String rEASONCODE) {
		REASONCODE = rEASONCODE;
	}

	public void setREDIRECTREQUIRED(String rEDIRECTREQUIRED) {
		REDIRECTREQUIRED = rEDIRECTREQUIRED;
	}

	public void setL_FMFfilterlDn(String l_FMFfilterlDn) {
		L_FMFfilterlDn = l_FMFfilterlDn;
	}

	public void setL_FMFfilterNAMEn(String l_FMFfilterNAMEn) {
		L_FMFfilterNAMEn = l_FMFfilterNAMEn;
	}
}
