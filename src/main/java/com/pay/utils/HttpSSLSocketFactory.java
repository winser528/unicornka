package com.pay.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpSSLSocketFactory extends SSLSocketFactory {
	private SSLContext getSSLContext() {
		return createEasySSLContext();
	}

	@Override
	public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2, int arg3) throws IOException {
		return getSSLContext().getSocketFactory().createSocket(arg0, arg1, arg2, arg3);
	}

	@Override
	public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3) throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(arg0, arg1, arg2, arg3);
	}

	@Override
	public Socket createSocket(InetAddress arg0, int arg1) throws IOException {
		return getSSLContext().getSocketFactory().createSocket(arg0, arg1);
	}

	@Override
	public Socket createSocket(String arg0, int arg1) throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(arg0, arg1);
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return null;
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return null;
	}

	@Override
	public Socket createSocket(Socket arg0, String arg1, int arg2, boolean arg3) throws IOException {
		return getSSLContext().getSocketFactory().createSocket(arg0, arg1, arg2, arg3);
	}

	private SSLContext createEasySSLContext() {
		try {
			SSLContext context = SSLContext.getInstance("SSL");
			context.init(null, new TrustManager[] { MyX509TrustManager.manger }, null);
			return context;
		} catch (Exception e) {
			LogProUtils.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 证书信任管理器
	 */
	public static class MyX509TrustManager implements X509TrustManager {

		static MyX509TrustManager manger = new MyX509TrustManager();

		public MyX509TrustManager() {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}
	}

	/**
	 * 解决由于服务器证书问题导致HTTPS无法访问的情况 PS:HTTPS hostname wrong: should be <localhost>
	 */
	public static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}
