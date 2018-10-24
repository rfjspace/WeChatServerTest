package com.wechatserver.info;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * ClassName: GlobalVariables
 * 
 * @Description: 全局属性
 */
public class GlobalVariables {
	public static String appId;
	public static String appSecret;
	public static String signature;
	public static String timestamp;
	public static String nonce;
	public static String token;
	public static String echostr;
	public static String encodingAESKey;
	public static String accessToken;
	public static String expires_in;

	private static Logger logger = Logger.getLogger(GlobalVariables.class.getName());

	public static void init(Properties prop) {
		GlobalVariables.appId = prop.getProperty("appId");
		GlobalVariables.appSecret = prop.getProperty("appSecret");
		GlobalVariables.token = prop.getProperty("token");
		GlobalVariables.encodingAESKey = prop.getProperty("encodingAESKey");
	}

	public static void infoPrint() {
		logger.info("appId : " + appId + "\n" + "appSecret : " + appSecret + "\n" + "signature : " + signature + "\n"
				+ "timestamp : " + timestamp + "\n" + "nonce : " + nonce + "\n" + "token : " + token + "\n"
				+ "encodingAESKey : " + encodingAESKey + "\n" + "accessToken : " + accessToken + "\n" + "expires_in : "
				+ expires_in);
	}
}
