package com.pay.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @TITLE HttpUtil.java
 * @NAME HTTP请求工具类
 * @DATE 2017-1-13
 */
public class HttpUtil extends HttpsUtil {

    private static String DEFAULT_CODING = "UTF-8";

    /**
     * 获取你的网站的域名
     */
    public static String getDomain(HttpServletRequest request) {
        return request.getServerName();
    }

    /**
     * 获取当前使用的协议的网站域名，当前页面使用的协议，http 或是 https;
     */
    public static String getHttpDomain(HttpServletRequest request) {
        String port = "";
        if (request.getServerPort() != 80) {
            port = ":" + request.getServerPort();
        }
        return request.getScheme() + "://" + request.getServerName() + port;
    }

    /**
     * 获取当前网站的协议的网站域名的端口的应用名
     */
    public static String getContextHttpUri(HttpServletRequest request) {
        String port = "";
        if (request.getServerPort() != 80) {
            port = ":" + request.getServerPort();
        }
        return request.getScheme() + "://" + request.getServerName() + port + request.getContextPath();
    }

    /**
     * 项目在容器中的实际发布运行的根路径
     */
    public static String getRealPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * 获取当前网站的协议的网站域名的端口的应用的页面所在目录下全名称
     */
    public static String getRequestFullUri(HttpServletRequest request) {
        return getContextHttpUri(request) + request.getServletPath();
    }

    /**
     * 获取当前使用的协议的网站域名的页面所在目录下全名称
     */
    public static String getRequestFullUriNoContextPath(HttpServletRequest request) {
        return getHttpDomain(request) + request.getServletPath();
    }

    /**
     * 获取真实的IP地址
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = "";
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                if (ip.indexOf("::ffff:") != -1)
                    ip = ip.replace("::ffff:", "");
                int index = ip.indexOf(",");
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ip = inet.getHostAddress();
                }
            }

            ip = request.getHeader("X-Real-IP");
            if (PayCommonUtil.isNotNull(ip) && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) { // "***.***.***.***".length() = 15
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    /**
     * 判断当前请求是否为Ajax
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return PayCommonUtil.isNotNull(header) && "XMLHttpRequest".equals(header);
    }

    /**
     * 重定向
     */
    public static void redirectUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String url) {
        try {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重定向到http://的url
     */
    public static void redirectHttpUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String url) {
        try {
            httpServletResponse.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置连接参数
     */
    private static URLConnection setConn(URL realUrl) throws IOException {
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        // 设置通用的请求属性
        // conn.setRequestProperty("Content-type", "UTF-8");
        conn.setConnectTimeout(60000);
        conn.setReadTimeout(60000);
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);

        return conn;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = setConn(realUrl);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            // 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 关闭输出流、输入流
     */
    private static void closeConn(BufferedReader in, Writer out) {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, Object> param) throws Exception {
        return sendPost(url, param, null);
    }

    public static String sendPost(String url, Map<String, Object> param, String coding) throws Exception {
        return sendPost(url, getUrlParamsByMap(param), coding);
    }

    public static String sendPost(String url, String param) throws Exception {
        return sendPost(url, param, null);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url    发送请求的 URL
     * @param param  请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param coding 编码
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param, String coding) throws Exception {
        if (PayCommonUtil.isNullOrEmpty(coding)) {
            coding = DEFAULT_CODING;
        }

        Writer out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = setConn(realUrl);
            // 获取URLConnection对象对应的输出流
            out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), coding));
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), coding));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (SocketTimeoutException e) {
            LogProUtils.error("http get throw SocketTimeoutException");
        } catch (Exception e) {
            LogProUtils.error("http get throw Exception");
            e.printStackTrace();
        } finally {
            closeConn(in, out);
        }
        return result;
    }

    /**
     * POST请求，返回结果字符串
     */
    public static String doPostStr(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = setConn(realUrl);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_CODING));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            closeConn(in, out);
        }
        return result;
    }

    /**
     * 将URL参数转换成MAP
     *
     * @param param aa=11&bb=22&cc=33
     */
    public static Map<String, Object> getUrlParamsByParam(String param) {
        Map<String, Object> map = new HashMap<String, Object>(0);
        if (PayCommonUtil.isNullOrEmpty(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    /**
     * 根据编码格式（encoding）将URL的参数编码，默认的编码格式UTF-8
     */
    public static String urlEncode(String params, String encoding) {
        try {
            String returnValue = URLEncoder.encode(params, DEFAULT_CODING);
            if (PayCommonUtil.isNotNull(encoding))
                returnValue = URLEncoder.encode(params, encoding.trim());

            return returnValue;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * URL解码（UTF-8） 根据编码格式（encoding）将参数解码，默认的编码格式UTF-8
     */
    public static String urlDecode(String params, String encoding) {
        try {
            String result = URLDecoder.decode(params, DEFAULT_CODING);
            if (PayCommonUtil.isNotNull(encoding))
                result = URLDecoder.decode(params, encoding);
            return result;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取请求的字符编码,默认使用gbk
     */
    public static String getCharacterEncoding(HttpServletRequest request, HttpServletResponse response) {
        return getCharacterEncoding(request, response, "gbk");
    }

    /**
     * 获取请求的字符编码
     */
    public static String getCharacterEncoding(HttpServletRequest request, HttpServletResponse response, String encoding) {
        if ((request == null) || (response == null)) {
            return encoding;
        }

        String enc = request.getCharacterEncoding();
        if ((enc == null) || ("".equals(enc))) {
            enc = response.getCharacterEncoding();
        }

        if ((enc == null) || ("".equals(enc))) {
            enc = encoding;
        }

        return enc;
    }

    /**
     * 字符串转输入流
     */
    public static InputStream Str2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }
}
