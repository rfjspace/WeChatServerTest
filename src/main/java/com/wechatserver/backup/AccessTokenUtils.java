package com.wechatserver.backup;

import com.wechatserver.info.GlobalVariables;

/***
 * 获取access_token工具类
 * 
 * @author Administrator
 *
 */
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
						AccessTokenUtils.getAccessToken(appId, appSecret);
						// 获取成功
						if (GlobalVariables.accessToken != null) { // 获取成功
							// 获取到access_token 休眠7000秒,大约2个小时
							System.out.println("accessToken : " + GlobalVariables.accessToken);
							// System.out.println("expiresin : " + WechatConfigInfo.expiresin);
							Thread.sleep(7000 * 1000);

						} else { // 获取失败
							// 获取的access_token为空 休眠3秒
							System.out.println("AccessToken获取失败");
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
	 * @return
	 */
	private static void getAccessToken(String appId, String appSecret) {

		// 接口地址为https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
		// String url = String.format(WechatConfigInfo.accessTokenUrl, appId,
		// appSecret);
		// 此请求为https的get请求，返回的数据格式为
		// String result = NetWorkHelperUtils.getHttpsResponse(url, "");
		// 使用FastJson将Json字符串解析成Json对象
		// {"access_token":"ACCESS_TOKEN","expires_in":7200}
		// JSONObject json = JSON.parseObject(result);
		// WechatConfigInfo.accessToken = json.getString("access_token");
		// WechatConfigInfo.expiresin = json.getInteger("expires_in");
	}
}
