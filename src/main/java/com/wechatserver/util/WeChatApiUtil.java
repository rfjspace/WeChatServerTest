package com.wechatserver.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.wechatserver.info.GlobalVariables;

/**
 * ClassName: WeChatApiUtil
 * 
 * @Description: 微信API工具类
 */
public class WeChatApiUtil {
	// token 接口(GET)
	private static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	// 素材上传(POST)https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
	private static final String UPLOAD_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/upload";
	// 素材下载:不支持视频文件的下载(GET)
	private static final String DOWNLOAD_MEDIA = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
	// 创建自定义菜单
	private static final String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

	/***
	 * 创建自定义菜单
	 * 
	 * @param postData
	 *            自定义菜单
	 */
	public static void createCustomMenu(String postData) {
		String menuUrl = String.format(MENU_CREATE, GlobalVariables.accessToken);
		String reponseMsg = WeChatApiUtil.httpsRequestToString(menuUrl, "POST", postData);
		JSONObject json = JSON.parseObject(reponseMsg);
		if ("ok".equals(json.getString("errmsg"))) {
			System.out.println("菜单创建成功");
		} else {
			System.out.println("菜单创建失败");
		}
	}

	/***
	 * 用于获取accessToken的
	 * 
	 * @param appId
	 * @param appSecret
	 */
	public static void getWeChatToken(String appId, String appSecret) {
		Thread thread = new Thread(new Runnable() {
			int count = 1;

			@Override
			public void run() {
				while (true) {
					try {
						String accessToken = WeChatApiUtil.getToken(appId, appSecret);// 获取accessToken
						if (null != accessToken) { // 获取成功
							System.out.println("accessToken : " + accessToken);
							Thread.sleep(7000 * 1000);// 休眠7000秒,大约2个小时
						} else {// 获取失败
							if (count > 3) {
								System.out.println("AccessToken获取失败 : 第" + count + "次");
								break;
							}
							count++;
							Thread.sleep(3 * 1000);// 休眠3秒
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.setDaemon(true); // 线程安全，主线程关闭，子线程关闭
		thread.start(); // 线程启动
	}

	/**
	 * 通用接口获取Token凭证
	 *
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	private static synchronized String getToken(String appId, String appSecret) {
		if (appId == null || appSecret == null) {
			return null;
		}
		String token = null;
		String expires_in = null;
		String tockenUrl = String.format(ACCESS_TOKEN, appId, appSecret);
		String response = httpsRequestToString(tockenUrl, "GET", null);
		JSONObject jsonObject = JSON.parseObject(response);
		if (null != jsonObject) {
			try {
				token = jsonObject.getString("access_token");
				expires_in = jsonObject.getString("expires_in");
				GlobalVariables.accessToken = token;
				GlobalVariables.expires_in = expires_in;
			} catch (JSONException e) {
				token = null;// 获取token失败
			}
		}
		return token;
	}

	/**
	 * 微信服务器素材上传
	 *
	 * @param file
	 *            表单名称media
	 * @param token
	 *            access_token
	 * @param type
	 *            type只支持四种类型素材(video/image/voice/thumb)
	 */
	@SuppressWarnings("deprecation")
	public static JSONObject uploadMedia(File file, String token, String type) {
		if (file == null || token == null || type == null) {
			return null;
		}

		if (!file.exists()) {
			System.out.println("上传文件不存在,请检查!");
			return null;
		}

		String url = UPLOAD_MEDIA;
		JSONObject jsonObject = null;
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Connection", "Keep-Alive");
		post.setRequestHeader("Cache-Control", "no-cache");
		FilePart media;
		HttpClient httpClient = new HttpClient();
		// 信任任何类型的证书
		Protocol myhttps = new Protocol("https", new SSLProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);

		try {
			media = new FilePart("media", file);
			Part[] parts = new Part[] { new StringPart("access_token", token), new StringPart("type", type), media };
			MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
			post.setRequestEntity(entity);
			int status = httpClient.executeMethod(post);
			if (status == HttpStatus.SC_OK) {
				String text = post.getResponseBodyAsString();
				jsonObject = JSONObject.parseObject(text);
			} else {
				System.out.println("upload Media failure status is:" + status);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 多媒体下载接口
	 *
	 * @param fileName
	 *            素材存储文件路径
	 * @param token
	 *            认证token
	 * @param mediaId
	 *            素材ID（对应上传后获取到的ID）
	 * @return 素材文件
	 * @comment 不支持视频文件的下载
	 */
	public static File downloadMedia(String fileName, String token, String mediaId) {
		String url = String.format(DOWNLOAD_MEDIA, token, mediaId);
		return httpRequestToFile(fileName, url, "GET", null);
	}

	/**
	 * 以http方式发送请求,并将请求响应内容输出到文件
	 *
	 * @param path
	 *            请求路径
	 * @param method
	 *            请求方法
	 * @param body
	 *            请求数据
	 * @return 返回响应的存储到文件
	 */
	public static File httpRequestToFile(String fileName, String path, String method, String body) {
		if (fileName == null || path == null || method == null) {
			return null;
		}

		File file = null;
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		FileOutputStream fileOut = null;
		try {
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(method);
			if (null != body) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(body.getBytes("UTF-8"));
				outputStream.close();
			}

			inputStream = conn.getInputStream();
			if (inputStream != null) {
				file = new File(fileName);
			} else {
				return file;
			}

			// 写入到文件
			fileOut = new FileOutputStream(file);
			if (fileOut != null) {
				int c = inputStream.read();
				while (c != -1) {
					fileOut.write(c);
					c = inputStream.read();
				}
			}
		} catch (Exception e) {
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
			try { // 必须关闭文件流 否则JDK运行时，文件被占用其他进程无法访问
				inputStream.close();
				fileOut.close();
			} catch (IOException execption) {
			}
		}
		return file;
	}

	/**
	 * 发送请求以https方式发送请求并将请求响应内容以String方式返回
	 *
	 * @param path
	 *            请求路径
	 * @param method
	 *            请求方法
	 * @param body
	 *            请求数据体
	 * @return 请求响应内容转换成字符串信息
	 */
	public static String httpsRequestToString(String path, String method, String body) {
		if (path == null || method == null) {
			return null;
		}

		String response = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		HttpsURLConnection conn = null;
		try {
			TrustManager[] tm = { new JEEWeiXinX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			System.out.println(path);
			URL url = new URL(path);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(method);
			if (null != body) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(body.getBytes("UTF-8"));
				outputStream.close();
			}

			inputStream = conn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			response = buffer.toString();
		} catch (Exception e) {

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
			try {
				bufferedReader.close();
				inputStreamReader.close();
				inputStream.close();
			} catch (IOException execption) {

			}
		}
		return response;
	}
}

class JEEWeiXinX509TrustManager implements X509TrustManager {
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
	/*
	 * public static void main(String[] args) throws Exception { // 媒体文件路径 String
	 * filePath =
	 * "D:/JavaSoftwareDevelopeFolder/IntelliJ IDEA_Workspace/WxStudy/web/media/image/我.jpg"
	 * ; // String filePath = "D:/JavaSoftwareDevelopeFolder/IntelliJ //
	 * IDEA_Workspace/WxStudy/web/media/voice/voice.mp3"; // String filePath =
	 * "D:\\JavaSoftwareDevelopeFolder\\IntelliJ //
	 * IDEA_Workspace\\WxStudy\\web\\media\\video\\小苹果.mp4"; // 媒体文件类型 String type =
	 * "image"; // String type = "voice"; // String type = "video"; JSONObject
	 * uploadResult = uploadMedia(filePath, type); // {"media_id":
	 * "dSQCiEHYB-pgi7ib5KpeoFlqpg09J31H28rex6xKgwWrln3HY0BTsoxnRV-xC_SQ",
	 * "created_at":1455520569,"type":"image"}
	 * System.out.println(uploadResult.toString());
	 * 
	 * // 下载刚刚上传的图片以id命名 String media_id = uploadResult.getString("media_id"); File
	 * file = downloadMedia("D:/" + media_id + ".png", media_id);
	 * System.out.println(file.getName());
	 * 
	 * }
	 */
}
