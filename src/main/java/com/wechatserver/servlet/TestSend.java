package com.wechatserver.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechatserver.dispatcher.EventDispatcher;
import com.wechatserver.entry.menu.ButtonKeys;
import com.wechatserver.util.MsgHandleUtil;

@WebServlet(name = "TestSend", urlPatterns = "/TestSend")
public class TestSend extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("FromUserName", "");
		map.put("ToUserName", "");
		map.put("Event", MsgHandleUtil.EVENT_TYPE_CLICK);
		map.put("EventKey", ButtonKeys.BUTTON_KEYS_F001);
		// String str = EventDispatcher.processEvent(map, resp);
		// System.out.println(str);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("aaaa");
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
}