package com.pay.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @TITLE MD5Util.java
 * @NAME MD5加密工具类
 * @DATE 2017-1-13
 */
public class MD5Util {

	/** 默认密钥 */
	private static String DEFAULT_KEY = "";
	/** UTF-8编码格式 */
	private static String DEFAULT_CODING = "utf-8";
	/** 十六进制数字 */
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 十六进制字节转字符串
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 十六进制的字节数组转字符串
	 */
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	/**
	 * 自定义MD5编码
	 */
	public static String MD5Encode(String str, String encoding) {
		String resultString = new String(str);
		try {
			// 摘要算法加密
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (encoding == null || "".equals(encoding))
				// 使用指定的 byte 数组对摘要进行最后更新，然后完成摘要计算。
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(encoding)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	/**
	 * 自定义MD5解码
	 */
	public static byte[] strMD5byte(String str) {
		byte[] msg = str.getBytes();
		MessageDigest md = null;
		try {
			// 摘要算法加密
			md = MessageDigest.getInstance("MD5");
			// 重置摘要以供再次使用
			md.reset();
			// 使用指定的 byte 数组更新摘要
			md.update(msg);
		} catch (NoSuchAlgorithmException e) {
		}
		return md.digest();
	}

	/**
	 * 字符串默认无密钥MD5加密
	 */
	public static String md5(String strSrc) {
		return md5(strSrc, DEFAULT_KEY, DEFAULT_CODING);
	}

	/**
	 * 字符串默认有密钥MD5加密
	 */
	public static String md5(String strSrc, String key) {
		return md5(strSrc, key, DEFAULT_CODING);
	}

	/**
	 * 根据密钥和编码将字符串MD5加密
	 */
	public static String md5(String strSrc, String key, String encoding) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] b = strSrc.getBytes(encoding);

			md5.update(b);

			String result = "";
			byte[] temp = md5.digest(key.getBytes(encoding));
			String s = "";
			for (byte bb : temp) {
				s = s + bb + " ";
			}

			for (int i = 0; i < temp.length; i++) {
				result = result + Integer.toHexString(0xFF & temp[i] | 0xFFFFFF00).substring(6);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
