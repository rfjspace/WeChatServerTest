package com.wechatserver.util;

import com.wechatserver.global.GlobalVariables;
import com.wechatserver.wxapi.AesException;
import com.wechatserver.wxapi.WXBizMsgCrypt;

/**
 * ClassName: CryptionUtil
 * 
 * @Description: 加解密工具
 */
public class CryptionUtil {
	/***
	 * 微信消息的解密
	 * 
	 * @param msgSignature
	 *            签名串
	 * @param encrypt
	 *            消息主体
	 * @return
	 */
	public static String msgDecrypt(String msgSignature, String encrypt) {
		try {
			WXBizMsgCrypt crypt = new WXBizMsgCrypt(GlobalVariables.token, GlobalVariables.encodingAESKey,
					GlobalVariables.appId);
			// TODO CryptMessage转换为String
			// CryptMessage entry = new CryptMessage();
			// entry.setToUserName("toUser");
			// entry.setEncrypt("%1$s");
			// String xmlFormat = XStreamUtil.toXML(entry);
			String xmlFormat = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
			String fromXML = String.format(xmlFormat, encrypt);
			return crypt.decryptMsg(msgSignature, GlobalVariables.timestamp, GlobalVariables.nonce, fromXML);

		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}

	/***
	 * 微信消息的加密
	 * 
	 * @param replyMsg
	 * @return
	 */
	public static String msgEncrypt(String replyMsg) {
		try {
			WXBizMsgCrypt crypt = new WXBizMsgCrypt(GlobalVariables.token, GlobalVariables.encodingAESKey,
					GlobalVariables.appId);
			return crypt.encryptMsg(replyMsg, GlobalVariables.timestamp, GlobalVariables.nonce);

		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}
}

/**
 * ClassName: CryptMessage
 * 
 * @Description: 加密消息
 */
class CryptMessage {
	// 接收方帐号（收到的 OpenID）
	private String ToUserName;
	// 密文
	private String Encrypt;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getEncrypt() {
		return Encrypt;
	}

	public void setEncrypt(String encrypt) {
		Encrypt = encrypt;
	}
}
