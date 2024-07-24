package com.pay.test;

import java.util.HashMap;
import java.util.Map;

import com.pay.KqBill.bean.KqReqDTO;
import com.pay.KqBill.services.KuaiQianService;
import com.pay.cardpay.bean.ChinaBankReqDTO;
import com.pay.cardpay.services.ChinaBankPayService;
import com.pay.paypal.services.PalNvpService;
import com.pay.utils.PayDateUtil;
import com.pay.wchat.bean.OrderReturnDTO;
import com.pay.wchat.config.Configure;
import com.pay.wchat.services.WchatUnifyService;

/**
 * @TITLE TestAction.java
 * @NAME 测试操作
 * @DATE 2017-1-19
 */
public class TestAction {

	public static void main(String[] args) throws Exception {
		// createWchat();
		// kuaiqian();
		// chinaBank();
		paypal();
	}

	/**
	 * 创建微信
	 */
	public static void createWchat() {
		try {
			OrderReturnDTO unifyPay = WchatUnifyService.unifyPay("test content", 1, "0001", Configure.NATIVE);
			System.out.println(unifyPay.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 快钱支付
	 */
	public static void kuaiqian() {
		KqReqDTO kq = new KqReqDTO("ceshi", 2, "1333333333", "201701231005", 1, PayDateUtil.getNowDate(PayDateUtil.TIME_STR_FORMAT),
				"商品名称", 1, "0001", "商品描述", "备注1", "备注2", "00", "");
		Map<String, Object> sign99Bill = KuaiQianService.sign99Bill(kq);
		String build99BillForm = KuaiQianService.build99BillForm(sign99Bill, "POST", "确认");
		System.out.println(build99BillForm);
	}

	/**
	 * 网银支付
	 */
	public static void chinaBank() {
		ChinaBankReqDTO cb = new ChinaBankReqDTO("0000124", 100, "备注可传参", "回调地址");
		Map<String, Object> param = ChinaBankPayService.getAssemblyData(cb);
		String bankWeb = ChinaBankPayService.getChinaBankWeb(param);
		System.out.println(bankWeb);
	}

	/**
	 * 贝宝支付
	 */
	public static void paypal() {
		Map<String, String> checkoutDetails = new HashMap<String, String>();
		checkoutDetails.put("L_PAYMENTREQUEST_0_NAME0", "DSLR Camera");
		checkoutDetails.put("L_PAYMENTREQUEST_0_NUMBER0", "A0123");
		checkoutDetails.put("L_PAYMENTREQUEST_0_DESC0", "Autofocus Camera");
		checkoutDetails.put("L_PAYMENTREQUEST_0_QTY0", "1");
		checkoutDetails.put("PAYMENTREQUEST_0_ITEMAMT", "10.00");
		checkoutDetails.put("PAYMENTREQUEST_0_TAXAMT", "2.00");
		checkoutDetails.put("PAYMENTREQUEST_0_SHIPPINGAMT", "5.00");
		checkoutDetails.put("PAYMENTREQUEST_0_HANDLINGAMT", "1.00");
		checkoutDetails.put("PAYMENTREQUEST_0_SHIPDISCAMT", "-3.00");
		checkoutDetails.put("PAYMENTREQUEST_0_INSURANCEAMT", "2.00");
		checkoutDetails.put("PAYMENTREQUEST_0_AMT", "17.00");
		checkoutDetails.put("LOGOIMG", "http://192.168.1.32:8989/checkout-mini-browser/img/logo.jpg");
		checkoutDetails.put("currencyCodeType", "USD");
		checkoutDetails.put("paymentType", "Sale");
		HashMap<String, String> map = PalNvpService.SetExpressCheckout(checkoutDetails,
				"http://192.168.1.32:8989/checkout-mini-browser/Return?page=review",
				"http://192.168.1.32:8989/checkout-mini-browser/cancel.jsp");
		System.out.println(map.toString());

		HashMap<String, String> details = PalNvpService.GetExpressCheckoutDetails(map.get("TOKEN").toString().trim());
		System.out.println(details.toString());
	}
}
