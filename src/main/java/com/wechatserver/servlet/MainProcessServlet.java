package com.wechatserver.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechatserver.dispatcher.EventDispatcher;
import com.wechatserver.dispatcher.MsgDispatcher;
import com.wechatserver.info.GlobalVariables;
import com.wechatserver.util.CryptionUtil;
import com.wechatserver.util.MsgHandleUtil;
import com.wechatserver.util.SignCheckUtil;

/**
 * ClassName: MainProcessServlet
 * 
 * @Description: 服务器主程序
 */
@WebServlet(name = "MainProcessServlet", urlPatterns = "/MainProcessServlet")
public class MainProcessServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 字符编码转换
		this.setGeneralAttributes(req, resp);
		// 全局属性设置
		GlobalVariables.signature = req.getParameter(GlobalVariables.signature);
		GlobalVariables.timestamp = req.getParameter(GlobalVariables.timestamp);
		GlobalVariables.nonce = req.getParameter(GlobalVariables.nonce);
		GlobalVariables.echostr = req.getParameter("echostr");
		// 签名验证
		if (SignCheckUtil.checkSignature(GlobalVariables.signature, GlobalVariables.timestamp, GlobalVariables.nonce)) {
			resp.getWriter().write(GlobalVariables.echostr);
		} else {
			throw new ServletException("连接失败");
		}
		// 全局属性打印
		GlobalVariables.infoPrint();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 字符编码转换
		this.setGeneralAttributes(req, resp);
		// 响应消息
		String respMsg = "";
		// 将消息解密并转换为集合
		Map<String, String> map = MsgHandleUtil.msgParse(req.getInputStream());
		// 取得消息类型
		String msgType = map.get("MsgType").toString();
		// 消息处理的分发
		if (msgType == MsgHandleUtil.REQ_MESSAGE_TYPE_EVENT) {
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
