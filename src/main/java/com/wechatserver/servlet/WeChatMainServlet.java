package com.wechatserver.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wechatserver.util.AccessTokenUtils;
import com.wechatserver.util.ConnectionHandlerUtils;

@WebServlet(name = "WeChatMainServlet", urlPatterns = "/WeChatMainServlet", loadOnStartup = 1, initParams = {
		@WebInitParam(name = "appId", value = "wx29bdb41bfe33b029"),
		@WebInitParam(name = "appSecret", value = "338b417ce51eaaf3dcf08f7f979c3397") })
public class WeChatMainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		System.out.println("启动WebServlet");
		super.init();
		final String appId = getInitParameter("appId");
		final String appSecret = getInitParameter("appSecret");
		// 获取微信公众号接口权限
		AccessTokenUtils.getWeChatPermission(appId, appSecret);
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
			resp.getWriter().write(req.getParameter("echostr"));
		} else {
			System.out.println("签名校验失败");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * 文本消息XML数据格式 <xml> <ToUserName><![CDATA[toUser]]></ToUserName>
		 * <FromUserName><![CDATA[fromUser]]></FromUserName>
		 * <CreateTime>1348831860</CreateTime> <MsgType><![CDATA[text]]></MsgType>
		 * <Content><![CDATA[this is a test]]></Content> <MsgId>1234567890123456</MsgId>
		 * </xml>
		 */
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap();
		// 从request中取得输入流
		InputStream inputStream = req.getInputStream();
		System.out.println("获取输入流");
		try {
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList) {
				System.out.println(e.getName() + "|" + e.getText());
				map.put(e.getName(), e.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			inputStream.close();
			inputStream = null;
		}
		String message = String.format(
				"<xml>" + "<ToUserName><![CDATA[%s]]></ToUserName>" + "<FromUserName><![CDATA[%s]]></FromUserName>"
						+ "<CreateTime>%s</CreateTime>" + "<MsgType><![CDATA[text]]></MsgType>"
						+ "<Content><![CDATA[%s]]></Content>" + "</xml>",
				map.get("FromUserName"), map.get("ToUserName"), new Date(), "你好才是真的好！");
		resp.getWriter().println(message);

	}
}
