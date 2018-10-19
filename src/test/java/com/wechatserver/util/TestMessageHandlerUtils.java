package com.wechatserver.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.wechatserver.util.MessageHandlerUtils.SendMsgType;

public class TestMessageHandlerUtils {
	@Test
	public void test() {
		MessageHandlerUtils mUtil = new MessageHandlerUtils();

		Map<String, Object> map = new HashMap<String, Object>();
		/*
		 * <ToUserName><![CDATA[%s]]></ToUserName>
		 * <FromUserName><![CDATA[%s]]></FromUserName> <CreateTime>%s</CreateTime>
		 * <MsgType><![CDATA[text]]></MsgType> <Content><![CDATA[%s]]></Content>
		 */
		map.put("ToUserName", "123");
		map.put("FromUserName", "321");
		map.put("CreateTime", new Date().toString());
		map.put("Content", "aaaaaa");
		SendMsgType msgTypeEnum = SendMsgType.text;
		String resoult = mUtil.buildReturnMsg(map, msgTypeEnum);
		System.out.println(resoult);
	}

}
