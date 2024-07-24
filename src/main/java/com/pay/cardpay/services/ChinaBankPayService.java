package com.pay.cardpay.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.cardpay.bean.ChinaBankReqDTO;

/**
 * @TITLE ChinaBankPayService.java
 * @NAME 网银在线支付
 * @DATE 2017-1-17
 */
public class ChinaBankPayService {

	/**
	 * 拼装网页在线支付数据
	 */
	public static Map<String, Object> getAssemblyData(ChinaBankReqDTO cb) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("v_mid", cb.getV_mid());// 商户编号
		map.put("v_oid", cb.getV_oid());// 订单编号
		map.put("v_amount", cb.getV_amount());// 订单总金额
		map.put("v_moneytype", cb.getV_moneytype());// 币种
		map.put("v_url", cb.getV_url());// 消费者完成消费后返回商户的页面地址
		map.put("key", cb.getKey());// 网银在线的密钥
		map.put("v_md5info", cb.getV_md5info()); // MD5校验码
		map.put("remark1", cb.getRemark1());// 备注1，自定义参数
		map.put("remark2", cb.getRemark2());// 结果通知页面

		return map;
	}

	/**
	 * 获取网银支付跳转页面信息
	 *
	 * @param v_amount
	 *            订单总金额
	 * @param v_mid
	 *            商户编号
	 * @param key
	 *            网银在线的密钥
	 * @param v_url
	 *            消费者完成消费后返回商户的页面地址
	 * @param v_oid
	 *            订单编号
	 * @param remark1
	 *            备注1，自定义参数
	 * @param remark2
	 *            结果通知页面，如果有值则所填地址通知，否则不通知；例：URL地址
	 */
	public static String getChinaBankWeb(Map<String, Object> param) {
		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<String>(param.keySet());
		sb.append("<body onLoad=\"javascript:document.E_FORM.submit()\">");
		sb.append("<form action=\"https://pay3.chinabank.com.cn/PayGate\" method=\"POST\" name=\"E_FORM\">");
		for (int i = 0; i < keys.size(); i++) {
			String name = keys.get(i);
			Object value = param.get(name);
			sb.append("<input type=\"hidden\" name=\"").append(null2String(name));
			sb.append("\"  value=\"").append(null2String(value)).append("\" size=\"100\">");
		}
		sb.append("</form><body>");
		return sb.toString();
	}

	private static String null2String(Object s) {
		return s == null ? "" : s.toString().trim();
	}
}
