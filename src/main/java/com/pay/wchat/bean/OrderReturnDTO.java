package com.pay.wchat.bean;

/**
 * @TITLE OrderReturnDTO.java
 * @NAME 微信统一下单返回结果
 * @DATE 2017-1-14
 */
public class OrderReturnDTO {

	/** 公众账号ID */
	private String appid;
	/** 银行类型 */
	private String bank_type;
	/** 现金支付金额 */
	private String cash_fee;
	/** 货币种类 */
	private String fee_type;
	/** 是否关注公众账号 */
	private String is_subscribe;
	/** 商户号(partnerid) */
	private String mch_id;
	/** 随机字符串 */
	private String nonce_str;
	/** 用户标识 */
	private String openid;
	/** 订单号 */
	private String out_trade_no;
	/** 业务结果 */
	private String result_code;
	/** 返回状态码 */
	private String return_code;
	/** 签名 */
	private String sign;
	/** 子商户公众号 */
	private String sub_appid;
	/** 是否关注子公众账号 */
	private String sub_is_subscribe;
	/** 子商户号 */
	private String sub_mch_id;
	/** 用户在子商户公众号中的标识 */
	private String sub_openid;
	/** 支付完成时间 */
	private String time_end;
	/** 总金额 */
	private String total_fee;
	/** 交易类型 */
	private String trade_type;
	/** 微信支付订单号 */
	private String transaction_id;
	/** 返回信息 */
	private String return_msg;
	/** 设备号 */
	private String device_info;
	/** 错误代码 */
	private String err_code;
	/** 错误代码描述 */
	private String err_code_des;
	/** 预支付交易会话标识 */
	private String prepay_id;
	/** 二维码链接 */
	private String code_url;

	/**
	 * 返回状态码
	 */
	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	/**
	 * 返回信息
	 */
	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	/**
	 * 商户公众账号ID
	 */
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * 商户号(partnerid)
	 */
	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	/**
	 * 设备号
	 */
	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	/**
	 * 随机字符串
	 */
	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	/**
	 * 签名
	 */
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * 业务结果
	 */
	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	/**
	 * 错误代码
	 */
	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	/**
	 * 错误代码描述
	 */
	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	/**
	 * 交易类型
	 */
	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	/**
	 * 预支付交易会话标识
	 */
	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	/**
	 * 二维码链接
	 */
	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}

	/**
	 * 子商户号
	 */
	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	/**
	 * 子商户公众号
	 */
	public String getSub_appid() {
		return sub_appid;
	}

	public void setSub_appid(String sub_appid) {
		this.sub_appid = sub_appid;
	}

	/**
	 * 获取订单号
	 */
	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * 获取银行类型
	 */
	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	/**
	 * 获取现金支付金额
	 */
	public String getCash_fee() {
		return cash_fee;
	}

	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}

	/**
	 * 获取货币种类
	 */
	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	/**
	 * 获取是否关注公众账号
	 */
	public String getIs_subscribe() {
		return is_subscribe;
	}

	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	/**
	 * 获取用户标识
	 */
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * 获取是否关注子公众账号
	 */
	public String getSub_is_subscribe() {
		return sub_is_subscribe;
	}

	public void setSub_is_subscribe(String sub_is_subscribe) {
		this.sub_is_subscribe = sub_is_subscribe;
	}

	/**
	 * 获取用户子标识
	 */
	public String getSub_openid() {
		return sub_openid;
	}

	public void setSub_openid(String sub_openid) {
		this.sub_openid = sub_openid;
	}

	/**
	 * 获取支付完成时间
	 */
	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	/**
	 * 获取总金额
	 */
	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	/**
	 * 获取微信支付订单号
	 */
	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	@Override
	public String toString() {
		return "OrderReturn [appid=" + appid + ", bank_type=" + bank_type + ", cash_fee=" + cash_fee + ", fee_type=" + fee_type
				+ ", is_subscribe=" + is_subscribe + ", mch_id=" + mch_id + ", nonce_str=" + nonce_str + ", openid=" + openid
				+ ", out_trade_no=" + out_trade_no + ", result_code=" + result_code + ", return_code=" + return_code + ", sign="
				+ sign + ", sub_appid=" + sub_appid + ", sub_is_subscribe=" + sub_is_subscribe + ", sub_mch_id=" + sub_mch_id
				+ ", sub_openid=" + sub_openid + ", time_end=" + time_end + ", total_fee=" + total_fee + ", trade_type=" + trade_type
				+ ", transaction_id=" + transaction_id + ", return_msg=" + return_msg + ", device_info=" + device_info + ", err_code="
				+ err_code + ", err_code_des=" + err_code_des + ", prepay_id=" + prepay_id + ", code_url=" + code_url + "]";
	}
}
