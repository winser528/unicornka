package com.pay.utils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * @TITLE PayCommonUtil.java
 * @NAME 支付公共类工具
 * @DATE 2017-1-13
 */
public class PayCommonUtil {

	/**
	 * 参数不为空
	 */
	public static boolean isNotNull(Object s) {
		return (s != null) && (!s.toString().equals(""));
	}

	/**
	 * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;

		if (obj instanceof Collection)
			return ((Collection<?>) obj).isEmpty();

		if (obj instanceof Map)
			return ((Map<?, ?>) obj).isEmpty();

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}

	/**
	 * 不为空转换String
	 */
	public static String null2String(Object s) {
		return s == null ? "" : s.toString().trim();
	}

	/**
	 * 不为空转换为INT
	 */
	public static int null2Int(Object s) {
		int v = 0;
		if (s != null)
			try {
				v = Integer.parseInt(null2String(s));
			} catch (Exception localException) {
			}
		return v;
	}

	/**
	 * 不为空转换为Float
	 */
	public static float null2Float(Object s) {
		float v = 0.0F;
		if (s != null)
			try {
				v = Float.parseFloat(null2String(s));
			} catch (Exception localException) {
			}
		return v;
	}

	/**
	 * 不为空转换为Double
	 */
	public static double null2Double(Object s) {
		double v = 0.0D;
		if (s != null)
			try {
				v = Double.parseDouble(null2String(s));
			} catch (Exception localException) {
			}
		return v;
	}

	/**
	 * 不为空转换Long
	 */
	public static Long null2Long(Object s) {
		Long v = Long.valueOf(-1L);
		if (s != null)
			try {
				v = Long.valueOf(Long.parseLong(s.toString()));
			} catch (Exception localException) {
			}
		return v;
	}

	/**
	 * 不为空转换BigDecimal
	 */
	public static BigDecimal null2BigDecimal(Object s) {
		BigDecimal v = BigDecimal.ZERO;
		if (s != null)
			try {
				v = new BigDecimal(null2String(s));
			} catch (Exception localException) {
			}
		return v;
	}

	/**
	 * 不为空转换为做判断返回的字符串参数表示的布尔值
	 */
	public static boolean null2Boolean(Object s) {
		boolean v = false;
		if (s != null)
			try {
				v = Boolean.parseBoolean(null2String(s));
			} catch (Exception localException) {
			}
		return v;
	}

	/**
	 * 不为空转换为时间类型
	 */
	public static Date null2Date(Object s) {
		Date d = null;
		if (s != null)
			try {
				d = PayDateUtil.getDate(PayDateUtil.TIMEF_FORMAT, null2String(s));
			} catch (Exception e) {
				d = new Date();
			}
		return d;
	}

	/**
	 * 根据分割符号获取分隔符前的字符串
	 */
	public static String substringBeforeLast(String str, String separator) {
		if (isNullOrEmpty(str) || (isNullOrEmpty(separator))) {
			return str;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * 获取一定长度的随机字符串
	 *
	 * @param length
	 *            指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String getRandomStringByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
