package com.pay.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * @TITLE LogProUtils.java
 * @NAME 交易日志专业工具
 * @DATE 2017-1-11
 */
public class LogProUtils {

	/** 配置文件 */
	private static Properties p = new Properties();
	/** 读取的配置文件名 */
	private static final String propertiesFileName = "paylog.properties";
	/** 路径配置方式 on:绝对路径，off:相对路径,默认绝对路径 （相对路径是相对于应用服务运行时的WEB-INF/classes目录） */
	private static final String absoluPathOFF = "off";
	/** 绝对路径开关 */
	public static final String absoluPathFlag;
	/** 日志级别,debug开启或关闭,默认关闭 */
	public static final String logLevel;
	/** 日志生成路径 */
	public static final String logPath;
	/** classPath根开始查找 */
	private static String fixPath;
	/** 对应的操作系统 */
	private static String OS = System.getProperty("os.name").toLowerCase();

	public static final String fileDownloadPath = getAbsolutePath(p.getProperty("fileDownloadPath"));

	static {
		try {
			debug("加载日志配置文件...........Start..........");
			fixPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			fixPath = URLDecoder.decode(fixPath, "utf-8");
			if ((fixPath.startsWith("/")) && (OS.indexOf("linux") < 0)) {
				fixPath = fixPath.substring(1);
			}

			InputStream inputStream = LogProUtils.class.getResourceAsStream("/" + propertiesFileName);

			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
			p.load(bf);
			inputStream.close();
			debug("加载日志配置文件..........End..........");
		} catch (Throwable e) {
			error("加载日志配置文件失败", e);
		}

		absoluPathFlag = p.getProperty("absoluPathFlag");
		logPath = getAbsolutePath(p.getProperty("logPath"));
		logLevel = p.getProperty("logLevel");
	}

	private static String getAbsolutePath(String path) {
		if (absoluPathOFF.equals(absoluPathFlag)) {
			String outPath = fixPath;
			path = path.replaceAll("\\\\", "/");
			path = path.replaceAll("/\\./", "/");
			if (path.startsWith("./"))
				path = path.substring(2);
			if (path.startsWith("/"))
				path = path.substring(1);
			if (path.startsWith("../")) {
				String[] inStr = path.split("\\.\\./");
				int count = inStr.length - 1;
				int i = 0;
				while (i <= count) {
					int lastFirst = outPath.lastIndexOf('/');
					outPath = outPath.substring(0, lastFirst);
					i++;
				}
				outPath = outPath + "/" + inStr[count];
			} else {
				outPath = outPath + path;
			}
			System.out.println("UP得到绝对路径：====>" + outPath);
			return outPath;
		}
		System.out.println("DOWN得到绝对路径：====>" + path);
		return path;
	}

	/**
	 * 生成文件
	 *
	 * @param msgCode
	 *            消息类型码
	 * @param filedata
	 *            信息流数据
	 */
	public static String buildfile(String msgCode, byte[] filedata) throws Exception {
		String filePath = "";
		try {
			String path = fileDownloadPath;
			if (PayCommonUtil.isNotNull(path)) {
				String nowTime = PayDateUtil.getNowDate("");
				String mselTime = PayDateUtil.getNowDate(PayDateUtil.MSEL_STR_FORMAT);
				path = path + nowTime;
				createDir(path);
				filePath = path + File.separator + msgCode + "_" + nowTime + "_" + mselTime + ".txt";

				File file = new File(filePath);
				FileOutputStream outputStream = new FileOutputStream(file);
				outputStream.write(filedata);
			}
		} catch (Exception e) {
			throw new Exception("保存文件[" + filePath + "]失败");
		}
		return filePath;
	}

	/**
	 * 日志消息
	 */
	public static void msg(String sWord) {
		logWriter(sWord, "msg");
	}

	/**
	 * Debug日志
	 */
	public static void debug(String sWord) {
		if ("debug".equals(logLevel))
			logWriter(sWord, "debug");
	}

	/**
	 * 错误日志
	 */
	public static void error(String errorMessage) {
		logWriter(errorMessage, "error");
	}

	/**
	 * 错误日志
	 *
	 * @param sWord
	 *            消息内容
	 * @param t
	 *            异常方法
	 */
	public static void error(String sWord, Throwable t) {
		logWriter(sWord + "\r\n" + t.toString(), "error");
	}

	/**
	 * 日志输出到文件
	 *
	 * @param sWord
	 *            内容
	 * @param level
	 *            日志级别
	 */
	private static void logWriter(String sWord, String level) {
		String logRoot = logPath;
		if (PayCommonUtil.isNotNull(logRoot)) {
			String logPath = logRoot + File.separator + PayDateUtil.getNowDate("");
			createDir(logPath);
			String nowTime = PayDateUtil.getNowDate("");
			String logName = logPath + File.separator + nowTime + "_" + level + ".log";
			FileWriter writer = null;
			try {
				writer = new FileWriter(logName, true);
				writer.write("\r\nLOG[ " + PayDateUtil.getNowDate(PayDateUtil.TIMEF_FORMAT) + "]\r\n" + sWord);
				writer.close();
			} catch (Exception e) {
				error("保存日志文件[" + logName + "]失败", e);
			}
		}
	}

	/**
	 * 创建目录
	 */
	private static void createDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			if (!path.endsWith(File.separator))
				path = path + File.separator;
			if (!dir.mkdirs())
				error("创建目录[" + path + "]失败");
		}
	}
}
