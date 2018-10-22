package com.wechatserver.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.wechatserver.info.WechatConfigInfo;

/***
 * 接入微信公众号服务器工具类
 * 
 * @author Administrator
 *
 */
public class ConnectHandlerUtils {

	/**
	 * 微信公众号服务器转发请求的验证
	 * 
	 * @param map
	 *            微信公众号服务器转发的服务器消息
	 * @return true 微信公众号服务器与servlet服务器通信成功 false 微信公众号服务器与servlet服务器通信失败
	 */
	public static boolean wechatConnectVaildate(String token) {
		String signature = WechatConfigInfo.signature;// 微信加密签名
		String timestamp = WechatConfigInfo.timestamp;// 时间戳
		String nonce = WechatConfigInfo.nonce;// 随机数

		// 将token、timestamp、nonce三个参数进行字典序排序
		String sortString = sort(token, timestamp, nonce);
		// 加密,生成加密签名
		String mySignature = sha1(sortString);
		// 校验签名
		if (mySignature != null && mySignature != "" && mySignature.equals(signature)) {
			return true;
		}
		return false;
	}

	/***
	 * SHA1加密,生成加密签名
	 * 
	 * @param 需要加密的字符串
	 * @return 加密后的内容
	 */
	private static String sha1(String sortString) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(sortString.getBytes());// 使用指定的字节数组更新摘要
			byte messageDigest[] = digest.digest();// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算。
			StringBuffer hexString = new StringBuffer();// 保存16进制的加密签名
			// 字节数组转换为十六进制数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);// & 0xFF取字节数组的低四位
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	/***
	 * 字典序排序
	 * 
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return 排序结果
	 */
	private static String sort(String token, String timestamp, String nonce) {
		String[] strArray = { token, timestamp, nonce };
		Arrays.sort(strArray);
		StringBuilder sb = new StringBuilder();
		for (String str : strArray) {
			sb.append(str);
		}
		return sb.toString();
	}
}
