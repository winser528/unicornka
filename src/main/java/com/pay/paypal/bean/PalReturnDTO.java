package com.pay.paypal.bean;

/**
 * @TITLE PalReturnDTO.java
 * @NAME 返回响应信息
 * @DATE 2017-1-24
 */
public class PalReturnDTO {

	/**
	 * 相应类型： <BR>
	 * 成功，Success、SuccessWithWarning <BR>
	 * 错误，Failure、FailureWithWarning、Warning
	 */
	private String ACK;
	/**  */
	private String tIMESTAMP;
	/**  */
	private String CRRELATIONID;
	/**  */
	private String VERSION;
	/**  */
	private String BUILD;
	/**  */
	private String TOKEN;

	public String getACK() {
		return ACK;
	}

	public String gettIMESTAMP() {
		return tIMESTAMP;
	}

	public String getCRRELATIONID() {
		return CRRELATIONID;
	}

	public String getVERSION() {
		return VERSION;
	}

	public String getBUILD() {
		return BUILD;
	}

	public void setACK(String aCK) {
		ACK = aCK;
	}

	public void settIMESTAMP(String tIMESTAMP) {
		this.tIMESTAMP = tIMESTAMP;
	}

	public void setCRRELATIONID(String cRRELATIONID) {
		CRRELATIONID = cRRELATIONID;
	}

	public void setVERSION(String vERSION) {
		VERSION = vERSION;
	}

	public void setBUILD(String bUILD) {
		BUILD = bUILD;
	}

	public String getTOKEN() {
		return TOKEN;
	}

	public void setTOKEN(String tOKEN) {
		TOKEN = tOKEN;
	}
}
