package com.pay.KqBill.bean;

import com.pay.utils.PropUtil;

/**
 * @TITLE KqConfig.java
 * @NAME 快钱配置参数
 * @DATE 2017-1-23
 */
public class KqConfig {

	/** 是否绝对路径 */
	private static boolean absolutePath = false;
	/** 证书路径 */
	private static String pfxPath = "";
	/** 证书密钥 */
	private static String keyPwd = "";

	static {
		absolutePath = Boolean.parseBoolean(PropUtil.get("kq_ap"));
		pfxPath = PropUtil.get("kq_pfxPath");
		keyPwd = PropUtil.get("kq_keyPwd");
	}

	public static boolean isAbsolutePath() {
		return absolutePath;
	}

	public static void setAbsolutePath(boolean absolutePath) {
		KqConfig.absolutePath = absolutePath;
	}

	public static String getPfxPath() {
		return pfxPath;
	}

	public static void setPfxPath(String pfxPath) {
		KqConfig.pfxPath = pfxPath;
	}

	public static String getKeyPwd() {
		return keyPwd;
	}

	public static void setKeyPwd(String keyPwd) {
		KqConfig.keyPwd = keyPwd;
	}
}
