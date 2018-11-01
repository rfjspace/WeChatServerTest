package com.wechatserver.mapper;

import java.util.List;
import java.util.Map;

public interface RespArticleMapper {
	public static final String INCREMENT_ID = "increment_id";
	public static final String ARTICLE_ID = "article_id";
	public static final String ARTICLE_TITLE = "article_title";
	public static final String ARTICLE_PICURL = "article_picurl";
	public static final String ARTICLE_URL = "article_url";
	public static final String ARTICLE_DESCRIPTION = "article_description";
	public static final String ARTICLE_LIST = "article_list";

	public int insertList(Map<String, Object> map);

	public List<Map<String, Object>> selectByArticleId(String articleId);

	public List<Map<String, Object>> selectAll();

}
