package com.wechatserver.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wechatserver.entry.message.response.Article;
import com.wechatserver.entry.message.response.NewsMessage;
import com.wechatserver.mapper.ArticleMapper;
import com.wechatserver.mapper.NewsMapper;

public class TestMybatis {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@org.junit.Test
	public void test() {
		SqlSession ss = MybatisUtil.getSession();

		NewsMessage newsMsg = new NewsMessage();
		newsMsg.setFromUserName("fromUserName");
		newsMsg.setToUserName("toUserName");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
		Long createTime = Long.parseLong(sdf.format(new Date()));
		newsMsg.setCreateTime(createTime);
		newsMsg.setMsgType("news");
		newsMsg.setArticleCount(2);
		Article art1 = new Article();
		art1.setTitle("Article1");
		art1.setPicUrl("PicUrl1");
		art1.setUrl("www.baidu.com");
		art1.setDescription("Article1");
		Article art2 = new Article();
		art2.setTitle("Article2");
		art2.setPicUrl("PicUrl2");
		art2.setUrl("www.google.com");
		art2.setDescription("Article2");

		List<Article> artList = new ArrayList<Article>();
		artList.add(art1);
		artList.add(art2);

		NewsMapper nm = ss.getMapper(NewsMapper.class);
		nm.insert(newsMsg);
		ArticleMapper am = ss.getMapper(ArticleMapper.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("article_id", createTime);
		map.put("article_list", artList);
		am.insert(map);
		ss.commit();

		NewsMessage nmr = nm.select("toUserName", "fromUserName", createTime);
		System.out.println(nmr.getFromUserName());
		System.out.println(nmr.getToUserName());
		System.out.println(nmr.getCreateTime());
		System.out.println(nmr.getMsgType());
		System.out.println(nmr.getArticleCount());
		for (Article a : nmr.getArticles()) {
			System.out.println(a.getTitle());
			System.out.println(a.getDescription());
			System.out.println(a.getPicUrl());
			System.out.println(a.getUrl());
		}

	}

}
