package com.wechatserver.dispatcher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wechatserver.entry.message.response.Article;
import com.wechatserver.entry.message.response.NewsMessage;
import com.wechatserver.entry.message.response.TextMessage;
import com.wechatserver.util.MsgHandleUtil;
import com.wechatserver.util.XStreamUtil;

/**
 * ClassName: EventDispatcher
 * 
 * @Description: 事件消息业务分发器
 */
public class EventDispatcher {
	public static String processEvent(Map<String, String> map) {
		// 获取事件类型
		String msgType = map.get("Event").toString();
		// base消息
		String toUserName = map.get("FromUserName").toString();
		String fromUserName = map.get("ToUserName").toString();
		SimpleDateFormat format = new SimpleDateFormat("yyyymmddHHmmss");
		Long createTime = Long.parseLong(format.format(new Date()));
		switch (msgType) {

		case MsgHandleUtil.EVENT_TYPE_SUBSCRIBE:// 关注事件
			TextMessage tm = new TextMessage();
			tm.setToUserName(toUserName);
			tm.setFromUserName(fromUserName);
			tm.setCreateTime(createTime);
			tm.setMsgType(msgType);
			tm.setContent("感谢关注，真好！");
			return XStreamUtil.toXML(tm);
		case MsgHandleUtil.EVENT_TYPE_UNSUBSCRIBE:// 取消关注事件
			System.out.println(toUserName + "用户已经取消关注！");
			break;
		case MsgHandleUtil.EVENT_TYPE_SCAN:// 扫描二维码事件
			// TODO
			System.out.println("扫描二维码事件");
			break;
		case MsgHandleUtil.EVENT_TYPE_LOCATION:// 位置上报事件
			// TODO
			System.out.println("位置上报事件");
			break;
		case MsgHandleUtil.EVENT_TYPE_CLICK:// 自定义菜单点击事件
			String clickKey = map.get("EventKey");
			// newMsgBt.setKey("newMsgBt");
			// serMsgBt.setKey("serMsgBt");
			// textMsgBt.setKey("textMsgBt");
			// imageMsgBt.setKey("imageMsgBt");
			// musicMsgBt.setKey("musicMsgBt");
			// videoMsgBt.setKey("videoMsgBt");
			// voiceMsgBt.setKey("voiceMsgBt");
			if ("newMsgBt".equals(clickKey)) {
				NewsMessage newsMsg = new NewsMessage();
				newsMsg.setToUserName(toUserName);
				newsMsg.setFromUserName(fromUserName);
				newsMsg.setCreateTime(createTime);
				newsMsg.setMsgType("news");
				newsMsg.setArticleCount(5);
				List<Article> articles = new ArrayList<Article>();
				Article art1 = new Article();
				art1.setTitle("微信公众平台图片最合适尺寸大小01");
				art1.setPicUrl(
						"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
				art1.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
				articles.add(art1);
				Article art2 = new Article();
				art1.setTitle("微信公众平台图片最合适尺寸大小02");
				art1.setPicUrl(
						"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
				art1.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
				articles.add(art2);
				Article art3 = new Article();
				art1.setTitle("微信公众平台图片最合适尺寸大小03");
				art1.setPicUrl(
						"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
				art1.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
				articles.add(art3);
				Article art4 = new Article();
				art1.setTitle("微信公众平台图片最合适尺寸大小04");
				art1.setPicUrl(
						"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
				art1.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
				articles.add(art4);
				Article art5 = new Article();
				art1.setTitle("微信公众平台图片最合适尺寸大小05");
				art1.setPicUrl(
						"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
				art1.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
				articles.add(art5);
				newsMsg.setArticles(articles);
				return XStreamUtil.toXML(newsMsg);
			}
			break;
		case MsgHandleUtil.EVENT_TYPE_VIEW: // 自定义菜单 View 事件
			System.out.println("自定义菜单 View 事件");
			break;
		default:
			break;
		}
		return "";
	}
}
