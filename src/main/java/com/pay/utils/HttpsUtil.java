package com.pay.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Field;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

/**
 * @TITLE HttpsUtil.java
 * @NAME HTTPS请求工具类
 * @DATE 2017-1-13
 */
public class HttpsUtil {

    /**
     * 将MAP转换成URL
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        if (sb.toString().endsWith("&")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * XML转 Map
     */
    public static Map<String, String> XML2Map(String xml) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
            if (doc != null) {
                Element root = doc.getDocumentElement();
                NodeList nodes = root.getChildNodes();
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        map.put(node.getNodeName(), node.getTextContent());
                    }
                }
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 请求参数转换为XML字符串
     */
    public static String getReqDateXml(Object o) {
        StringBuilder xml = new StringBuilder("<xml>");
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String name = field.getName().replace("_-", "_");
                Object value = field.get(o);
                if (value != null) {
                    xml.append("<" + name + ">").append(value.toString()).append("</" + name + ">");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        xml.append("</xml>");
        return xml.toString();
    }

    /**
     * 发起HTTPS请求并获取结果
     *
     * @param requestUrl 路径
     * @param Method     请求方式
     * @param output     提交的数据
     */
    public static StringBuffer httpsRequest(String requestUrl, String Method, String output) {
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new HttpSSLSocketFactory.MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(ssf);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            // 设置请求方式（GET/POST）
            connection.setRequestMethod(Method);
            if ("https".equalsIgnoreCase(url.getProtocol())) {
                connection.setSSLSocketFactory(new HttpSSLSocketFactory());
                connection.setHostnameVerifier(new HttpSSLSocketFactory.TrustAnyHostnameVerifier());// 解决由于服务器证书问题导致HTTPS无法访问的情况
            }
            // 当有数据需要提交时
            if (null != output) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(output.getBytes("UTF-8"));
                outputStream.close();
            }
            // 将返回的输入流转换成字符串
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    // ============================== 带证书的HTTPS请求 ==============================

    /**
     * 钥匙和信任证书初始化
     *
     * @param type
     * @param cert_path 证书位置
     * @param cert_pwd  证书密钥
     */
    public SSLConnectionSocketFactory initKeyStore(String type, String cert_path, String cert_pwd) throws Exception {
        if ((type != null) && (!type.toString().equals(""))) {
            type = "PKCS12";
        }
        KeyStore keyStore = KeyStore.getInstance(type);// 指定类型的 KeyStore 对象
        FileInputStream fis = new FileInputStream(cert_path);// 加载本地的证书进行HTTPS加密传输
        try {
            keyStore.load(fis, cert_pwd.toCharArray());// 设置证书密码
        } catch (CertificateException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            fis.close();
        }
        // 信任自己的CA和自签名证书
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, cert_pwd.toCharArray()).build();
        // 只允许TLSv1协议
        return new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    /**
     * 通过HTTPS往API post XML数据
     *
     * @param type
     * @param cert_path 证书路径
     * @param cert_pwd  证书密码
     * @param url       API地址
     * @param xmlObj    要提交的XML数据对象
     * @return API回包的实际数据
     */
    public String sendPost(String type, String cert_path, String cert_pwd, String url, Object xmlObj) {
        String result = null;
        try {
            SSLConnectionSocketFactory sslSf = initKeyStore(type, cert_path, cert_pwd);
            // HTTP请求器
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslSf).build();
            // 请求器的配置，设置连接超时时间（默认10秒）&&传输超时时间（默认30秒）
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();
            // 请求对象
            HttpPost httpPost = new HttpPost(url);
            // 将要提交给API的数据对象转换成XML格式数据Post给API
            String postDataXML = getReqDateXml(xmlObj);
            LogProUtils.msg("API，POST过去的数据是：");
            LogProUtils.msg(postDataXML);
            // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
            StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.setEntity(postEntity);
            // 设置请求器的配置
            httpPost.setConfig(requestConfig);
            LogProUtils.msg("executing request" + httpPost.getRequestLine());
            // 执行后返回信息
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            httpPost.abort();
        } catch (SocketTimeoutException e) {
            LogProUtils.error("http get throw SocketTimeoutException");
        } catch (Exception e) {
            LogProUtils.error("http get throw Exception");
        }
        return result;
    }
}
