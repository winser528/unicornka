package com.pay.alipay.config;

import com.pay.utils.PropUtil;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.2
 *日期：2011-03-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */
public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "";

	// 交易安全检验码，由数字和字母组成的32位字符串
	public static String key = "";

	// 签约支付宝账号
	public static String seller_email = "";

	// 支付宝服务器通知的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
	// 必须保证其地址能够在互联网中访问的到
	public static String notify_url = "";
	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 页面跳转同步通知页面路径
	public static String return_url = "";

	// 调试用，创建TXT日志路径
	public static String log_path = "";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "gbk";

	// 签名方式 不需修改
	public static String sign_type = "MD5";

	// 商户的私钥
	public static String private_key = "";

	// 支付宝的公钥
	public static String ali_public_key = "";

	static {
		partner = PropUtil.get("alipay_partner");
		key = PropUtil.get("alipay_key");
		seller_email = PropUtil.get("alipay_seller_email");
		notify_url = PropUtil.get("alipay_notify_url");
		return_url = PropUtil.get("alipay_return_url");
		log_path = PropUtil.get("alipay_log_path") + "alipay_log_" + System.currentTimeMillis() + ".txt";
		input_charset = PropUtil.get("alipay_input_charset");
		private_key = PropUtil.get("alipay_private_key");
		ali_public_key = PropUtil.get("alipay_public_key");
	}

	public static String getPartner() {
		return partner;
	}

	public static void setPartner(String partner) {
		AlipayConfig.partner = partner;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		AlipayConfig.key = key;
	}

	public static String getSeller_email() {
		return seller_email;
	}

	public static void setSeller_email(String seller_email) {
		AlipayConfig.seller_email = seller_email;
	}

	public static String getNotify_url() {
		return notify_url;
	}

	public static void setNotify_url(String notify_url) {
		AlipayConfig.notify_url = notify_url;
	}

	public static String getReturn_url() {
		return return_url;
	}

	public static void setReturn_url(String return_url) {
		AlipayConfig.return_url = return_url;
	}

	public static String getLog_path() {
		return log_path;
	}

	public static void setLog_path(String log_path) {
		AlipayConfig.log_path = log_path;
	}

	public static String getInput_charset() {
		return input_charset;
	}

	public static void setInput_charset(String input_charset) {
		AlipayConfig.input_charset = input_charset;
	}

	public static String getSign_type() {
		return sign_type;
	}

	public static void setSign_type(String sign_type) {
		AlipayConfig.sign_type = sign_type;
	}

	public static String getPrivate_key() {
		return private_key;
	}

	public static void setPrivate_key(String private_key) {
		AlipayConfig.private_key = private_key;
	}

	public static String getAli_public_key() {
		return ali_public_key;
	}

	public static void setAli_public_key(String ali_public_key) {
		AlipayConfig.ali_public_key = ali_public_key;
	}
}
