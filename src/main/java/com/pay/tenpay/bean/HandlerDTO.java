package com.pay.tenpay.bean;

import java.util.SortedMap;
import java.util.TreeMap;

import com.pay.utils.PropUtil;

/**
 * @TITLE HandlerDTO.java
 * @NAME 处理实体
 * @DATE 2017-1-14
 */
public class HandlerDTO {

	/** 密钥 */
	private String key;
	/** 所有数据 */
	private SortedMap<String, String> parameters;
	/** DeBuy信息 */
	private String debugInfo;

	public HandlerDTO() {
		this.key = PropUtil.get("tenpay_key");
		this.parameters = new TreeMap<String, String>();
		this.debugInfo = "";
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public SortedMap<String, String> getAllParameters() {
		return this.parameters;
	}

	public String getParameter(String parameter) {
		String s = this.parameters.get(parameter);
		return s == null ? "" : s;
	}

	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if (parameterValue != null) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}

	public String getDebugInfo() {
		return debugInfo;
	}

	public void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}
}
