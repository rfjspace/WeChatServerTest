package com.wechatserver.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.wechatserver.info.WechatConfigInfo;

public class TestMsgCryptionUtils {
	@Test
	public void test() throws ParserConfigurationException, SAXException, IOException {
		WechatConfigInfo.encodingAESKey = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
		WechatConfigInfo.token = "pamtest";
		WechatConfigInfo.timestamp = "1409304348";
		WechatConfigInfo.nonce = "xxxxxx";
		WechatConfigInfo.appId = "wxb11529c136998cb6";

		MsgHandlerUtils mhu = new MsgHandlerUtils();
		String replyMsg = "<xml>" + "	<ToUserName><![CDATA[123]]></ToUserName>"
				+ "	<FromUserName><![CDATA[456]]></FromUserName>" + "	<CreateTime>789</CreateTime>"
				+ "	<MsgType><![CDATA[text]]></MsgType>" + "	<Content><![CDATA[123456789]]></Content>" + "</xml>";
		String msgEncrypt = MsgCryptionUtils.msgEncrypt(replyMsg);
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
		String msgDecrypt = MsgCryptionUtils.msgDecrypt(msgSignature, encrypt);
		System.out.println("msgDecrypt : " + msgDecrypt);
	}
}
