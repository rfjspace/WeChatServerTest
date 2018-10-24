package com.wechatserver.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.wechatserver.info.GlobalVariables;

/**
 * ClassName: SignUtil
 * 
 * @Description: 接入微信公众号服务器工具类
 */
public class SignCheckUtil {

	/***
	 * 微信服务器签名验证
	 * 
	 * @param signature
	 *            微信服务器签名
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		// 将token、timestamp、nonce三个参数进行字典序排序
		String sortString = sort(GlobalVariables.token, timestamp, nonce);
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
	 * @param sortStr
	 *            需要加密的字符串
	 * @return 加密后的内容
	 */
	private static String sha1(String sortStr) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(sortStr.getBytes());// 使用指定的字节数组更新摘要
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
