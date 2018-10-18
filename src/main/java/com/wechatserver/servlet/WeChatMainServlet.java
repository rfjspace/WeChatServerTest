package com.wechatserver.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechatserver.util.ConnectionHandlerUtils;

@WebServlet(
		name = "WeChatMainServlet", 
        urlPatterns = "/WeChatMainServlet", 
        loadOnStartup = 1, 
        initParams = {
		   @WebInitParam(name = "appId", value = ""), 
		   @WebInitParam(name = "appSecret", value = "") 
		})
public class WeChatMainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("signature", req.getParameter("signature"));
		map.put("timestamp", req.getParameter("timestamp"));
		map.put("nonce", req.getParameter("nonce"));
		map.put("echostr", req.getParameter("echostr"));
		
		System.out.println("开始校验签名");
		if (ConnectionHandlerUtils.wechatConnectionVaildate(map)) {
			System.out.println("签名校验通过");
			resp.getWriter().write("感谢关注，真好。");
		} else {
			System.out.println("签名校验失败");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}
}
