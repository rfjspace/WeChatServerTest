package com.wechatserver.dispatcher;

import java.util.Map;

import com.wechatserver.util.MsgHandleUtil;

/**
 * ClassName: MsgDispatcher
 * 
 * @Description: 消息业务处理分发器
 */
public class MsgDispatcher {
	public static String processMessage(Map<String, String> map) {
		// 获取消息类型
		String msgType = map.get("MsgType").toString();
		switch (msgType) {
		case MsgHandleUtil.REQ_MESSAGE_TYPE_TEXT:// 文本消息
			System.out.println("文本消息");
			break;
		case MsgHandleUtil.REQ_MESSAGE_TYPE_IMAGE:// 图片消息
			System.out.println("图片消息");
			break;
		case MsgHandleUtil.REQ_MESSAGE_TYPE_LINK:// 链接消息
			System.out.println("链接消息");
			break;
		case MsgHandleUtil.REQ_MESSAGE_TYPE_LOCATION:// 位置消息
			System.out.println("位置消息");
			break;
		case MsgHandleUtil.REQ_MESSAGE_TYPE_VIDEO:// 视频消息
			System.out.println("视频消息");
			break;
		case MsgHandleUtil.REQ_MESSAGE_TYPE_VOICE:// 语音消息
			System.out.println("语音消息");
			break;
		default:
			break;
		}
		return "";
	}
}
