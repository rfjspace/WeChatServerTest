package com.wechatserver.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MessageHandlerUtils {
	/***
	 * 将微信服务器消息转化为Map集合
	 * 
	 * @param input
	 *            微信服务器消息
	 * @return 微信服务器消息的K-V的映射集合
	 */
	public Map<String, Object> transFormXML(InputStream input) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 读取输入流
		Document document = getDocument(input);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();
		// 遍历所有子节点
		for (Element e : elementList) {
			map.put(e.getName().toString(), e.getText());
		}
		return map;
	}

	/***
	 * 将微信服务器消息转化为Xml格式数据
	 * 
	 * @param input
	 *            微信服务器消息
	 * @return 微信服务器消息的Xml格式
	 */
	private Document getDocument(InputStream input) {
		Document document = null;
		// 读取输入流
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(input);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

	/***
	 * 构建服务器返回消息
	 * 
	 * @param map
	 *            将要返回的数据源
	 * @param msgTypeEnum
	 *            要返回的数据类型
	 * @return 将要的返回消息
	 */
	public String buildReturnMsg(Map<String, Object> map, SendMsgType msgTypeEnum) {
		String returnMsg = "";
		String formatStr = "";// 返回消息的格式字串
		String toUserName = map.get("FromUserName").toString();
		String fromUserName = map.get("ToUserName").toString();
		String createTime = map.get("CreateTime").toString();
		switch (msgTypeEnum) {
		case text:
			formatStr = getFormatStr("/format/text.xml");
			String content = map.get("Content").toString();
			returnMsg = String.format(formatStr, toUserName, fromUserName, createTime, content);
			break;
		case image:
			formatStr = getFormatStr("/format/image.xml");
			String iMediaId = map.get("MediaId").toString();
			returnMsg = String.format(formatStr, toUserName, fromUserName, createTime, iMediaId);
			break;
		case voice:
			formatStr = getFormatStr("/format/voice.xml");
			String vcMediaId = map.get("MediaId").toString();
			returnMsg = String.format(formatStr, toUserName, fromUserName, createTime, vcMediaId);
			break;
		case video:
			formatStr = getFormatStr("/format/video.xml");
			String vdMediaId = map.get("MediaId").toString();
			String thumbMediaId = map.get("ThumbMediaId").toString();
			String vTitle = map.get("Title").toString();
			String vDescription = map.get("Description").toString();
			returnMsg = String.format(formatStr, toUserName, fromUserName, createTime, vdMediaId, thumbMediaId, vTitle,
					vDescription);
			break;
		case music:
			formatStr = getFormatStr("/format/music.xml");
			String Title = map.get("Title").toString();
			String Description = map.get("Description").toString();
			String MusicURL = map.get("MusicURL").toString();
			String HQMusicUrl = map.get("HQMusicUrl").toString();
			String ThumbMediaId = map.get("ThumbMediaId").toString();
			returnMsg = String.format(formatStr, toUserName, fromUserName, createTime, Title, Description, MusicURL,
					HQMusicUrl, ThumbMediaId);
			break;
		case news:
			formatStr = getFormatStr("/format/news.xml");
			String ArticleCount = map.get("ArticleCount").toString();
			String Articles = map.get("Articles").toString();
			returnMsg = String.format(formatStr, toUserName, fromUserName, createTime, ArticleCount, Articles);
			break;
		default:
			break;
		}
		return returnMsg;
	}

	private String getFormatStr(String location) {
		final int beginIndex = 39;
		InputStream is = getClass().getResourceAsStream(location);
		String formatStr = getDocument(is).asXML();
		int endIndex = formatStr.length();
		return formatStr.substring(beginIndex, endIndex).replaceAll("[\\s]", "");
	}

	public void resolveAcceptMsg(Map<String, Object> map) {
		AcceptMsgType msgType = AcceptMsgType.valueOf(map.get("MsgType").toString());
		switch (msgType) {
		case text:
			break;
		case image:
			break;
		case voice:
			break;
		case video:
			break;
		case shortvideo:
			break;
		case location:
			break;
		case link:
			break;
		case event:
			break;
		default:
			break;
		}
	}

	public enum AcceptMsgType {
		text, // 文本
		image, // 图片
		voice, // 语音
		video, // 视频
		shortvideo, // 小视频
		location, // 位置
		link, // 链接
		event// 事件
	}

	public enum SendMsgType {
		text, // 文本
		image, // 图片
		voice, // 语音
		video, // 视频
		music, // 音乐
		news, // 图文消息
	}
}
