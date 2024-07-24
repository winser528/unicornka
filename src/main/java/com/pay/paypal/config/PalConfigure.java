package com.pay.paypal.config;

import com.pay.utils.PropUtil;

/**
 * @TITLE PalConfigure.java
 * @NAME PayPal配置参数
 * @DATE 2017-1-24
 */
public class PalConfigure {

	/** 付款请求路径 */
	public static String EXPRESSCHECKOUT;
	/** 获取token请求路径 */
	public static String NVPENDPOINT;
	/** PayPal API 用户名 */
	public static String buser;
	/** PayPal API 密码 */
	public static String bpwd;
	/** PayPal API 签名字符串。如果使用API证书，请勿包括该参数 */
	public static String bsignature;
	/** 支付按钮名称 */
	public static String buttonSource;
	/** API版本 */
	public static String bversion;

	static {
		String strSandbox = "";
		if ("true".equals(PropUtil.get("SANDBOX_FLAG"))) {
			strSandbox = "_SANDBOX";
		}
		EXPRESSCHECKOUT = PropUtil.get("PP_CHECKOUT_URL" + strSandbox);
		NVPENDPOINT = PropUtil.get("PP_NVP_ENDPOINT" + strSandbox);
		buser = PropUtil.get("PP_USER" + strSandbox);
		bpwd = PropUtil.get("PP_PASSWORD" + strSandbox);
		bsignature = PropUtil.get("PP_SIGNATURE" + strSandbox);
		buttonSource = PropUtil.get("SBN_CODE");
		bversion = PropUtil.get("API_VERSION");
		java.lang.System.setProperty("https.protocols", PropUtil.get("SSL_VERSION_TO_USE"));
	}
}
