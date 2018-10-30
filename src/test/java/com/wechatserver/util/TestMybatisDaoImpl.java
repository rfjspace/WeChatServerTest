package com.wechatserver.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wechatserver.dao.MybatisDaoImpl;
import com.wechatserver.entry.message.response.Article;
import com.wechatserver.entry.message.response.NewsMessage;
import com.wechatserver.mapper.RespNewsMessageMapper;

public class TestMybatisDaoImpl {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		SqlSession sqlSession = MybatisUtil.getSession();
		RespNewsMessageMapper rnm = sqlSession.getMapper(RespNewsMessageMapper.class);
		NewsMessage newsMsg = new NewsMessage();
		newsMsg.setFromUserName("fromUserName");
		newsMsg.setToUserName("toUserName");
		newsMsg.setCreateTime(123456789);
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
		newsMsg.setArticles(artList);
		rnm.insert(newsMsg);
	}

}
