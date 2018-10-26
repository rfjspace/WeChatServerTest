package com.wechatserver.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wechatserver.dispatcher.EventDispatcher;
import com.wechatserver.dispatcher.MsgDispatcher;
import com.wechatserver.entry.menu.ButtonKeys;
import com.wechatserver.entry.menu.ClickButton;
import com.wechatserver.entry.menu.ViewButton;
import com.wechatserver.info.GlobalVariables;
import com.wechatserver.util.CryptionUtil;
import com.wechatserver.util.MsgHandleUtil;
import com.wechatserver.util.SignCheckUtil;
import com.wechatserver.util.WeChatApiUtil;

/**
 * ClassName: MainProcessServlet
 * 
 * @Description: 服务器主程序
 */
@WebServlet(name = "MainProcessServlet", urlPatterns = "/MainProcessServlet")
public class MainProcessServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 字符编码转换
		this.setGeneralAttributes(req, resp);
		// 全局属性设置
		GlobalVariables.signature = req.getParameter("signature");
		GlobalVariables.timestamp = req.getParameter("timestamp");
		GlobalVariables.nonce = req.getParameter("nonce");
		GlobalVariables.echostr = req.getParameter("echostr");
		// 签名验证
		if (SignCheckUtil.checkSignature(GlobalVariables.signature, GlobalVariables.timestamp, GlobalVariables.nonce)) {
			resp.getWriter().write(GlobalVariables.echostr);
		} else {
			throw new ServletException("连接失败");
		}
		// 全局属性打印
		GlobalVariables.infoPrint();
		// 创建菜单按钮
		ClickButton newMsgBt = new ClickButton();
		newMsgBt.setName("最新消息");
		newMsgBt.setType("click");
		newMsgBt.setKey(ButtonKeys.BUTTON_KEYS_F001);
		ViewButton gwBt = new ViewButton();
		gwBt.setName("官方网站");
		gwBt.setType("view");
		gwBt.setUrl("https://www.baidu.com");
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
		// 创建菜单结构
		JSONArray sub_button = new JSONArray();
		sub_button.add(textMsgBt);
		sub_button.add(imageMsgBt);
		sub_button.add(musicMsgBt);
		sub_button.add(videoMsgBt);
		sub_button.add(voiceMsgBt);
		JSONObject sub_menu = new JSONObject();
		sub_menu.put("name", "消息菜单");
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
		JSONObject menu = new JSONObject();
		menu.put("button", main_button);
		// 创建菜单
		WeChatApiUtil.createCustomMenu(menu.toJSONString());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 字符编码转换
		this.setGeneralAttributes(req, resp);
		// 响应消息
		String respMsg = "";
		// 全局属性设置
		GlobalVariables.signature = req.getParameter("signature");
		GlobalVariables.timestamp = req.getParameter("timestamp");
		GlobalVariables.nonce = req.getParameter("nonce");
		GlobalVariables.echostr = req.getParameter("echostr");
		// 将消息解密并转换为集合
		Map<String, String> map = MsgHandleUtil.msgParse(req.getInputStream());
		// 取得消息类型
		String msgType = map.get("MsgType").toString();
		// 消息处理的分发
		if (MsgHandleUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
			respMsg = EventDispatcher.processEvent(map); // 进入事件处理
		} else {
			respMsg = MsgDispatcher.processMessage(map); // 进入消息处理
		}
		// 将响应消息加密
		if ("Encrypt".equals(MsgHandleUtil.CRYPTFLG)) {
			respMsg = CryptionUtil.msgEncrypt(respMsg);
		}
		// 发送响应消息
		resp.getWriter().write(respMsg);
	}

	/***
	 * Request，Response一般属性的设定
	 * 
	 * @param req
	 *            Request
	 * @param resp
	 *            Response
	 */
	private void setGeneralAttributes(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
