package com.wechatserver.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wechatserver.entry.menu.ButtonKeys;
import com.wechatserver.entry.menu.ClickButton;
import com.wechatserver.entry.menu.ViewButton;
import com.wechatserver.entry.message.response.Article;
import com.wechatserver.entry.message.response.NewsMessage;
import com.wechatserver.global.GlobalVariables;
import com.wechatserver.mapper.DatebaseDDLMapper;
import com.wechatserver.mapper.MaterialFileMapper;
import com.wechatserver.mapper.MenuCreateMapper;
import com.wechatserver.mapper.RespArticleMapper;
import com.wechatserver.mapper.RespNewsMapper;

public class TestMybatis {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@org.junit.Test
	public void test() {
		SqlSession ss = MybatisUtil.getSession();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSSss");
		String articleId = sdf.format(new Date());

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
		RespArticleMapper am = ss.getMapper(RespArticleMapper.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(RespArticleMapper.ARTICLE_ID, articleId);
		map.put(RespArticleMapper.ARTICLE_LIST, artList);
		am.insertList(map);
		ss.commit();
		am.selectByArticleId(articleId);
		am.selectAll();
		ss.close();

	}

	@Test
	public void testDropTable() {
		SqlSession ss = MybatisUtil.getSession();
		DatebaseDDLMapper ddm = ss.getMapper(DatebaseDDLMapper.class);
		ddm.dropTable();
		ddm.createTable();
		// ddm.deleteTableData();
		ss.commit();
		ss.close();
	}

	@Test
	public void testButton() {
		// this.testDropTable();
		SqlSession ss = MybatisUtil.getSession();
		MenuCreateMapper mcm = ss.getMapper(MenuCreateMapper.class);
		ClickButton newMsgBt = new ClickButton();
		newMsgBt.setName("最新消息");
		newMsgBt.setType("click");
		newMsgBt.setKey(ButtonKeys.BUTTON_KEYS_F001);
		ViewButton gwBt = new ViewButton();
		gwBt.setName("官方网站");
		gwBt.setType("view");
		gwBt.setUrl(WeChatApiUtil.getUrlEncode("http://www.4nyb55.natappfree.cc/WeChatServerTest/AuthForwardServlet"));
		ClickButton serMsgBt = new ClickButton();
		serMsgBt.setName("客户服务");
		serMsgBt.setType("click");
		serMsgBt.setKey(ButtonKeys.SUBBUTTON_KEYS_S032);
		ClickButton textMsgBt = new ClickButton();
		textMsgBt.setName("文本测试");
		textMsgBt.setType("click");
		textMsgBt.setKey(ButtonKeys.SUBBUTTON_KEYS_S021);
		ClickButton imageMsgBt = new ClickButton();
		imageMsgBt.setName("图片测试");
		imageMsgBt.setType("click");
		imageMsgBt.setKey(ButtonKeys.SUBBUTTON_KEYS_S022);
		ClickButton musicMsgBt = new ClickButton();
		musicMsgBt.setName("音乐测试");
		musicMsgBt.setType("click");
		musicMsgBt.setKey(ButtonKeys.SUBBUTTON_KEYS_S023);
		ClickButton videoMsgBt = new ClickButton();
		videoMsgBt.setName("视频测试");
		videoMsgBt.setType("click");
		videoMsgBt.setKey(ButtonKeys.SUBBUTTON_KEYS_S024);
		ClickButton voiceMsgBt = new ClickButton();
		voiceMsgBt.setName("语音测试");
		voiceMsgBt.setType("click");
		voiceMsgBt.setKey(ButtonKeys.SUBBUTTON_KEYS_S025);
		// // 创建菜单结构
		// JSONArray sub_button = new JSONArray();
		// sub_button.add(textMsgBt);
		// sub_button.add(imageMsgBt);
		// sub_button.add(musicMsgBt);
		// sub_button.add(videoMsgBt);
		// sub_button.add(voiceMsgBt);
		// JSONObject sub_menu = new JSONObject();
		// sub_menu.put("name", "消息菜单");
		// sub_menu.put("sub_button", sub_button);
		// JSONArray help_button = new JSONArray();
		// help_button.add(gwBt);
		// help_button.add(serMsgBt);
		// JSONObject help_menu = new JSONObject();
		// help_menu.put("name", "帮助服务");
		// help_menu.put("sub_button", help_button);
		// JSONArray main_button = new JSONArray();
		// main_button.add(newMsgBt);
		// main_button.add(sub_menu);
		// main_button.add(help_menu);
		// JSONObject menu = new JSONObject();
		// menu.put("button", main_button);
		mcm.insertClickButton(newMsgBt, 1, null);
		mcm.insertSubButton("消息菜单");
		mcm.insertSubButton("帮助服务");
		mcm.insertViewButton(gwBt, 2, "帮助服务");
		mcm.insertClickButton(serMsgBt, 2, "帮助服务");
		mcm.insertClickButton(textMsgBt, 2, "消息菜单");
		mcm.insertClickButton(imageMsgBt, 2, "消息菜单");
		mcm.insertClickButton(musicMsgBt, 2, "消息菜单");
		mcm.insertClickButton(videoMsgBt, 2, "消息菜单");
		mcm.insertClickButton(voiceMsgBt, 2, "消息菜单");
		ss.commit();
		// SqlSession ss = MybatisUtil.getSession();
		// MenuCreateMapper mcm = ss.getMapper(MenuCreateMapper.class);
		List<Map<String, Object>> menuList = mcm.selectMainMenu();
		JSONObject menu = new JSONObject();
		JSONArray mainButton = new JSONArray();
		for (Map<String, Object> m : menuList) {
			int level = (int) m.get("menu_level");
			if (level == 1) {
				String mainType = m.get("button_type").toString();
				switch (mainType) {
				case "sub":
					String helpMenu = m.get("button_name").toString();
					JSONObject subMenu = new JSONObject();
					subMenu.put("name", helpMenu);
					JSONArray subButton = new JSONArray();
					List<Map<String, Object>> childList = mcm.selectBySuperName(helpMenu);
					for (Map<String, Object> c : childList) {
						String subType = c.get("button_type").toString();
						switch (subType) {
						case "click":
							ClickButton cb = new ClickButton();
							cb.setType(c.get("button_type").toString());
							cb.setName(c.get("button_name").toString());
							cb.setKey(c.get("click_key").toString());
							subButton.add(cb);
							break;
						case "view":
							ViewButton vb = new ViewButton();
							vb.setType(c.get("button_type").toString());
							vb.setName(c.get("button_name").toString());
							vb.setUrl(c.get("view_url").toString());
							subButton.add(vb);
							break;
						default:
							break;
						}
					}
					subMenu.put("sub_button", subButton);
					mainButton.add(subMenu);
					break;
				case "click":
					ClickButton cb = new ClickButton();
					cb.setType(m.get("button_type").toString());
					cb.setName(m.get("button_name").toString());
					cb.setKey(m.get("click_key").toString());
					mainButton.add(cb);
					break;
				case "view":
					ViewButton vb = new ViewButton();
					vb.setType(m.get("button_type").toString());
					vb.setName(m.get("button_name").toString());
					vb.setUrl(m.get("view_url").toString());
					mainButton.add(vb);
					break;
				default:
					break;
				}
			}
		}
		menu.put("button", mainButton);
		System.out.println(menu);
		ss.close();
	}

	@Test
	public void testMain() {
		SqlSession ss = MybatisUtil.getSession();
		// base消息
		String toUserName = "toUserName";
		String fromUserName = "fromUserName";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmSSss");
		Long createTime = Long.parseLong(format.format(new Date()));
		NewsMessage newsMsg = new NewsMessage();
		newsMsg.setToUserName(toUserName);
		newsMsg.setFromUserName(fromUserName);
		newsMsg.setCreateTime(createTime);
		newsMsg.setMsgType("news");
		List<Article> articles = new ArrayList<Article>();
		Article art1 = new Article();
		art1.setTitle("微信公众平台图片最合适尺寸大小01");
		art1.setPicUrl(
				"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
		art1.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
		articles.add(art1);
		Article art2 = new Article();
		art2.setTitle("微信公众平台图片最合适尺寸大小02");
		art2.setPicUrl(
				"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
		art2.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
		articles.add(art2);
		Article art3 = new Article();
		art3.setTitle("微信公众平台图片最合适尺寸大小03");
		art3.setPicUrl(
				"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
		art3.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
		articles.add(art3);
		newsMsg.setArticles(articles);
		newsMsg.setArticleCount(articles.size());

		RespNewsMapper rnm = ss.getMapper(RespNewsMapper.class);
		rnm.insert(newsMsg);
		RespArticleMapper ram = ss.getMapper(RespArticleMapper.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(RespArticleMapper.ARTICLE_ID, createTime.toString());
		map.put(RespArticleMapper.ARTICLE_LIST, articles);
		ram.insertList(map);
		ss.commit();
		NewsMessage newM = rnm.selectLastNewsMessage();
		for (Article a : newM.getArticles()) {
			System.out.println(a.getTitle());
		}
		ss.close();
	}

	@Test
	public void testMaterialFile() throws Exception {
		this.testDropTable();
		SqlSession ss = MybatisUtil.getSession();
		MaterialFileMapper mfm = ss.getMapper(MaterialFileMapper.class);

		String location = "/images/";
		String name = "image01.jpg";
		String type = "image";
		File file = new ResourceLoadUtil().fileLoad(location.concat(name));
		GlobalVariables.accessToken = "15_UmpEVQgdYQPBpxShJGjhfAd2AATOEGrHvKAmAbzxKfbtUd6wUOgWPNS5mL7A4tgJ-IyJpzaYwbB4LgI_cY7eQCEnSB3FSdfpxUL19iTyzIoIV7x7VZD5XMq1vTheoihRLnyE5bbFz9C49iHPKPMiADATYV";
		String mediaId = WeChatApiUtil.getUploadMediaId(file, "image");
		if (mediaId == null) {
			throw new Exception();
		}
		Map<String, String> fileMap = new HashMap<String, String>();
		fileMap.put("matName", name);
		fileMap.put("matType", type);
		fileMap.put("matId", mediaId);
		mfm.insertFile(fileMap);
		mfm.selectFileByName(name);
		ss.commit();
		ss.close();
	}
}
