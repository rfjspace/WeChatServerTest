package com.wechatserver.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechatserver.info.AcceptMsgType;
import com.wechatserver.info.SendMsgType;
import com.wechatserver.info.WechatConfigInfo;
import com.wechatserver.util.ConnectHandlerUtils;
import com.wechatserver.util.MsgHandlerUtils;
import com.wechatserver.util.PropertiesLoadUtils;
import com.wechatserver.util.WeChatApiUtils;

@WebServlet(name = "WeChatMainServlet", urlPatterns = "/WeChatMainServlet", loadOnStartup = 1)
public class WeChatMainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		Properties proper = new PropertiesLoadUtils().propertiesLoad("/wechatConfigInfo.properties");
		WechatConfigInfo.appId = proper.getProperty("appId");
		WechatConfigInfo.appSecret = proper.getProperty("appSecret");
		WechatConfigInfo.token = proper.getProperty("token");
		WechatConfigInfo.encodingAESKey = proper.getProperty("encodingAESKey");
		// 获取微信公众号接口权限
		WeChatApiUtils.getWeChatToken(WechatConfigInfo.appId, WechatConfigInfo.appSecret);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.setGeneralAttributes(req, resp);
		WechatConfigInfo.signature = req.getParameter("signature");
		WechatConfigInfo.timestamp = req.getParameter("timestamp");
		WechatConfigInfo.nonce = req.getParameter("nonce");
		if (ConnectHandlerUtils.wechatConnectVaildate(WechatConfigInfo.token)) {
			resp.getWriter().write(req.getParameter("echostr"));
		} else {
			System.out.println("与微信平台连接失败");
		}

		System.out.println("signature : " + WechatConfigInfo.signature);
		System.out.println("timestamp : " + WechatConfigInfo.timestamp);
		System.out.println("nonce : " + WechatConfigInfo.nonce);

		System.out.println("appId : " + WechatConfigInfo.appId);
		System.out.println("appSecret : " + WechatConfigInfo.appSecret);
		System.out.println("token : " + WechatConfigInfo.token);
		System.out.println("encodingAESKey : " + WechatConfigInfo.encodingAESKey);
		String menuData = " {\n" + "     \"button\":[\n" + "     {    \n" + "          \"type\":\"click\",\n"
				+ "          \"name\":\"今日歌曲\",\n" + "          \"key\":\"V1001_TODAY_MUSIC\"\n" + "      },\n"
				+ "      {\n" + "           \"name\":\"菜单\",\n" + "           \"sub_button\":[\n" + "           {    \n"
				+ "               \"type\":\"view\",\n" + "               \"name\":\"搜索\",\n"
				+ "               \"url\":\"http://www.soso.com/\"\n" + "            },\n" + "            {\n"
				+ "                 \"type\":\"miniprogram\",\n" + "                 \"name\":\"wxa\",\n"
				+ "                 \"url\":\"http://mp.weixin.qq.com\",\n"
				+ "                 \"appid\":\"wx286b93c14bbf93aa\",\n"
				+ "                 \"pagepath\":\"pages/lunar/index\"\n" + "             },\n" + "            {\n"
				+ "               \"type\":\"click\",\n" + "               \"name\":\"赞一下我们\",\n"
				+ "               \"key\":\"V1001_GOOD\"\n" + "            }]\n" + "       }]\n" + " }";
		WeChatApiUtils.createCustomMenu(menuData);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.setGeneralAttributes(req, resp);
		String result = "";
		MsgHandlerUtils mhu = new MsgHandlerUtils();
		Map<String, Object> map = mhu.msgParse(req.getInputStream());
		AcceptMsgType msgType = AcceptMsgType.valueOf(map.get("MsgType").toString().toUpperCase());
		switch (msgType) {
		case TEXT:
			map.put("Content", "你说：" + map.get("Content"));
			result = mhu.buildReponseMsg(map, SendMsgType.TEXT);
			break;
		case IMAGE:
			result = mhu.buildReponseMsg(map, SendMsgType.IMAGE);
			break;
		case VOICE:
			result = mhu.buildReponseMsg(map, SendMsgType.VOICE);
			break;
		case VIDEO:
		case SHORTVIDEO:
			result = mhu.buildReponseMsg(map, SendMsgType.VIDEO);
			break;
		case LOCATION:
			map.put("Content", "Location_X：" + map.get("Location_X") + "\n" + "Location_Y：" + map.get("Location_Y"));
			result = mhu.buildReponseMsg(map, SendMsgType.TEXT);
			break;
		case LINK:
			map.put("Content", "Link_URL：" + map.get("Url"));
			result = mhu.buildReponseMsg(map, SendMsgType.TEXT);
			break;
		case EVENT:
			map.put("Content", "Event_Type：" + map.get("Event"));
			result = mhu.buildReponseMsg(map, SendMsgType.TEXT);
			break;
		default:
			map.put("Content", "我不是很懂你想做什么。。");
			result = mhu.buildReponseMsg(map, SendMsgType.TEXT);
			break;
		}
		if ("Encrypt".equals(mhu.cryptFlg)) {
			result = mhu.msgEncrypt(result);
		}
		resp.getWriter().println(result);

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
