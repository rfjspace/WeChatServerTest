package com.wechatserver.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * ClassName: MybatisUtil
 * 
 * @Description: 获取Mybatis的sqlSession(事务)工具类
 */
public class MybatisUtil {
	public static SqlSessionFactory sessionFactory;
	static {
		try {
			// 使用MyBatis提供的Resources类加载mybatis的配置文件
			Reader reader = Resources.getResourceAsReader("MyBatis.cfg.xml");
			// 构建sqlSession的工厂
			sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 创建能执行映射文件中sql的sqlSession
	 * 
	 * @return
	 */
	public static SqlSession getSession() {
		return sessionFactory.openSession();
	}
}
