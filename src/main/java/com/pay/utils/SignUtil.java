package com.pay.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.pay.wchat.bean.OrderReturnDTO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @TITLE SingUtil.java
 * @NAME 签名工具类
 * @DATE 2017-1-14
 */
public class SignUtil {

    /**
     * 实体转换为验签MAP
     *
     * @param clz 参与验签的实体类
     */
    public static <T> SortedMap<String, Object> toMap(Object o, Class<T> clz) {
        SortedMap<String, Object> map = new TreeMap<String, Object>();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);// 类中的成员变量为private,故必须进行此操作
            Object obj;
            try {
                obj = field.get(o);
                if (obj != null) {
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 腾讯签名算法sign
     *
     * @param encoding   编码格式
     * @param parameters 参数
     * @param key        密钥
     */
    public static String TencentSign(String encoding, SortedMap<String, Object> parameters, String key) {
        StringBuffer sb = new StringBuffer();
        Set<Entry<String, Object>> es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator<Entry<String, Object>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String k = entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        LogProUtils.debug("验签参与的所有参数：===>" + sb.toString());
        String sign = MD5Util.MD5Encode(sb.toString(), encoding).toUpperCase();
        return sign;
    }

    /**
     * 字符串转输入流
     */
    public static InputStream getStringStream(String sInputString) {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
        }
        return tInputStringStream;
    }

    /**
     * XML字符串转MAP
     */
    public static SortedMap<String, Object> getMapFromXML(String xmlString) {
        SortedMap<String, Object> map = new TreeMap<String, Object>();
        try {
            // 这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = getStringStream(xmlString);
            Document document = builder.parse(is);
            // 获取到document里面的全部结点
            NodeList allNodes = document.getFirstChild().getChildNodes();
            Node node;
            int i = 0;
            while (i < allNodes.getLength()) {
                node = allNodes.item(i);
                if (node instanceof Element) {
                    map.put(node.getNodeName(), node.getTextContent());
                }
                i++;
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将 XML 转换为目标对象
     *
     * @param xml          XML 字符串
     * @param targetObject 目标对象实例
     * @throws Exception 如果解析或设置值时出错
     */
    public static <T> T parseXMLToObject(String xml, Class<T> targetObject) throws Exception {
        T clz = targetObject.newInstance();
        // 创建 DocumentBuilderFactory 和 DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(xml.getBytes()));
        // 获取根元素
        Element root = document.getDocumentElement();
        // 遍历根元素的所有子节点
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();
                String nodeValue = node.getTextContent().trim();
                // 查找相应的 setter 方法
                Method[] methods = clz.getClass().getMethods();
                for (Method method : methods) {
                    String methodName = method.getName();
                    if (methodName.startsWith("set") && methodName.substring(3).equalsIgnoreCase(nodeName)) {
                        // 设置字段值
                        Class<?> type = method.getParameterTypes()[0];
                        if (type == boolean.class || type == Boolean.class) {
                            method.invoke(clz, Boolean.parseBoolean(nodeValue));
                        } else if (type == int.class || type == Integer.class) {
                            method.invoke(clz, Integer.parseInt(nodeValue));
                        } else if (type == double.class || type == Double.class) {
                            method.invoke(clz, Double.parseDouble(nodeValue));
                        } else if (type == long.class || type == Long.class) {
                            method.invoke(clz, Long.parseLong(nodeValue));
                        } else {
                            method.invoke(clz, nodeValue);
                        }
                    }
                }
            }
        }
        return clz;
    }

    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     *
     * @param responseString API返回的XML数据字符串
     * @return API签名是否合法
     */
    public static boolean TencentSignCheckIsSignValidFromResponseString(String encoding, String responseString, String key) {
        SortedMap<String, Object> map = getMapFromXML(responseString);
        LogProUtils.debug(map.toString());

        String signFromAPIResponse = map.get("sign").toString();
        if (signFromAPIResponse == "" || signFromAPIResponse == null) {
            LogProUtils.debug("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        LogProUtils.debug("服务器回包里面的签名是:" + signFromAPIResponse);
        // 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        // 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = TencentSign(encoding, map, key);

        if (!signForAPIResponse.equals(signFromAPIResponse)) {
            // 签名验不过，表示这个API返回的数据有可能已经被篡改了
            LogProUtils.debug("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        LogProUtils.debug("恭喜，API返回的数据签名验证通过!!!");
        return true;
    }

    // ////////////////////////////////////////////////
    //
    // 支付宝支付验签方法
    //
    // ////////////////////////////////////////////////

    /**
     * 过滤参数中的空和不参与验签的字段
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();

        if ((sArray == null) || (sArray.size() <= 0)) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if ((value == null) || (value.equals("")) || (key.equalsIgnoreCase("sign")) || (key.equalsIgnoreCase("sign_type"))) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 创建链接字符串
     */
    public static String createLinkObejct(Map<String, Object> params) {
        Map<String, String> map = new HashMap<String, String>();
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if ((value == null) || (value.equals(""))) {
                continue;
            }
            map.put(key, value.toString().trim());
        }
        return createLinkString(map);
    }

    /**
     * 创建链接字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        // 返回值
        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1)
                prestr = prestr + key + "=" + value;
            else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * 支付宝验签
     */
    public static String AlipaySign(Map<String, String> sArray, String key, String encoding) {
        String prestr = createLinkString(sArray);
        String mysign = MD5Util.md5(prestr, key, encoding);
        return mysign;
    }
}
