package com.wechatserver.util;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wechatserver.entry.menu.ClickButton;
import com.wechatserver.entry.menu.ViewButton;
import com.wechatserver.mapper.MenuCreateMapper;

/**
 * ClassName: MenuCreateUtil
 * 
 * @Description: 创建生成菜单工具(DB)
 */
public class MenuCreateUtil {

	private static SqlSession sqlSession = MybatisUtil.getSession();
	private static MenuCreateMapper mcMapper = sqlSession.getMapper(MenuCreateMapper.class);

	/***
	 * 微信菜单创建
	 * 
	 * @return
	 */
	public static JSONObject createMenuByDB() {
		List<Map<String, Object>> menuList = mcMapper.selectMainMenu();
		JSONObject menu = new JSONObject();
		JSONArray mainButton = new JSONArray();
		for (Map<String, Object> m : menuList) {
			int level = (int) m.get("menu_level");
			if (level == 1) {
				String mainType = m.get("button_type").toString();
				switch (mainType) {
				case "sub":
					String helpMenu = m.get("button_name").toString();
					JSONObject subMenu = new JSONObject();
					subMenu.put("name", helpMenu);
					JSONArray subButton = new JSONArray();
					List<Map<String, Object>> childList = mcMapper.selectBySuperName(helpMenu);
					for (Map<String, Object> c : childList) {
						String subType = c.get("button_type").toString();
						switch (subType) {
						case "click":
							ClickButton cb = new ClickButton();
							cb.setType(c.get("button_type").toString());
							cb.setName(c.get("button_name").toString());
							cb.setKey(c.get("click_key").toString());
							subButton.add(cb);
							break;
						case "view":
							ViewButton vb = new ViewButton();
							vb.setType(c.get("button_type").toString());
							vb.setName(c.get("button_name").toString());
							vb.setUrl(c.get("view_url").toString());
							subButton.add(vb);
							break;
						default:
							break;
						}
					}
					subMenu.put("sub_button", subButton);
					mainButton.add(subMenu);
					break;
				case "click":
					ClickButton cb = new ClickButton();
					cb.setType(m.get("button_type").toString());
					cb.setName(m.get("button_name").toString());
					cb.setKey(m.get("click_key").toString());
					mainButton.add(cb);
					break;
				case "view":
					ViewButton vb = new ViewButton();
					vb.setType(m.get("button_type").toString());
					vb.setName(m.get("button_name").toString());
					vb.setUrl(m.get("view_url").toString());
					mainButton.add(vb);
					break;
				default:
					break;
				}
			}
		}
		menu.put("button", mainButton);
		return menu;
	}

	/***
	 * 测试用ViewUrl的变更
	 * 
	 * @param buttonName
	 * @param url
	 */
	public static void updateViewUrl(String buttonName, String url) {
		String urlEncode = WeChatApiUtil.getUrlEncode(url);
		mcMapper.updateViewUrl(buttonName, urlEncode);
		sqlSession.commit();
	}
}
