package com.wechatserver.servlet;

import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.wechatserver.info.GlobalVariables;
import com.wechatserver.util.PropertyLoadUtil;
import com.wechatserver.util.WeChatApiUtil;

/**
 * ClassName: InitializedServlet
 * 
 * @Description: 服务器初期化，accessToken的获取
 */
@WebServlet(name = "InitializedServlet", urlPatterns = "/InitializedServlet", loadOnStartup = 1)
public class InitializedServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		String location = "/GlobalVariables.properties";
		Properties prop = new PropertyLoadUtil().load(location);
		GlobalVariables.init(prop);
		WeChatApiUtil.getWeChatToken(GlobalVariables.appId, GlobalVariables.appSecret);
	}
}
