package com.pay.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @TITLE PayDateUtil.java
 * @NAME 支付用到的时间工具类
 * @DATE 2017-1-13
 */
public class PayDateUtil {

	// 默认显示日期的格式
	/** yyyyMMdd HH:mm:ss */
	public static final String DEFAULT_TIME = "yyyyMMdd HH:mm:ss";
	/** yyyy-MM-dd */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	/** HH:mm:ss */
	public static final String TIME = "HH:mm:ss";
	/** yyyy-MM-dd HH:mm:ss */
	public static final String TIMEF_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** 默认显示日期时间毫秒格式 yyyy-MM-dd HH:mm:ss.SSS */
	public static final String MSEL_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	/** 默认显示简体中文日期的格式yyyy年MM月dd日 */
	public static final String ZHCN_DATE_FORMAT = "yyyy年MM月dd日";
	/** 默认显示简体中文日期时间的格式yyyy年MM月dd日HH时mm分ss秒 */
	public static final String ZHCN_TIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";
	/** 默认显示简体中文日期时间毫秒格式yyyy年MM月dd日HH时mm分ss秒SSS毫秒 */
	public static final String ZHCN_MSEL_FORMAT = "yyyy年MM月dd日HH时mm分ss秒SSS毫秒";
	/** 获取日期串格式yyyyMMdd */
	public static final String DATE_STR_FORMAT = "yyyyMMdd";
	/** 月日MMdd */
	public static final String DATE_MM_DD = "MMdd";
	/** 获取日期时间串格式 yyyyMMddHHmmss */
	public static final String TIME_STR_FORMAT = "yyyyMMddHHmmss";
	/** 获取日期时间毫秒串格式yyyyMMddHHmmssSSS */
	public static final String MSEL_STR_FORMAT = "yyyyMMddHHmmssSSS";
	/** 格式化为 yyyy-MM-dd */
	private static DateFormat dateFormat = null;
	/** 格式化为 yyyy-MM-dd HH:mm:ss */
	private static DateFormat dateTimeFormat = null;
	/** 格式化为 简体中文日期的格式yyyy年MM月dd日 */
	private static DateFormat zhcnDateFormat = null;
	/** 格式化为 简体中文日期时间的格式yyyy年MM月dd日HH时mm分ss秒 */
	private static DateFormat zhcnDateTimeFormat = null;
	/** 格式化为 时间毫秒串格式yyyyMMddHHmmssSSS */
	private static DateFormat roundDateTimeFormat = null;
	/** 格式化为 时间串格式 yyyyMMddHHmmss */
	private static SimpleDateFormat timeStrFomat = null;
	static {
		dateFormat = new SimpleDateFormat(DATE_FORMAT);
		dateTimeFormat = new SimpleDateFormat(TIMEF_FORMAT);
		zhcnDateFormat = new SimpleDateFormat(ZHCN_DATE_FORMAT);
		zhcnDateTimeFormat = new SimpleDateFormat(ZHCN_TIME_FORMAT);
		roundDateTimeFormat = new SimpleDateFormat(MSEL_STR_FORMAT);
		timeStrFomat = new SimpleDateFormat(TIME_STR_FORMAT);
	}

	private static DateFormat getDateFormat(String formatStr) {
		if (formatStr.equalsIgnoreCase(DATE_FORMAT)) {
			return dateFormat;
		} else if (formatStr.equalsIgnoreCase(TIMEF_FORMAT)) {
			return dateTimeFormat;
		} else if (formatStr.equalsIgnoreCase(ZHCN_DATE_FORMAT)) {
			return zhcnDateFormat;
		} else if (formatStr.equalsIgnoreCase(ZHCN_TIME_FORMAT)) {
			return zhcnDateTimeFormat;
		} else if (formatStr.equalsIgnoreCase(MSEL_STR_FORMAT)) {
			return roundDateTimeFormat;
		} else if (formatStr.equalsIgnoreCase(TIME_STR_FORMAT)) {
			return timeStrFomat;
		} else {
			if (PayCommonUtil.isNotNull(formatStr)) {
				return new SimpleDateFormat(formatStr);
			} else {
				return dateTimeFormat;
			}
		}
	}

	/**
	 * 获取当前时间，如没有格式则使用'yyyyMMdd'
	 */
	public static String getNowDate(String formatStr) {
		DateFormat df = null;
		if (PayCommonUtil.isNotNull(formatStr)) {
			df = getDateFormat(formatStr);
		} else {
			df = new SimpleDateFormat(DATE_STR_FORMAT);
		}
		return df.format(new Date());
	}

	/**
	 * 按照默认formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
	 */
	public static Date getDate(String dateTimeStr, String formatStr) {
		try {
			if (PayCommonUtil.isNotNull(dateTimeStr)) {
				DateFormat sdf = getDateFormat(formatStr);
				return sdf.parse(dateTimeStr);
			}
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * 将Date转换成formatStr格式的字符串
	 */
	public static String dateToDateString(Date date, String formatStr) {
		DateFormat df = getDateFormat(formatStr);
		return df.format(date);
	}

}
