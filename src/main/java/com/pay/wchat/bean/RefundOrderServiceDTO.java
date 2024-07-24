package com.pay.wchat.bean;

/**
 * @TITLE RefundOrderServiceDTO.java
 * @NAME 查询退款请求
 * @DATE 2017-1-17
 */
public class RefundOrderServiceDTO extends OrderServiceDTO {

	private String out_refund_no;// 商户退款单号
	private String refund_id;// 微信退款单号

	public RefundOrderServiceDTO(String appid, String mch_id, String transaction_id, String out_trade_no, String out_refund_no,
								 String refund_id) {
		super(appid, mch_id, transaction_id, out_trade_no);
		this.out_refund_no = out_refund_no;
		this.refund_id = refund_id;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public String getRefund_id() {
		return refund_id;
	}

	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
}
