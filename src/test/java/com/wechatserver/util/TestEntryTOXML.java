package com.wechatserver.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wechatserver.entry.message.response.Article;
import com.wechatserver.entry.message.response.NewsMessage;
import com.wechatserver.entry.message.response.TextMessage;
import com.wechatserver.entry.message.response.Voice;
import com.wechatserver.entry.message.response.VoiceMessage;

public class TestEntryTOXML {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test1() {
		VoiceMessage textMsg = new VoiceMessage();
		textMsg.setFromUserName("fromUserName");
		textMsg.setToUserName("toUserName");
		textMsg.setCreateTime(123456789);
		textMsg.setMsgType("voice");
		Voice voice = new Voice();
		voice.setMediaId("111111111");
		textMsg.setVoice(voice);
		String str = XStreamUtil.toXML(textMsg);
		System.out.println(str);
	}

	@Test
	public void test2() {
		String str = "<xml><Content><![CDATA[78945566]]></Content><ToUserName><![CDATA[toUserName]]></ToUserName><FromUserName><![CDATA[fromUserName]]></FromUserName><CreateTime><![CDATA[123456789]]></CreateTime><MsgType><![CDATA[text]]></MsgType><MsgId>123456789</MsgId></xml>";
		TextMessage textMsg = (TextMessage) XStreamUtil.fromXML(str, TextMessage.class);
		System.out.println(textMsg.getContent());

	}

	@Test
	public void test3() {
		NewsMessage newsMsg = new NewsMessage();
		newsMsg.setFromUserName("fromUserName");
		newsMsg.setToUserName("toUserName");
		newsMsg.setCreateTime(123456789);
		newsMsg.setMsgType("news");
		newsMsg.setArticleCount(2);
		Article art1 = new Article();
		art1.setTitle("Article1");
		art1.setPicUrl("PicUrl1");
		art1.setUrl("www.baidu.com");
		art1.setDescription("Article1");
		Article art2 = new Article();
		art2.setTitle("Article2");
		art2.setPicUrl("PicUrl2");
		art2.setUrl("www.google.com");
		art2.setDescription("Article2");
		List<Article> artList = new ArrayList<Article>();
		artList.add(art1);
		artList.add(art2);
		newsMsg.setArticles(artList);
		String str = XStreamUtil.toXML(newsMsg).replaceAll("[\\s]", "");
		System.out.println(str);
	}

	@Test
	public void test4() {
		String str = "<xml><ArticleCount><![CDATA[2]]></ArticleCount><Articles><item><Title><![CDATA[Article1]]></Title><Description><![CDATA[Article1]]></Description><PicUrl><![CDATA[PicUrl1]]></PicUrl><Url><![CDATA[www.baidu.com]]></Url></item><item><Title><![CDATA[Article2]]></Title><Description><![CDATA[Article2]]></Description><PicUrl><![CDATA[PicUrl2]]></PicUrl><Url><![CDATA[www.google.com]]></Url></item></Articles><ToUserName><![CDATA[toUserName]]></ToUserName><FromUserName><![CDATA[fromUserName]]></FromUserName><CreateTime><![CDATA[123456789]]></CreateTime><MsgType><![CDATA[news]]></MsgType></xml>";
		NewsMessage textMsg = (NewsMessage) XStreamUtil.fromXML(str, NewsMessage.class);
		System.out.println(textMsg.getArticleCount());

	}
}
