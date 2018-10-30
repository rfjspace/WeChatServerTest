package com.wechatserver.dao;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.wechatserver.util.MybatisUtil;

public class MybatisDaoImpl {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insert(Object entry, Class objClass) {
		SqlSession sqlSession = MybatisUtil.getSession();
		Object mapper = sqlSession.getMapper(objClass);
		Method insert;
		try {
			insert = objClass.getMethod("insert");
			insert.invoke(mapper, entry);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void delete(String toUserName, String fromUserName, Long createTime, Class objClass) {
		SqlSession sqlSession = MybatisUtil.getSession();
		Object mapper = sqlSession.getMapper(objClass);
		Method delete;
		try {
			delete = objClass.getMethod("delete");
			delete.invoke(mapper, toUserName, fromUserName, createTime);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void update(Object entry, Class objClass) {
		SqlSession sqlSession = MybatisUtil.getSession();
		Object mapper = sqlSession.getMapper(objClass);
		Method delete;
		try {
			delete = objClass.getMethod("update");
			delete.invoke(mapper, entry);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object selectOne(String toUserName, String fromUserName, Long createTime, Class objClass) {
		SqlSession sqlSession = MybatisUtil.getSession();
		Object mapper = sqlSession.getMapper(objClass);
		Method delete;
		Object result = null;
		try {
			delete = objClass.getMethod("selectOne");
			result = delete.invoke(mapper, toUserName, fromUserName, createTime);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object> selectAll(Class objClass) {
		SqlSession sqlSession = MybatisUtil.getSession();
		Object mapper = sqlSession.getMapper(objClass);
		Method delete;
		List<Object> result = null;
		try {
			delete = objClass.getMethod("selectAll");
			result = (List<Object>) delete.invoke(mapper);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
		}
		return result;
	}
}
