package com.wechatserver.mapper;

import java.util.List;
import java.util.Map;

import com.wechatserver.entry.menu.ClickButton;
import com.wechatserver.entry.menu.ViewButton;

public interface MenuCreateMapper {
	public int insertClickButton(ClickButton cButton, int level, String superName);

	public int insertViewButton(ViewButton vButton, int level, String superName);

	public int insertSubButton(String name);

	public List<Map<String, Object>> selectMainMenu();

	public List<Map<String, Object>> selectBySuperName(String superName);

	public int updateViewUrl(String buttonName, String url);
}
