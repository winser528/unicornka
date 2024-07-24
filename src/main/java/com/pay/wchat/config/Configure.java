package com.pay.wchat.config;

import com.pay.utils.PayCommonUtil;
import com.pay.utils.PropUtil;

/**
 * 各种配置数据
 */
public class Configure {

	// 这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
	// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
	// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改
	private static String key = "";// 微信商户API的密钥
	// 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	private static String mchID = "";
	// 微信分配的公众号ID（开通公众号之后可以获取到）
	private static String appID = "";
	// 微信公众号中的密钥
	private static String appkey = "";
	// 受理模式下给子商户分配的子商户号
	private static String subMchID = "";
	// 受理模式下给子商户分配的子商户公众号
	private static String subAppID = "";
	// HTTPS证书的本地路径
	private static String certLocalPath = "";
	// HTTPS证书密码，默认密码等于商户号MCHID
	private static String certPassword = "";
	// 是否使用异步线程的方式来上报API测速，默认为异步模式
	private static boolean useThreadToDoReport = true;
	// 机器IP
	private static String ip = "127.0.0.1";
	// 子公众号key
	private static String subAppkey = "";
	// 回调地址
	private static String notifyUrl = "";

	static {
		key = PropUtil.get("wchat_key");
		mchID = PropUtil.get("wchat_mchID");
		appID = PropUtil.get("wchat_appID");
		appkey = PropUtil.get("wchat_appkey");
		subMchID = PropUtil.get("wchat_subMchID");
		subAppID = PropUtil.get("wchat_subAppID");
		certLocalPath = PropUtil.get("wchat_certLocalPath");
		certPassword = PropUtil.get("wchat_certPassword");
		useThreadToDoReport = PayCommonUtil.null2Boolean(PropUtil.get("wchat_useThreadToDoReport"));
		ip = PropUtil.get("wchat_ip");
		subAppkey = PropUtil.get("wchat_subAppkey");
		notifyUrl = PropUtil.get("wchat_notify_url");
	}

	// 以下是几个API的路径：
	/**
	 * // 1）被扫支付API
	 */
	public static String PAY_API = "https://api.mch.weixin.qq.com/pay/micropay";
	/**
	 * // 2）被扫支付查询API
	 */
	public static String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";
	/**
	 * // 3）退款API
	 */
	public static String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	/**
	 * // 4）退款查询API
	 */
	public static String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";
	/**
	 * // 5）撤销API
	 */
	public static String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";
	/**
	 * // 6）下载对账单API
	 */
	public static String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";
	/**
	 * // 7) 统计上报API
	 */
	public static String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";
	/**
	 * Https请求类名
	 */
	public static String HttpsRequestClassName = "";

	/** 微信统一下单类型：原生模式 */
	public static final String NATIVE = "NATIVE";
	/** 微信统一下单类型：H5模式 */
	public static final String JSAPI = "JSAPI";
	/** 微信统一下单类型：APP调起模式 */
	public static final String APP = "APP";

	/**
	 * 是否使用异步线程的方式来上报API测速，默认为异步模式(true)
	 */
	public boolean isUseThreadToDoReport() {
		return useThreadToDoReport;
	}

	public void setHttpsRequestClassName(String name) {
		HttpsRequestClassName = name;
	}

	/**
	 * 商户密钥
	 */
	public static String getKey() {
		return key;
	}

	/**
	 * 商户公众账号ID
	 */
	public static String getAppid() {
		return appID;
	}

	/**
	 * 商户号
	 */
	public static String getMchid() {
		return mchID;
	}

	/**
	 * 子商户号
	 */
	public static String getSubMchid() {
		return subMchID;
	}

	/**
	 * 子商户公众号
	 */
	public static String getSubAppId() {
		return subAppID;
	}

	/**
	 * HTTPS证书的本地路径
	 */
	public static String getCertLocalPath() {
		return certLocalPath;
	}

	/**
	 * HTTPS证书密码，默认密码等于商户号MCHID
	 */
	public static String getCertPassword() {
		return certPassword;
	}

	/**
	 * APP和网页支付提交用户端IP，Native支付填调用微信支付API的机器IP
	 */
	public static String getIP() {
		return ip;
	}

	/**
	 * HTTPS请求类名
	 */
	public static String getHttpsRequestClassName() {
		return HttpsRequestClassName;
	}

	/**
	 * 公众号密钥
	 */
	public static String getAppkey() {
		return appkey;
	}

	/**
	 * 子公众号key
	 */
	public String getSubAppkey() {
		return subAppkey;
	}

	/**
	 * 回调地址
	 */
	public static String getNotifyUrl() {
		return notifyUrl;
	}
}
