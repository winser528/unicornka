package com.pay.wchat.bean;

import java.lang.reflect.Field;
import java.util.SortedMap;
import java.util.TreeMap;

import com.pay.utils.PayCommonUtil;
import com.pay.utils.SignUtil;
import com.pay.wchat.config.Configure;

/**
 * @TITLE RefundReqMsgDTO.java
 * @NAME 退款申请信息
 * @DATE 2017-1-17
 */
public class RefundReqMsgDTO {

	private String appid = "";// 公众账号ID
	private String mch_id = "";// 商户号
	private String sub_mch_id = "";// 子商户号
	private String device_info = "";// 设备号
	private String nonce_str = "";// 随机字符串
	private String sign = "";// 签名
	private String sign_type = "";// 签名类型
	private String transaction_id = "";// 微信订单号
	private String out_trade_no = "";// 商户订单号
	private String out_refund_no = "";// 商户退款单号
	private int total_fee = 0;// 订单金额
	private int refund_fee = 0;// 退款金额
	private String refund_fee_type = "CNY";// 货币种类
	private String op_user_id = "";// 操作员帐号, 默认为商户号
	private String refund_account = "";// 退款资金来源

	/**
	 * 请求退款服务
	 *
	 * @param transactionID
	 *            是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。
	 *            建议优先使用
	 * @param outTradeNo
	 *            商户系统内部的订单号,transaction_id 、out_trade_no
	 *            二选一，如果同时存在优先级：transaction_id>out_trade_no
	 * @param deviceInfo
	 *            微信支付分配的终端设备号，与下单一致
	 * @param outRefundNo
	 *            商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
	 * @param totalFee
	 *            订单总金额，单位为分
	 * @param refundFee
	 *            退款总金额，单位为分,可以做部分退款
	 * @param opUserID
	 *            操作员帐号, 默认为商户号
	 * @param refundFeeType
	 *            货币类型，符合ISO 4217标准的三位字母代码，默认为CNY（人民币）
	 */
	public RefundReqMsgDTO(String transactionID, String outTradeNo, String outRefundNo, int totalFee, int refundFee, String opUserID,
						   String refundFeeType) {
		// 微信分配的公众号ID（开通公众号之后可以获取到）
		setAppid(Configure.getAppid());
		// 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
		setMch_id(Configure.getMchid());
		// 子商户ID
		setSub_mch_id(Configure.getSubMchid());
		// transaction_id是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。
		setTransaction_id(transactionID);
		// 商户系统自己生成的唯一的订单号
		setOut_trade_no(outTradeNo);
		// 商户退款单号
		setOut_refund_no(outRefundNo);
		// 订单金额
		setTotal_fee(totalFee);
		// 退款金额
		setRefund_fee(refundFee);
		// 操作员帐号, 默认为商户号
		setOp_user_id(opUserID);
		// 随机字符串，不长于32 位
		setNonce_str(PayCommonUtil.getRandomStringByLength(32));
		// 根据API给的签名规则进行签名
		String sign = SignUtil.TencentSign("utf-8", this.toMap(), Configure.getKey());
		setSign(sign);// 把签名数据设置到Sign这个属性中
	}

	/**
	 * 请求退款服务
	 *
	 * @param appid
	 *            公众号ID
	 * @param mch_id
	 *            商户号ID
	 * @param key
	 *            商户密钥
	 * @param transactionID
	 *            是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。
	 *            建议优先使用
	 * @param outTradeNo
	 *            商户系统内部的订单号,transaction_id 、out_trade_no
	 *            二选一，如果同时存在优先级：transaction_id>out_trade_no
	 * @param deviceInfo
	 *            微信支付分配的终端设备号，与下单一致
	 * @param outRefundNo
	 *            商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
	 * @param totalFee
	 *            订单总金额，单位为分
	 * @param refundFee
	 *            退款总金额，单位为分,可以做部分退款
	 * @param opUserID
	 *            操作员帐号, 默认为商户号
	 * @param refundFeeType
	 *            货币类型，符合ISO 4217标准的三位字母代码，默认为CNY（人民币）
	 */
	public RefundReqMsgDTO(String appid, String mch_id, String key, String transactionID, String outTradeNo, String outRefundNo,
						   int totalFee, int refundFee, String opUserID, String refundFeeType) {
		// 微信分配的公众号ID（开通公众号之后可以获取到）
		setAppid(appid);
		// 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
		setMch_id(mch_id);
		// transaction_id是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。
		setTransaction_id(transactionID);
		// 商户系统自己生成的唯一的订单号
		setOut_trade_no(outTradeNo);
		// 商户退款单号
		setOut_refund_no(outRefundNo);
		// 订单金额
		setTotal_fee(totalFee);
		// 退款金额
		setRefund_fee(refundFee);
		// 操作员帐号, 默认为商户号
		setOp_user_id(opUserID);
		// 随机字符串，不长于32 位
		setNonce_str(PayCommonUtil.getRandomStringByLength(32));
		// 根据API给的签名规则进行签名
		String sign = SignUtil.TencentSign("utf-8", this.toMap(), key);
		setSign(sign);// 把签名数据设置到Sign这个属性中
	}

	/**
	 * 公众账号ID
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * 商户号
	 */
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getSub_mch_id() {
		return sub_mch_id;
	}

	/**
	 * 设备号
	 */
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	/**
	 * 随机字符串
	 */
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	/**
	 * 签名
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * 签名类型
	 */
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	/**
	 * 微信订单号
	 */
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	/**
	 * 商户订单号
	 */
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * 商户退款单号
	 */
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	/**
	 * 订单金额
	 */
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	/**
	 * 退款金额
	 */
	public void setRefund_fee(int refund_fee) {
		this.refund_fee = refund_fee;
	}

	/**
	 * 货币种类
	 */
	public void setRefund_fee_type(String refund_fee_type) {
		this.refund_fee_type = refund_fee_type;
	}

	/**
	 * 操作员帐号, 默认为商户号
	 */
	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}

	/**
	 * 退款资金来源
	 */
	public void setRefund_account(String refund_account) {
		this.refund_account = refund_account;
	}

	/**
	 * 公众账号ID
	 */
	public String getAppid() {
		return appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public int getRefund_fee() {
		return refund_fee;
	}

	public String getRefund_fee_type() {
		return refund_fee_type;
	}

	public String getOp_user_id() {
		return op_user_id;
	}

	public String getRefund_account() {
		return refund_account;
	}

	@Override
	public String toString() {
		return "RefundReqMsgDTO [appid=" + appid + ", mch_id=" + mch_id + ", device_info=" + device_info + ", nonce_str=" + nonce_str
				+ ", sign=" + sign + ", sign_type=" + sign_type + ", transaction_id=" + transaction_id + ", out_trade_no="
				+ out_trade_no + ", out_refund_no=" + out_refund_no + ", total_fee=" + total_fee + ", refund_fee=" + refund_fee
				+ ", refund_fee_type=" + refund_fee_type + ", op_user_id=" + op_user_id + ", refund_account=" + refund_account + "]";
	}

	public SortedMap<String, Object> toMap() {
		SortedMap<String, Object> map = new TreeMap<String, Object>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			Object obj;
			try {
				obj = field.get(this);
				if (obj != null) {
					map.put(field.getName(), obj);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
