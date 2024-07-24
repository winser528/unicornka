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
 * @NAME ������־רҵ����
 * @DATE 2017-1-11
 */
public class LogProUtils {

	/** �����ļ� */
	private static Properties p = new Properties();
	/** ��ȡ�������ļ��� */
	private static final String propertiesFileName = "paylog.properties";
	/** ·�����÷�ʽ on:����·����off:���·��,Ĭ�Ͼ���·�� �����·���������Ӧ�÷�������ʱ��WEB-INF/classesĿ¼�� */
	private static final String absoluPathOFF = "off";
	/** ����·������ */
	public static final String absoluPathFlag;
	/** ��־����,debug������ر�,Ĭ�Ϲر� */
	public static final String logLevel;
	/** ��־����·�� */
	public static final String logPath;
	/** classPath����ʼ���� */
	private static String fixPath;
	/** ��Ӧ�Ĳ���ϵͳ */
	private static String OS = System.getProperty("os.name").toLowerCase();

	public static final String fileDownloadPath = getAbsolutePath(p.getProperty("fileDownloadPath"));

	static {
		try {
			debug("������־�����ļ�...........Start..........");
			fixPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			fixPath = URLDecoder.decode(fixPath, "utf-8");
			if ((fixPath.startsWith("/")) && (OS.indexOf("linux") < 0)) {
				fixPath = fixPath.substring(1);
			}

			InputStream inputStream = LogProUtils.class.getResourceAsStream("/" + propertiesFileName);

			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
			p.load(bf);
			inputStream.close();
			debug("������־�����ļ�..........End..........");
		} catch (Throwable e) {
			error("������־�����ļ�ʧ��", e);
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
			System.out.println("UP�õ�����·����====>" + outPath);
			return outPath;
		}
		System.out.println("DOWN�õ�����·����====>" + path);
		return path;
	}

	/**
	 * �����ļ�
	 * 
	 * @param msgCode
	 *            ��Ϣ������
	 * @param filedata
	 *            ��Ϣ������
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
			throw new Exception("�����ļ�[" + filePath + "]ʧ��");
		}
		return filePath;
	}

	/**
	 * ��־��Ϣ
	 */
	public static void msg(String sWord) {
		logWriter(sWord, "msg");
	}

	/**
	 * Debug��־
	 */
	public static void debug(String sWord) {
		if ("debug".equals(logLevel))
			logWriter(sWord, "debug");
	}

	/**
	 * ������־
	 */
	public static void error(String errorMessage) {
		logWriter(errorMessage, "error");
	}

	/**
	 * ������־
	 * 
	 * @param sWord
	 *            ��Ϣ����
	 * @param t
	 *            �쳣����
	 */
	public static void error(String sWord, Throwable t) {
		logWriter(sWord + "\r\n" + t.toString(), "error");
	}

	/**
	 * ��־������ļ�
	 * 
	 * @param sWord
	 *            ����
	 * @param level
	 *            ��־����
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
				error("������־�ļ�[" + logName + "]ʧ��", e);
			}
		}
	}

	/**
	 * ����Ŀ¼
	 */
	private static void createDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			if (!path.endsWith(File.separator))
				path = path + File.separator;
			if (!dir.mkdirs())
				error("����Ŀ¼[" + path + "]ʧ��");
		}
	}
}
