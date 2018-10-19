package com.wechatserver.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wechatserver.info.WechatConfigInfo;
import com.wechatserver.util.AccessTokenUtils;
import com.wechatserver.util.ConnectionHandlerUtils;
import com.wechatserver.util.MessageHandlerUtils;
import com.wechatserver.util.PropertiesLoadUtils;
import com.wechatserver.util.MessageHandlerUtils.SendMsgType;

@WebServlet(name = "WeChatMainServlet", urlPatterns = "/WeChatMainServlet", loadOnStartup = 1)
public class WeChatMainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		PropertiesLoadUtils plu = new PropertiesLoadUtils();
		Properties proper = plu.propertiesLoad("/wechatConfigInfo.properties");
		WechatConfigInfo.appId = proper.getProperty("appId");
		WechatConfigInfo.appSecret = proper.getProperty("appSecret");
		WechatConfigInfo.token = proper.getProperty("token");
		WechatConfigInfo.encodingAESKey = proper.getProperty("encodingAESKey");
		// 获取微信公众号接口权限
		AccessTokenUtils.getWeChatPermission(WechatConfigInfo.appId, WechatConfigInfo.appSecret);
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
		if (ConnectionHandlerUtils.wechatConnectionVaildate(map, WechatConfigInfo.token)) {
			resp.getWriter().write(req.getParameter("echostr"));
		} else {
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		MessageHandlerUtils mUtil = new MessageHandlerUtils();
		Map<String, Object> map = new HashMap<String, Object>();
		map = mUtil.transFormXML(req.getInputStream());
		SendMsgType msgTypeEnum = SendMsgType.valueOf(map.get("MsgType").toString());
		String resoult = mUtil.buildReturnMsg(map, msgTypeEnum);
		System.out.println(resoult);
		resp.getWriter().println(resoult);

	}
}
