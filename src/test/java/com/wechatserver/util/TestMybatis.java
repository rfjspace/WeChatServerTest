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
import com.wechatserver.mapper.DatebaseDDLMapper;
import com.wechatserver.mapper.RespArticleMapper;
import com.wechatserver.mapper.RespNewsMapper;

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

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSSss");
		String articleId = sdf.format(new Date());

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
		RespArticleMapper am = ss.getMapper(RespArticleMapper.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(RespArticleMapper.ARTICLE_ID, articleId);
		map.put(RespArticleMapper.ARTICLE_LIST, artList);
		am.insertList(map);
		ss.commit();
		am.selectByArticleId(articleId);
		am.selectAll();

	}

	@Test
	public void testDropTable() {
		SqlSession ss = MybatisUtil.getSession();
		DatebaseDDLMapper ddm = ss.getMapper(DatebaseDDLMapper.class);
		// ddm.createTable();
		// ddm.deleteTableData();
		ss.commit();
	}

	@Test
	public void testMain() {
		SqlSession ss = MybatisUtil.getSession();
		// base消息
		String toUserName = "toUserName";
		String fromUserName = "fromUserName";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmSSss");
		Long createTime = Long.parseLong(format.format(new Date()));
		NewsMessage newsMsg = new NewsMessage();
		newsMsg.setToUserName(toUserName);
		newsMsg.setFromUserName(fromUserName);
		newsMsg.setCreateTime(createTime);
		newsMsg.setMsgType("news");
		List<Article> articles = new ArrayList<Article>();
		Article art1 = new Article();
		art1.setTitle("微信公众平台图片最合适尺寸大小01");
		art1.setPicUrl(
				"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
		art1.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
		articles.add(art1);
		Article art2 = new Article();
		art2.setTitle("微信公众平台图片最合适尺寸大小02");
		art2.setPicUrl(
				"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
		art2.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
		articles.add(art2);
		Article art3 = new Article();
		art3.setTitle("微信公众平台图片最合适尺寸大小03");
		art3.setPicUrl(
				"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
		art3.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
		articles.add(art3);
		newsMsg.setArticles(articles);
		newsMsg.setArticleCount(articles.size());

		RespNewsMapper rnm = ss.getMapper(RespNewsMapper.class);
		rnm.insert(newsMsg);
		RespArticleMapper ram = ss.getMapper(RespArticleMapper.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(RespArticleMapper.ARTICLE_ID, createTime.toString());
		map.put(RespArticleMapper.ARTICLE_LIST, articles);
		ram.insertList(map);
		ss.commit();
		NewsMessage newM = rnm.selectLastNewsMessage();
		for (Article a : newM.getArticles()) {
			System.out.println(a.getTitle());
		}
	}
}
