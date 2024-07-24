package com.pay.paypal.services;

import java.util.ArrayList;
import java.util.List;

import com.pay.paypal.bean.SysMap;

/**
 * @TITLE PayPalService.java
 * @NAME 贝宝支付
 * @DATE 2017-1-17
 */
public class PayPalService {

	/**
	 * @param business
	 *            收款人（即商家）的电子邮件地址或账户号
	 * @param item_name
	 *            由您（商家）传递的物品名称
	 * @param item_number
	 *            您用于跟踪购买的传递变量
	 * @param return_url
	 *            付款后页面
	 * @param notify_url
	 * @param amount
	 * @param currency_code
	 *            货币
	 * @param custom
	 *            由您（商家）传递的自定义值。在任何情形下，都不会向您的客户显示这些传递变量。
	 * @param invoice
	 *            可供您用来识别此次购物的帐单号码的转递变量。 如果省略，则没有变量传回。
	 * @param no_note
	 *            付款说明,可为空
	 */
	public static String getPayPalWeb(String business, String item_name, String item_number, String return_url, String notify_url,
									  String amount, String currency_code, String custom, String invoice, String no_note) throws Exception {
		try {
			List<SysMap> list = new ArrayList<SysMap>();
			/*
			 * _xclick表示该按钮为“立即购买”或“捐赠”按钮。
			 * xclick_subscription表示该按钮为“订阅”按钮。
			 * _cart表示为“购物车”相关按钮。
			 * s_x-click表示该按钮为加密按钮。
			 */
			list.add(new SysMap("cmd", "_xclick"));
			list.add(new SysMap("business", business));// 贝宝帐号
			list.add(new SysMap("item_name", item_name));// payment for
			list.add(new SysMap("amount", amount));// 订单金额
			list.add(new SysMap("currency_code", currency_code));// 货币
			list.add(new SysMap("return", return_url));// 付款后页面
			list.add(new SysMap("invoice", invoice));// 订单号
			list.add(new SysMap("charset", "utf-8"));// 字符集
			list.add(new SysMap("no_shipping", "1"));// 不要求客户提供收货地址
			list.add(new SysMap("no_note", null2String(no_note)));// 付款说明
			list.add(new SysMap("notify_url", notify_url));// Post页面或者pp后台设置
			list.add(new SysMap("rm", "2"));// 值为2时，支付完成返回值
			// list.add(new SysMap("locale.x", "en_US"));// 语种
			list.add(new SysMap("item_number", item_number));// 可以记录其他信息，如用户ID等
			list.add(new SysMap("custom", custom));// 由您（商家）传递的自定义值。在任何情形下，都不会向您的客户显示这些传递变量

			StringBuffer sb = new StringBuffer();
			sb.append("<body onLoad=\"javascript:document.paypal.submit()\">");
			sb.append("<form action=\"https://www.paypal.com/cgi-bin/webscr\" method=\"POST\" name=\"paypal\">");
			for (SysMap sm : list) {
				sb.append("<input type=\"hidden\" name=\"").append(null2String(sm.getKey()));
				sb.append("\"  value=\"").append(null2String(sm.getValue())).append("\" size=\"100\">");
			}
			sb.append("</form><body>");
			return sb.toString();
		} catch (Exception e) {
		}

		return null;
	}

	private static String null2String(Object s) {
		return s == null ? "" : s.toString().trim();
	}
}
