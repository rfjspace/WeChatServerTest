package com.wechatserver.mapper;

import java.util.List;

import com.wechatserver.entry.message.response.NewsMessage;

public interface RespNewsMessageMapper {

	public void insert(NewsMessage newsMessage);

	public void delete(String toUserName, String fromUserName, Long createTime);

	public void update(NewsMessage newsMessage);

	public NewsMessage selectOne(String toUserName, String fromUserName, Long createTime);

	public List<NewsMessage> selectAll();

}
