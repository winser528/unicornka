package com.pay.wchat.bean;

import com.pay.utils.PayCommonUtil;
import com.pay.utils.SignUtil;
import com.pay.wchat.config.Configure;

/**
 * @TITLE WchatReqDTO.java
 * @NAME 微信下单数据
 * @DATE 2017-1-14
 */
public class UnifyReqDTO {

	private String appid;// 公众账号ID
	private String mch_id;// 商户号
	private String nonce_str;// 随机字符串
	private String sign;// 签名
	private String body;// 商品描述
	private String out_trade_no;// 商户订单号
	private int total_fee;// 订单总金额，单位为分
	private String spbill_create_ip;// APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	private String notify_url;// 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	private String trade_type;// 交易类型，取值如下：JSAPI，NATIVE，APP
	private String sub_mch_id;// 子商户号
	private String sub_appid;// 子商户公众号ID
	private String long_url;// 微信二维码长URL链接

	/**
	 * 统一下单子商户支付到商户
	 *
	 * @param body
	 *            商品描述
	 * @param out_trade_no
	 *            商户订单号
	 * @param total_fee
	 *            订单总金额，单位为分
	 * @param notify_url
	 *            回调地址
	 * @param trade_type
	 *            交易类型，取值如下：JSAPI，NATIVE，APP
	 */
	public UnifyReqDTO(String body, String out_trade_no, int total_fee, String notify_url, String trade_type) {
		// 微信分配的公众号ID（开通公众号之后可以获取到）
		setAppid(Configure.getAppid());
		// 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
		setMch_id(Configure.getMchid());
		// 随机字符串
		setNonce_str(PayCommonUtil.getRandomStringByLength(32));
		// 商品描述
		setBody(body);
		// 商户订单号
		setOut_trade_no(out_trade_no);
		// 订单总金额，单位为分
		setTotal_fee(total_fee);
		// 用户端ip
		setSpbill_create_ip(Configure.getIP());
		// 异步通知回调地址
		setNotify_url(notify_url);
		// 下单类型
		setTrade_type(trade_type);
		// 子商户公众号ID
		setSub_appid(Configure.getSubAppId());
		// 微信分配的子商户ID
		setSub_mch_id(Configure.getSubMchid());
		// 根据API给的签名规则进行签名
		String sign = SignUtil.TencentSign("utf-8", SignUtil.toMap(this, UnifyReqDTO.class), Configure.getKey());
		setSign(sign);// 把签名数据设置到Sign这个属性中
	}

	/**
	 * 普通商户统一下单
	 *
	 * @param appid
	 *            公众账号ID
	 * @param mch_id
	 *            商户号
	 * @param body
	 *            商品描述
	 * @param out_trade_no
	 *            商户订单号
	 * @param total_fee
	 *            订单总金额，单位为分
	 * @param notify_url
	 *            回调地址
	 * @param trade_type
	 *            交易类型，取值如下：JSAPI，NATIVE，APP
	 */
	public UnifyReqDTO(String appid, String mch_id, String key, String body, String out_trade_no, int total_fee, String notify_url,
					   String trade_type) {
		// 微信分配的公众号ID（开通公众号之后可以获取到）
		setAppid(appid);
		// 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
		setMch_id(mch_id);
		// 随机字符串
		setNonce_str(PayCommonUtil.getRandomStringByLength(32));
		// 商品描述
		setBody(body);
		// 商户订单号
		setOut_trade_no(out_trade_no);
		// 订单总金额，单位为分
		setTotal_fee(total_fee);
		// 用户端ip
		setSpbill_create_ip(Configure.getIP());
		// 异步通知回调地址
		setNotify_url(notify_url);
		// 下单类型
		setTrade_type(trade_type);
		// 子商户公众号ID
		setSub_appid(Configure.getSubAppId());
		// 微信分配的子商户ID
		setSub_mch_id(Configure.getSubMchid());
		// 根据API给的签名规则进行签名
		String sign = SignUtil.TencentSign("utf-8", SignUtil.toMap(this, UnifyReqDTO.class), key);
		setSign(sign);// 把签名数据设置到Sign这个属性中
	}

	/**
	 * 将微信扫码支付的长URL转换短地址
	 *
	 * @param appid
	 *            公众账号ID
	 * @param mch_id
	 *            商户号
	 * @param long_url
	 *            微信二维码长URL链接
	 */
	public UnifyReqDTO(String appid, String mch_id, String long_url) {
		// 微信分配的公众号ID（开通公众号之后可以获取到）
		setAppid(appid);
		// 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
		setMch_id(mch_id);
		this.long_url = long_url;
		setNonce_str(PayCommonUtil.getRandomStringByLength(32));
		// 根据API给的签名规则进行签名
		String sign = SignUtil.TencentSign("utf-8", SignUtil.toMap(this, OrderBillDTO.class), Configure.getKey());
		setSign(sign);// 把签名数据设置到Sign这个属性中
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	public String getSub_appid() {
		return sub_appid;
	}

	public void setSub_appid(String sub_appid) {
		this.sub_appid = sub_appid;
	}

	public String getLong_url() {
		return long_url;
	}

	public void setLong_url(String long_url) {
		this.long_url = long_url;
	}

	@Override
	public String toString() {
		return "微信下单数据：===> [appid=" + appid + ", mch_id=" + mch_id + ", nonce_str=" + nonce_str + ", sign=" + sign + ", body=" + body
				+ ", out_trade_no=" + out_trade_no + ", total_fee=" + total_fee + ", spbill_create_ip=" + spbill_create_ip
				+ ", notify_url=" + notify_url + ", trade_type=" + trade_type + ", sub_mch_id=" + sub_mch_id + ", sub_appid="
				+ sub_appid + "]";
	}
}
