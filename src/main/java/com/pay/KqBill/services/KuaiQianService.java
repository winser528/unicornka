package com.pay.KqBill.services;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.KqBill.bean.KqConfig;
import com.pay.KqBill.bean.KqReqDTO;
import com.pay.utils.MD5Util;
import com.pay.utils.SignUtil;

/**
 * @TITLE KuaiQianService.java
 * @NAME 快钱支付服务
 * @DATE 2017-1-21
 */
public class KuaiQianService {

	/**
	 * 返回验签结果
	 */
	public static Map<String, Object> sign99Bill(KqReqDTO krd) {
		Map<String, Object> sParaTemp = new HashMap<String, Object>();
		// 协议参数
		sParaTemp.put("inputCharset", krd.getInputCharset());
		sParaTemp.put("bgUrl", krd.getBgUrl());
		sParaTemp.put("version", krd.getVersion());
		sParaTemp.put("language", krd.getLanguage());
		sParaTemp.put("signType", krd.getSignType());
		// 买卖双方信息参数
		sParaTemp.put("merchantAcctId", krd.getMerchantAcctId());
		sParaTemp.put("payerName", krd.getPayerName());
		sParaTemp.put("payerContactType", krd.getPayerContactType());
		sParaTemp.put("payerContact", krd.getPayerContact());
		// 业务参数
		sParaTemp.put("orderId", krd.getOrderId());
		sParaTemp.put("orderAmount", krd.getOrderAmount());
		sParaTemp.put("orderTime", krd.getOrderTime());
		sParaTemp.put("productName", krd.getProductName());
		sParaTemp.put("productNum", krd.getProductNum());
		sParaTemp.put("productId", krd.getProductId());
		sParaTemp.put("productDesc", krd.getProductDesc());
		sParaTemp.put("ext1", krd.getExt1());
		sParaTemp.put("ext2", krd.getExt2());
		sParaTemp.put("payType", krd.getPayType());
		sParaTemp.put("redoFlag", krd.getRedoFlag());
		sParaTemp.put("pid", krd.getPid());

		sParaTemp.put("signType", MD5Util.MD5Encode(SignUtil.createLinkObejct(sParaTemp), "UTF-8"));

		return sParaTemp;
	}

	public String signMsg(String signMsg) {

		String base64 = "";
		try {
			// 密钥仓库
			KeyStore ks = KeyStore.getInstance("PKCS12");
			// 绝对路径
			String file = KqConfig.getPfxPath();
			if (KqConfig.isAbsolutePath()) {
				// 相对路径
				file = System.getProperty("user.dir") + "cert/kq/tester-rsa.pfx";
			}
			// 读取密钥仓库
			FileInputStream ksfis = new FileInputStream(file);

			BufferedInputStream ksbufin = new BufferedInputStream(ksfis);
			char[] keyPwd = KqConfig.getKeyPwd().toCharArray();
			ks.load(ksbufin, keyPwd);
			// 从密钥仓库得到私钥
			PrivateKey priK = (PrivateKey) ks.getKey("test-alias", keyPwd);
			// 加密算法
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(priK);
			signature.update(signMsg.getBytes("utf-8"));
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			base64 = encoder.encode(signature.sign());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return base64;
	}

	/**
	 * 块钱支付页面生成
	 *
	 * @param sParaTemp
	 *            参数信息
	 * @param Method
	 *            发送方式
	 * @param strButtonName
	 *            按钮名称
	 */
	public static String build99BillForm(Map<String, Object> sParaTemp, String method, String strButtonName) {

		List<String> keys = new ArrayList<String>(sParaTemp.keySet());
		StringBuffer sbHtml = new StringBuffer();
		sbHtml.append("<form id=\"99billsubmit\" name=\"99billsubmit\" ");
		sbHtml.append("action=\"https://www.99bill.com/gateway/recvMerchantInfoAction.htm\" ");
		sbHtml.append("method=\"").append(method).append("\">");
		// 隐藏的参数
		for (int i = 0; i < keys.size(); i++) {
			String name = keys.get(i);
			Object value = sParaTemp.get(name);
			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}
		// 提交按钮
		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['99billsubmit'].submit();</script>");

		return sbHtml.toString();
	}

}
