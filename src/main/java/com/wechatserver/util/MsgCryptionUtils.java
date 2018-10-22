package com.wechatserver.util;

import com.wechatserver.info.WechatConfigInfo;
import com.wechatserver.wxapi.AesException;
import com.wechatserver.wxapi.WXBizMsgCrypt;

/***
 * 微信消息的加密解密
 * 
 * @author Administrator
 *
 */
public class MsgCryptionUtils {

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
			WXBizMsgCrypt crypt = new WXBizMsgCrypt(WechatConfigInfo.token, WechatConfigInfo.encodingAESKey,
					WechatConfigInfo.appId);
			String xmlFormat = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
			String fromXML = String.format(xmlFormat, encrypt);
			return crypt.decryptMsg(msgSignature, WechatConfigInfo.timestamp, WechatConfigInfo.nonce, fromXML);

		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}

	/***
	 * 微信消息的加密
	 * 
	 * @param messages
	 * @return
	 */
	public static String msgEncrypt(String replyMsg) {
		try {
			WXBizMsgCrypt crypt = new WXBizMsgCrypt(WechatConfigInfo.token, WechatConfigInfo.encodingAESKey,
					WechatConfigInfo.appId);
			return crypt.encryptMsg(replyMsg, WechatConfigInfo.timestamp, WechatConfigInfo.nonce);

		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}
}
