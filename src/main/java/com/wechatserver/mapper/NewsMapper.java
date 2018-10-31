package com.wechatserver.mapper;

import com.wechatserver.entry.message.response.NewsMessage;

public interface NewsMapper {
	public int insert(NewsMessage nm);

	public NewsMessage select(String to, String from, Long time);
}
