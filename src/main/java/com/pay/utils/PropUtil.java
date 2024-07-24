package com.pay.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

/**
 * @TITLE PropUtil.java
 * @NAME 从类路径加载属性文件或文件对象
 * @DATE 2017-1-13
 */
public class PropUtil {

	private static Properties properties = new Properties();
	private static String DEFAULT_ENCODING = "UTF-8";

	/**
	 * 读取properties配置文件信息
	 */
	static {
		try {
			properties.load(PropUtil.class.getClassLoader().getResourceAsStream("alipay.properties"));
			properties.load(PropUtil.class.getClassLoader().getResourceAsStream("paycard.properties"));
			properties.load(PropUtil.class.getClassLoader().getResourceAsStream("paypal.properties"));
			properties.load(PropUtil.class.getClassLoader().getResourceAsStream("tenpay.properties"));
			properties.load(PropUtil.class.getClassLoader().getResourceAsStream("unionpay.properties"));
			properties.load(PropUtil.class.getClassLoader().getResourceAsStream("wachat.properties"));
			properties.load(PropUtil.class.getClassLoader().getResourceAsStream("yspay.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prop constructor.
	 *
	 * @see #PropUtil(String, String)
	 */
	public PropUtil(String fileName) {
		this(fileName, DEFAULT_ENCODING);
	}

	/**
	 * Prop constructor
	 * <p>
	 * Example:<br>
	 * Prop prop = new Prop("my_config.txt", "UTF-8");<br>
	 * String userName = prop.get("userName");<br>
	 * <br>
	 *
	 * prop = new Prop("com/jfinal/file_in_sub_path_of_classpath.txt", "UTF-8");
	 * <br>
	 * String value = prop.get("key");
	 *
	 * @param fileName
	 *            the properties file's name in classpath or the sub directory
	 *            of classpath
	 * @param encoding
	 *            the encoding
	 */
	public PropUtil(String fileName, String encoding) {
		InputStream inputStream = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName); // properties.load(Prop.class.getResourceAsStream(fileName));
			if (inputStream == null)
				throw new IllegalArgumentException("Properties file not found in classpath: " + fileName);
			properties = new Properties();
			properties.load(new InputStreamReader(inputStream, encoding));
		} catch (IOException e) {
			throw new RuntimeException("Error loading properties file.", e);
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * Prop constructor.
	 *
	 * @see #PropUtil(File, String)
	 */
	public PropUtil(File file) {
		this(file, DEFAULT_ENCODING);
	}

	/**
	 * Prop constructor
	 * <p>
	 * Example:<br>
	 * Prop prop = new Prop(new File("/var/config/my_config.txt"), "UTF-8");<br>
	 * String userName = prop.get("userName");
	 *
	 * @param file
	 *            the properties File object
	 * @param encoding
	 *            the encoding
	 */
	public PropUtil(File file, String encoding) {
		if (file == null)
			throw new IllegalArgumentException("File can not be null.");
		if (file.isFile() == false)
			throw new IllegalArgumentException("File not found : " + file.getName());

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			properties = new Properties();
			properties.load(new InputStreamReader(inputStream, encoding));
		} catch (IOException e) {
			throw new RuntimeException("Error loading properties file.", e);
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * 获取配置文件中的键的值
	 */
	public static String get(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 获取配置文件的值如果没有值则根据指定的默认值使用
	 */
	public String get(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public Integer getInt(String key) {
		return getInt(key, null);
	}

	public Integer getInt(String key, Integer defaultValue) {
		String value = properties.getProperty(key);
		if (value != null)
			return Integer.parseInt(value.trim());
		return defaultValue;
	}

	public Long getLong(String key) {
		return getLong(key, null);
	}

	public Long getLong(String key, Long defaultValue) {
		String value = properties.getProperty(key);
		if (value != null)
			return Long.parseLong(value.trim());
		return defaultValue;
	}

	public Boolean getBoolean(String key) {
		return getBoolean(key, null);
	}

	public Boolean getBoolean(String key, Boolean defaultValue) {
		String value = properties.getProperty(key);
		if (value != null) {
			value = value.toLowerCase().trim();
			if ("true".equals(value))
				return true;
			else if ("false".equals(value))
				return false;
			throw new RuntimeException("The value can not parse to Boolean : " + value);
		}
		return defaultValue;
	}

	public boolean containsKey(String key) {
		return properties.containsKey(key);
	}

	public Properties getProperties() {
		return properties;
	}

	/**
	 * 获取项目路径
	 */
	public static String getProjectPath(boolean isRoot) {
		String path = System.getProperty("user.dir");
		try {
			if (isRoot) {
				path.replace("bin", "webapps");
			} else {
				path += "\\";
			}
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * TODO 获取根目录
	 *
	 * @author PHeH
	 */
	public static String getRootPath() {
		// 因为类名为"Application"，因此" Application.class"一定能找到
		String result = PropUtil.class.getResource("PropUtil.class").toString();
		int index = result.indexOf("WEB-INF");
		if (index == -1) {
			index = result.indexOf("bin");
		}
		result = result.substring(0, index);
		if (result.startsWith("jar")) {
			// 当class文件在jar文件中时，返回"jar:file:/F:/ ..."样的路径
			result = result.substring(10);
		} else if (result.startsWith("file")) {
			// 当class文件在class文件中时，返回"file:/F:/ ..."样的路径
			result = result.substring(6);
		}
		if (result.endsWith("/"))
			result = result.substring(0, result.length() - 1);// 不包含最后的"/"
		return result;
	}

	/**
	 * 获取TOMCAT路径，可以根据指定的参数获取指定位置
	 */
	public static String getTomcatPath(HttpServletRequest request, String pathFile) {
		return request.getSession().getServletContext().getRealPath(pathFile);
	}
}
