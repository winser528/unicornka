package com.pay.alipay.services;

import com.fit.entity.Orders;
import com.fit.entity.Pays;
import com.pay.alipay.config.AlipayConfig;
import com.pay.utils.HttpUtil;
import com.pay.utils.SignUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @TITLE AlipayService.java
 * @NAME 构造支付宝各接口请求参数
 * @DATE 2017-1-13
 */
public class AlipayService {

    /**
     * 支付宝提供给商户的服务接入网关URL(新)
     */
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
    private static final String gbk_charset = "gbk";

    /**
     * 生成页面
     *
     * @param sParaTemp     请求参数
     * @param gateway       请求URL
     * @param strMethod     请求方式
     * @param strButtonName 按钮名称
     * @param key           帐号KEY
     */
    public static String buildForm(Map<String, String> sParaTemp, String gateway, String strMethod, String strButtonName, String key) {
        // 过滤参数中的空和不参与验签的字段
        Map<String, String> sPara = SignUtil.paraFilter(sParaTemp);
        // 生成校验值
        String aliSign = SignUtil.AlipaySign(sPara, key, gbk_charset);
        sPara.put("sign", aliSign);
        // 判断请求服务不为网页即时到账授权接口
        if (!sPara.get("service").equals("alipay.wap.trade.create.direct")) {
            // 判断请求服务不为网页即时到账交易接口
            if (!sPara.get("service").equals("alipay.wap.auth.authAndExecute"))
                sPara.put("sign_type", gbk_charset);
        }

        List<String> keys = new ArrayList<String>(sPara.keySet());
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<form id='alipaysubmit' name='alipaysubmit' enctype='multipart/form-data'");
        sbHtml.append("action='").append(gateway).append("_input_charset=").append(gbk_charset);
        sbHtml.append("' method='").append(strMethod).append("'>");

        for (int i = 0; i < keys.size(); i++) {
            String name = keys.get(i);
            String value = sPara.get(name);
            sbHtml.append("<input type='hidden' name='" + name + "' value='" + value + "'/>");
        }

        sbHtml.append("<input type='submit' value='" + strButtonName + "' style='display:none;'></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

        return sbHtml.toString();
    }

    /**
     * 即时到账交易接口
     */
    public static String create_direct_pay_by_user(Pays pay, Orders order) {
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.getPartner());
        sParaTemp.put("return_url", AlipayConfig.getReturn_url());
        sParaTemp.put("notify_url", AlipayConfig.getNotify_url());
        sParaTemp.put("seller_email", AlipayConfig.getSeller_email());
        sParaTemp.put("_input_charset", AlipayConfig.getInput_charset());

        return buildForm(sParaTemp, ALIPAY_GATEWAY_NEW, "get", "确认", AlipayConfig.key);
    }

    /**
     * 纯担保交易
     */
    public static String create_partner_trade_by_buyer(Pays pay, Orders order) {
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("partner", AlipayConfig.getPartner());
        sParaTemp.put("service", "create_partner_trade_by_buyer");
        sParaTemp.put("return_url", AlipayConfig.getReturn_url());
        sParaTemp.put("notify_url", AlipayConfig.getNotify_url());
        sParaTemp.put("seller_email", AlipayConfig.getSeller_email());
        sParaTemp.put("_input_charset", AlipayConfig.getInput_charset());

        return buildForm(sParaTemp, ALIPAY_GATEWAY_NEW, "get", "确认", AlipayConfig.key);
    }

    /**
     * 标准实物双接口(标准双接口)
     */
    public static String trade_create_by_buyer(Map<String, String> sParaTemp) {
        sParaTemp.put("service", "trade_create_by_buyer");
        sParaTemp.put("partner", AlipayConfig.getPartner());
        sParaTemp.put("return_url", AlipayConfig.getReturn_url());
        sParaTemp.put("notify_url", AlipayConfig.getNotify_url());
        sParaTemp.put("seller_email", AlipayConfig.getSeller_email());
        sParaTemp.put("_input_charset", AlipayConfig.getInput_charset());

        return buildForm(sParaTemp, ALIPAY_GATEWAY_NEW, "get", "确认", AlipayConfig.key);
    }

    /**
     * 构造即时到帐批量退款无密接口
     *
     * @param sParaTemp 请求参数集合
     * @return 支付宝返回XML处理结果
     * @throws Exception
     */
    public static String refund_fastpay_by_platform_nopwd(String gateway, Map<String, Object> sParaTemp) throws Exception {
        // 增加基本配置
        sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("notify_url", AlipayConfig.notify_url);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        return HttpUtil.sendPost(gateway, sParaTemp);
    }

    /**
     * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
     *
     * @return 时间戳字符串
     */
    public static String query_timestamp() throws IOException, ParserConfigurationException, SAXException {
        // 构造访问query_timestamp接口的URL串
        String strUrl = ALIPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + AlipayConfig.partner;
        StringBuffer result = new StringBuffer();
        // 创建 DocumentBuilderFactory 实例
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        // 创建 DocumentBuilder 实例
        DocumentBuilder builder = factory.newDocumentBuilder();

        // 读取XML数据
        InputSource inputSource = new InputSource(new URL(strUrl).openStream());
        Document doc = builder.parse(inputSource);

        // 解析XML文档，查找时间戳
        NodeList nodeList = doc.getElementsByTagName("alipay");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if ("is_success".equals(node.getNodeName()) && "T".equals(node.getTextContent())) {
                NodeList timestampNodes = doc.getElementsByTagName("timestamp");
                for (int j = 0; j < timestampNodes.getLength(); j++) {
                    Node timestampNode = timestampNodes.item(j);
                    result.append(timestampNode.getTextContent());
                }
            }
        }
        return result.toString();
    }
}
