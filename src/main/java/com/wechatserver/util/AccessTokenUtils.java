package com.wechatserver.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechatserver.entry.AccessToken;
import com.wechatserver.info.AccessTokenInfo;


public class AccessTokenUtils {
	/***
	 * 用于获取accessToken的
	 * 
	 * @param appId
	 * @param appSecret
	 */
	public static void getWeChatPermission(String appId, String appSecret) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						// 获取accessToken
						AccessTokenInfo.accessToken = getAccessToken(appId, appSecret);
						// 获取成功
						if (AccessTokenInfo.accessToken != null) { // 获取成功
							// 获取到access_token 休眠7000秒,大约2个小时
							Thread.sleep(7000 * 1000);

						} else { // 获取失败
							// 获取的access_token为空 休眠3秒
							Thread.sleep(3 * 1000);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/***
	 * 获取access_token
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	private static AccessToken getAccessToken(String appId, String appSecret) {

		// 接口地址为https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
		String Url = String.format(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId,
				appSecret);
		// 此请求为https的get请求，返回的数据格式为
		// {"access_token":"ACCESS_TOKEN","expires_in":7200}
		String result = NetWorkHelperUtils.getHttpsResponse(Url, "");
		System.out.println("获取到的access_token=" + result);
		// 使用FastJson将Json字符串解析成Json对象
		JSONObject json = JSON.parseObject(result);
		AccessToken token = new AccessToken();
		token.setAccessToken(json.getString("access_token"));
		token.setExpiresin(json.getInteger("expires_in"));
		return token;
	}
}
