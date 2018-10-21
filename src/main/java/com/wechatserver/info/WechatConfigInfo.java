package com.wechatserver.info;

/***
 * 微信公众号开发-一般属性
 * 
 * @author Administrator
 *
 */
public class WechatConfigInfo {
	public static String appId;
	public static String appSecret;
	public static String signature;
	public static String timestamp;
	public static String nonce;
	public static String token;
	public static String encodingAESKey;

	public static String accessToken;
	public static int expiresin;
	// 微信公众平台acces_token的API接口
	public static final String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
}
