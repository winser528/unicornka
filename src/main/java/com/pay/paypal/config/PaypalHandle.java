package com.pay.paypal.config;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

/**
 * @TITLE PaypalHandle.java
 * @NAME 贝宝请求操作,发送及解析工具类
 * @DATE 2017-2-6
 */
public class PaypalHandle {

	public static boolean isSet(Object value) {
		return (value != null && value.toString().length() != 0);
	}

	/*********************************************************************************
	 * httpcall: Function to perform the API call to PayPal using API signature @
	 * methodName is name of API method. @ nvpStr is nvp string. returns a NVP
	 * string containing the response from the server.
	 *********************************************************************************
	 * <br>
	 * 向PAYPAL发送请求
	 *
	 * @param methodName
	 *            请求的类型名
	 * @param nvpStr
	 *            参数信息 例：&AA=BB&CC=DD
	 */
	public static HashMap<String, String> httpcall(String methodName, String nvpStr) {

		StringBuilder encodedData = new StringBuilder("METHOD=").append(methodName);
		encodedData.append("&VERSION=").append(PalConfigure.bversion).append("&PWD=").append(PalConfigure.bpwd);
		encodedData.append("&USER=").append(PalConfigure.buser).append("&SIGNATURE=").append(PalConfigure.bsignature).append(nvpStr);
		encodedData.append("&BUTTONSOURCE=").append(PalConfigure.buttonSource);
		// 返回信息
		HashMap<String, String> nvp = null;
		try {
			URL postURL = new URL(PalConfigure.NVPENDPOINT);
			HttpURLConnection conn = (HttpURLConnection) postURL.openConnection();
			// 设置连接参数
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// 发布的内容类型。模拟编码表单数据
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 请求的与实体对应的MIME信息
			conn.setRequestProperty("User-Agent", "Mozilla/4.0");// 浏览器类型
			conn.setRequestProperty("Content-Length", String.valueOf(encodedData.length()));// 请求的内容长度
			conn.setRequestMethod("POST");
			// 获得输出流
			DataOutputStream output = new DataOutputStream(conn.getOutputStream());
			output.writeBytes(encodedData.toString());
			output.flush();
			output.close();
			// 从输入流中读取输入
			int rc = conn.getResponseCode();
			if (rc != -1) {
				BufferedReader is = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder respText = new StringBuilder();
				String line = null;
				while (((line = is.readLine()) != null)) {
					respText.append(line);
				}
				nvp = deformatNVP(respText.toString());
			}
			return nvp;
		} catch (IOException e) {
			e.printStackTrace();
			// handle the error here
			return null;
		}
	}

	/*********************************************************************************
	 * deformatNVP: Function to break the NVP string into a HashMap pPayLoad is
	 * the NVP string. returns a HashMap object containing all the name value
	 * pairs of the string.
	 *********************************************************************************/
	public static HashMap<String, String> deformatNVP(String pPayload) {
		HashMap<String, String> nvp = new HashMap<String, String>();
		StringTokenizer stTok = new StringTokenizer(pPayload, "&");
		while (stTok.hasMoreTokens()) {
			StringTokenizer stInternalTokenizer = new StringTokenizer(stTok.nextToken(), "=");
			if (stInternalTokenizer.countTokens() == 2) {
				try {
					String key = URLDecoder.decode(stInternalTokenizer.nextToken(), "UTF-8");
					String value;
					value = URLDecoder.decode(stInternalTokenizer.nextToken(), "UTF-8");
					nvp.put(key.toUpperCase(), value);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return nvp;
	}

	/*********************************************************************************
	 * RedirectURL: Function to redirect the user to the PayPal site token is
	 * the parameter that was returned by PayPal returns a HashMap object
	 * containing all the name value pairs of the string.
	 *********************************************************************************/
	public void redirectURL(HttpServletResponse response, String token, boolean action) {
		String payPalURL = PalConfigure.EXPRESSCHECKOUT + token;
		if (action)
			payPalURL = payPalURL + "&useraction=commit";
		response.setStatus(302);
		response.setHeader("Location", payPalURL);
		response.setHeader("Connection", "close");
	}
}
