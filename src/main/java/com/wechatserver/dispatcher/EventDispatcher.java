package com.wechatserver.dispatcher;

import java.util.Map;

import com.wechatserver.util.MsgHandleUtil;

/**
 * ClassName: EventDispatcher
 * 
 * @Description: 事件消息业务分发器
 */
public class EventDispatcher {
	public static String processEvent(Map<String, String> map) {
		// 获取事件类型
		String msgType = map.get("Event").toString();
		switch (msgType) {

		case MsgHandleUtil.EVENT_TYPE_SUBSCRIBE:// 关注事件

			break;
		case MsgHandleUtil.EVENT_TYPE_UNSUBSCRIBE:// 取消关注事件

			break;
		case MsgHandleUtil.EVENT_TYPE_SCAN:// 扫描二维码事件

			break;
		case MsgHandleUtil.EVENT_TYPE_LOCATION:// 位置上报事件

			break;
		case MsgHandleUtil.EVENT_TYPE_CLICK:// 自定义菜单点击事件

			break;
		case MsgHandleUtil.EVENT_TYPE_VIEW: // 自定义菜单 View 事件

			break;
		default:
			break;
		}
		return null;
	}
}
