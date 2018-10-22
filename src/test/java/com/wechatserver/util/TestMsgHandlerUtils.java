package com.wechatserver.util;

import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.wechatserver.info.SendMsgType;
import com.wechatserver.info.WechatConfigInfo;

public class TestMsgHandlerUtils {
	@Test
	public void testCrypt() throws Exception {
		MsgHandlerUtils mhu = new MsgHandlerUtils();
		WechatConfigInfo.encodingAESKey = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
		WechatConfigInfo.token = "pamtest";
		WechatConfigInfo.timestamp = "1409304348";
		WechatConfigInfo.nonce = "xxxxxx";
		WechatConfigInfo.appId = "wxb11529c136998cb6";

		String replyMsg = "<xml>" + "	<ToUserName><![CDATA[123]]></ToUserName>"
				+ "	<FromUserName><![CDATA[456]]></FromUserName>" + "	<CreateTime>789</CreateTime>"
				+ "	<MsgType><![CDATA[text]]></MsgType>" + "	<Content><![CDATA[123456789]]></Content>" + "</xml>";
		String msgEncrypt = mhu.msgEncrypt(replyMsg);
		System.out.println("msgEncrypt : " + msgEncrypt);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(msgEncrypt);
		InputSource is = new InputSource(sr);
		Document document = db.parse(is);
		Element root = document.getDocumentElement();
		NodeList nodelist1 = root.getElementsByTagName("Encrypt");
		NodeList nodelist2 = root.getElementsByTagName("MsgSignature");

		String encrypt = nodelist1.item(0).getTextContent();
		String msgSignature = nodelist2.item(0).getTextContent();
		String msgDecrypt = mhu.msgDecrypt(msgSignature, encrypt);
		System.out.println("msgDecrypt : " + msgDecrypt);
	}

	@Test
	public void testBuildReponseMsg() {
		MsgHandlerUtils mUtil = new MsgHandlerUtils();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ToUserName", "123");
		map.put("FromUserName", "321");
		map.put("CreateTime", new Date().toString());
		map.put("Content", "aaaaaa");
		SendMsgType msgTypeEnum = SendMsgType.TEXT;
		String resoult = mUtil.buildReponseMsg(map, msgTypeEnum);
		System.out.println(resoult);
	}
}
