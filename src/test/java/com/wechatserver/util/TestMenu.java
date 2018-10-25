package com.wechatserver.util;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wechatserver.entry.menu.ClickButton;
import com.wechatserver.entry.menu.ViewButton;

public class TestMenu {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		ClickButton newMsgBt = new ClickButton();
		newMsgBt.setName("最新消息");
		newMsgBt.setType("click");
		newMsgBt.setKey("newMsgBt");
		ClickButton serMsgBt = new ClickButton();
		serMsgBt.setName("客户服务");
		serMsgBt.setType("click");
		serMsgBt.setKey("serMsgBt");
		ViewButton gwBt = new ViewButton();
		gwBt.setName("官方网站");
		gwBt.setType("view");
		gwBt.setUrl("www.baidu.com");
		ClickButton textMsgBt = new ClickButton();
		textMsgBt.setName("文本测试");
		textMsgBt.setType("click");
		textMsgBt.setKey("textMsgBt");
		ClickButton imageMsgBt = new ClickButton();
		imageMsgBt.setName("图片测试");
		imageMsgBt.setType("click");
		imageMsgBt.setKey("imageMsgBt");
		ClickButton musicMsgBt = new ClickButton();
		musicMsgBt.setName("音乐测试");
		musicMsgBt.setType("click");
		musicMsgBt.setKey("musicMsgBt");
		ClickButton videoMsgBt = new ClickButton();
		videoMsgBt.setName("视频测试");
		videoMsgBt.setType("click");
		videoMsgBt.setKey("videoMsgBt");
		ClickButton voiceMsgBt = new ClickButton();
		voiceMsgBt.setName("语音测试");
		voiceMsgBt.setType("click");
		voiceMsgBt.setKey("voiceMsgBt");
		// 创建菜单结构
		JSONArray sub_button = new JSONArray();
		sub_button.add(textMsgBt);
		sub_button.add(imageMsgBt);
		sub_button.add(musicMsgBt);
		sub_button.add(videoMsgBt);
		sub_button.add(voiceMsgBt);
		JSONObject sub_menu = new JSONObject();
		sub_menu.put("name", "菜单");
		sub_menu.put("sub_button", sub_button);
		JSONArray help_button = new JSONArray();
		help_button.add(gwBt);
		help_button.add(serMsgBt);
		JSONObject help_menu = new JSONObject();
		help_menu.put("name", "帮助服务");
		help_menu.put("sub_button", help_button);
		JSONArray main_button = new JSONArray();
		main_button.add(newMsgBt);
		main_button.add(sub_menu);
		main_button.add(help_menu);
		JSONObject btton = new JSONObject();
		btton.put("button", main_button);
		System.out.println(btton);
	}

}
