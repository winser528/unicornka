package com.pay.paypal.services;

import java.util.HashMap;
import java.util.Map;

import com.pay.paypal.config.PaypalHandle;

/**
 * @TITLE PalNvpService.java
 * @NAME 快速支付服务
 * @DATE 2017-1-24
 */
public class PalNvpService {

	/**
	 * 开始结账
	 *
	 * @param checkoutDetails
	 *            基本参数
	 * @param returnURL
	 *            付款后页面
	 * @param cancelURL
	 *            请求失败页面
	 */
	public static HashMap<String, String> SetExpressCheckout(Map<String, String> checkoutDetails, String returnURL, String cancelURL) {

		StringBuilder nvpstr = new StringBuilder("");
		if (PaypalHandle.isSet(checkoutDetails.get("PAYMENTREQUEST_0_AMT"))) {
			nvpstr.append("&PAYMENTREQUEST_0_AMT=").append(checkoutDetails.get("PAYMENTREQUEST_0_AMT"));
		}
		// if(PaypalHandle.isSet(checkoutDetails.get("paymentType"))){
		// nvpstr.append(
		// "&PAYMENTREQUEST_0_PAYMENTACTION=").append(checkoutDetails.get("paymentType");
		// }

		if (PaypalHandle.isSet(returnURL))
			nvpstr.append("&RETURNURL=").append(returnURL);

		if (PaypalHandle.isSet(cancelURL))
			nvpstr.append("&CANCELURL=").append(cancelURL);

		// nvpstr.append(
		// "&PAYMENTREQUEST_0_SELLERPAYPALACCOUNTID=").append(this.getSellerEmail();

		// Optional parameters for SetExpressCheckout API call(可选参数)
		if (PaypalHandle.isSet(checkoutDetails.get("currencyCodeType"))) {// 货币代码类型
			nvpstr.append("&PAYMENTREQUEST_0_CURRENCYCODE=").append(checkoutDetails.get("currencyCodeType"));
		}

		if (PaypalHandle.isSet(checkoutDetails.get("PAYMENTREQUEST_0_ITEMAMT"))) {// 付款请求金额
			nvpstr.append("&PAYMENTREQUEST_0_ITEMAMT=").append(checkoutDetails.get("PAYMENTREQUEST_0_ITEMAMT"));
		}

		if (PaypalHandle.isSet(checkoutDetails.get("PAYMENTREQUEST_0_TAXAMT"))) {// 税收金额
			nvpstr.append("&PAYMENTREQUEST_0_TAXAMT=").append(checkoutDetails.get("PAYMENTREQUEST_0_TAXAMT"));
		}

		if (PaypalHandle.isSet(checkoutDetails.get("PAYMENTREQUEST_0_SHIPPINGAMT"))) {// 发货数量
			nvpstr.append("&PAYMENTREQUEST_0_SHIPPINGAMT=").append(checkoutDetails.get("PAYMENTREQUEST_0_SHIPPINGAMT"));
		}

		if (PaypalHandle.isSet(checkoutDetails.get("PAYMENTREQUEST_0_HANDLINGAMT"))) {// 手续费总额
			nvpstr.append("&PAYMENTREQUEST_0_HANDLINGAMT=").append(checkoutDetails.get("PAYMENTREQUEST_0_HANDLINGAMT"));
		}

		if (PaypalHandle.isSet(checkoutDetails.get("PAYMENTREQUEST_0_SHIPDISCAMT"))) {// 折扣金额
			nvpstr.append("&PAYMENTREQUEST_0_SHIPDISCAMT=").append(checkoutDetails.get("PAYMENTREQUEST_0_SHIPDISCAMT"));
		}

		if (PaypalHandle.isSet(checkoutDetails.get("PAYMENTREQUEST_0_INSURANCEAMT"))) {// 保险金额
			nvpstr.append("&PAYMENTREQUEST_0_INSURANCEAMT=").append(checkoutDetails.get("PAYMENTREQUEST_0_INSURANCEAMT"));
		}

		if (PaypalHandle.isSet(checkoutDetails.get("L_PAYMENTREQUEST_0_NAME0")))// 商品名称
			nvpstr.append("&L_PAYMENTREQUEST_0_NAME0=").append(checkoutDetails.get("L_PAYMENTREQUEST_0_NAME0"));

		if (PaypalHandle.isSet(checkoutDetails.get("L_PAYMENTREQUEST_0_NUMBER0")))// 商品编号
			nvpstr.append("&L_PAYMENTREQUEST_0_NUMBER0=").append(checkoutDetails.get("L_PAYMENTREQUEST_0_NUMBER0"));

		if (PaypalHandle.isSet(checkoutDetails.get("L_PAYMENTREQUEST_0_DESC0")))// 商品描述
			nvpstr.append("&L_PAYMENTREQUEST_0_DESC0=").append(checkoutDetails.get("L_PAYMENTREQUEST_0_DESC0"));

		if (PaypalHandle.isSet(checkoutDetails.get("PAYMENTREQUEST_0_ITEMAMT")))// 价格
			nvpstr.append("&L_PAYMENTREQUEST_0_AMT0=").append(checkoutDetails.get("PAYMENTREQUEST_0_ITEMAMT"));

		if (PaypalHandle.isSet(checkoutDetails.get("L_PAYMENTREQUEST_0_QTY0")))// 数量
			nvpstr.append("&L_PAYMENTREQUEST_0_QTY0=").append(checkoutDetails.get("L_PAYMENTREQUEST_0_QTY0"));

		if (PaypalHandle.isSet(checkoutDetails.get("LOGOIMG")))// 显示的LOGO
			nvpstr.append("&LOGOIMG=" + checkoutDetails.get("LOGOIMG"));

		return PaypalHandle.httpcall("SetExpressCheckout", nvpstr.toString());
	}

	/**
	 * 获取付款人信息
	 *
	 * @param token
	 *            SetExpressCheckout响应值
	 */
	public static HashMap<String, String> GetExpressCheckoutDetails(String token) {
		String method = "GetExpressCheckoutDetails";
		/*
		 * Build a second API request to PayPal, using the token as the ID to
		 * get the details on the payment authorization
		 */
		String nvpstr = "&TOKEN=" + token;

		/*
		 * Make the API call and store the results in an array. If the call was
		 * a success, show the authorization details, and provide an action to
		 * complete the payment. If failed, show the error
		 */
		return PaypalHandle.httpcall(method, nvpstr);
	}
}
