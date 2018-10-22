package com.wechatserver.backup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 访问网络用到的工具类
 */
public class NetWorkHelperUtils {
	/***
	 * 发起Https请求
	 * 
	 * @param reqUrl请求的URL地址
	 * @param requestMethod
	 * @return 响应后的字符串
	 */
	public static String getHttpsResponse(String reqUrl, String requestMethod) {
		URL url;
		InputStream is;
		String resultData = "";
		try {
			url = new URL(reqUrl);
			// 发送Https请求
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			// 网络安全证书
			TrustManager[] tm = { xtm };
			// SSLSocket扩展Socket并提供使用SSL或TLS协议的安全套接字
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, tm, null);
			// 解决https需要验证问题
			con.setSSLSocketFactory(ctx.getSocketFactory());
			con.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			con.setDoInput(true);// 允许输入流，即允许下载
			// 在android中必须将此项设置为false
			con.setDoOutput(false);// 允许输出流，即允许上传
			con.setUseCaches(false);// 不使用缓冲
			if (null != requestMethod && !requestMethod.equals("")) {
				con.setRequestMethod(requestMethod);// 使用指定的方式请求
			} else {
				con.setRequestMethod("GET");// 使用GET方式请求
			}
			// 获取输入流，此时才真正建立链接
			is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bufferReader = new BufferedReader(isr);
			String inputLine;
			while ((inputLine = bufferReader.readLine()) != null) {
				resultData += inputLine + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultData;
	}

	static // 网络安全证书
	X509TrustManager xtm = new X509TrustManager() {

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {


		}
	};
}
