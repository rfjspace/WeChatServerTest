package com.wechatserver.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.wechatserver.entry.info.UserInfo;
import com.wechatserver.mapper.UserInfoMapper;
import com.wechatserver.util.MybatisUtil;
import com.wechatserver.util.WeChatApiUtil;

/**
 * ClassName: AuthForwardServlet
 * 
 * @Description: 网页授权，页面跳转
 */
@WebServlet(name = "AuthForwardServlet", urlPatterns = "/AuthForwardServlet")
public class AuthForwardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 字符编码转换
		this.setGeneralAttributes(req, resp);
		// 获取网页授权acess_token
		SqlSession session = MybatisUtil.getSession();
		try {
			String forwardUrl = "login.jsp";
			String code = req.getParameter("code");
			// 用户自定义状态值
			// String state = req.getParameter("state");
			UserInfoMapper uim = session.getMapper(UserInfoMapper.class);
			JSONObject authToken = WeChatApiUtil.getAuthToken(code);
			// 获取用户微信信息
			String aToken = authToken.getString("access_token");
			String openId = authToken.getString("openid");
			JSONObject authInfo = WeChatApiUtil.getAuthInfo(aToken, openId);
			// JSON转<T>泛型
			UserInfo uInfo = JSONObject.parseObject(authInfo.toJSONString(), UserInfo.class);
			// 查询DB是否存在
			UserInfo userInfo = uim.selectByOpenId(uInfo.getOpenId());
			// 判定用户是否是网站会员
			if (null == userInfo) {
				uim.insert(uInfo);
				// 下述结构就会在第二次访问时出现重复的授权请求,导致请求code失效(???)
				// req.getRequestDispatcher("register.jsp").forward(req, resp);
				forwardUrl = "register.jsp";
			}
			session.commit();
			// 根据结构跳转相应网页
			// req.getRequestDispatcher("register.jsp").forward(req, resp);
			// req.getRequestDispatcher("login.jsp").forward(req, resp);
			req.setAttribute("authInfo", authInfo);
			req.getRequestDispatcher(forwardUrl).forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			req.setAttribute("error", e);
			req.getRequestDispatcher("error.jsp").forward(req, resp);
		}
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
