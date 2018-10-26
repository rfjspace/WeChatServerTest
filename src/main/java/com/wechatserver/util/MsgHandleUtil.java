package com.wechatserver.util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wechatserver.entry.message.response.TextMessage;

/**
 * ClassName: MsgHandleUtil
 * 
 * @Description: 消息处理工具类
 */
public class MsgHandleUtil {
	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 返回消息类型：图片
	 */
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 返回消息类型：语音
	 */
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 返回消息类型：视频
	 */
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：视频
	 */
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 事件类型：VIEW(自定义菜单 URl 视图)
	 */
	public static final String EVENT_TYPE_VIEW = "VIEW";

	/**
	 * 事件类型：LOCATION(上报地理位置事件)
	 */
	public static final String EVENT_TYPE_LOCATION = "LOCATION";

	/**
	 * 事件类型：SCAN(二维码扫码事件)
	 */
	public static final String EVENT_TYPE_SCAN = "SCAN";

	/***
	 * cryptFlg
	 */
	public static String CRYPTFLG = null;

	/***
	 * 将微信公众号消息（明文模式和安全模式）转化为message集合
	 * 
	 * @param inputMsg
	 *            微信公众号消息流
	 * @return message集合
	 */
	public static Map<String, String> msgParse(InputStream inputMsg) {
		// inputStream转map
		Map<String, String> cryptMap = parseMap(inputMsg);
		if (null != cryptMap.get("Encrypt")) {
			CRYPTFLG = "Encrypt";
			String msgCrypt = CryptionUtil.msgDecrypt(cryptMap.get("MsgSignature").toString(),
					cryptMap.get("Encrypt").toString());// 消息解密
			return parseMap(msgCrypt);// string转map
		}
		CRYPTFLG = "UnEncrypt";
		return cryptMap;
	}

	/***
	 * 将微信服务器InputStream消息转化为Map集合
	 * 
	 * @param input
	 *            明文模式数据
	 * @return 微信服务器消息的K-V的映射集合
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, String> parseMap(InputStream input) {
		Map<String, String> map = new HashMap<String, String>();
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
	 * 将微信服务器String消息转化为Map集合
	 * 
	 * @param input
	 *            安全模式数据
	 * @return
	 */
	private static Map<String, String> parseMap(String input) {
		Map<String, String> map = new HashMap<String, String>();
		// 读取输入流
		Document document = getDocument(input);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		@SuppressWarnings("unchecked")
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
	private static Document getDocument(InputStream input) {
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
	 * 将String类型消息转化为Xml格式数据
	 * 
	 * @param input
	 *            微信服务器消息
	 * @return 微信服务器消息的Xml格式
	 */
	private static Document getDocument(String input) {
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
	 * @param msgType
	 *            要返回的数据类型
	 * @return 将要的返回消息
	 */
	public String buildRespMsg(Map<String, Object> map, String msgType) {
		String respMsg = "";
		String toUserName = map.get("FromUserName").toString();
		String fromUserName = map.get("ToUserName").toString();
		SimpleDateFormat format = new SimpleDateFormat("yyyymmddHHmmss");
		Long createTime = Long.parseLong(format.format(new Date()));
		//TODO 将数据库消息集合转化为XML消息，或者直接将select的实体类转换？DB结构？
		switch (msgType) {
		case RESP_MESSAGE_TYPE_TEXT:
			TextMessage tm = new TextMessage();
			tm.setToUserName(toUserName);
			tm.setFromUserName(fromUserName);
			tm.setCreateTime(createTime);
			tm.setMsgType(RESP_MESSAGE_TYPE_TEXT);
			tm.setContent(map.get("Content").toString());
			respMsg = XStreamUtil.toXML(tm);
			break;
		case RESP_MESSAGE_TYPE_IMAGE:
			break;
		case RESP_MESSAGE_TYPE_MUSIC:
			break;
		case RESP_MESSAGE_TYPE_NEWS:
			break;
		case RESP_MESSAGE_TYPE_VIDEO:
			break;
		case RESP_MESSAGE_TYPE_VOICE:
			break;
		default:
			break;
		}
		return respMsg;
	}

	// private String getFormatStr(String location) {
	// final int beginIndex = 39;
	// InputStream is;
	// try {
	// is = getClass().getResourceAsStream(location);
	// String formatStr = getDocument(is).asXML();
	// int endIndex = formatStr.length();
	// is.close();
	// return formatStr.substring(beginIndex, endIndex).replaceAll("[\\s]", "");
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return "";
	// }

}
