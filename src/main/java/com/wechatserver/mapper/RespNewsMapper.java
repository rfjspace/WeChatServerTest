package com.wechatserver.mapper;

import com.wechatserver.entry.message.response.NewsMessage;

public interface RespNewsMapper {
	public int insert(NewsMessage nm);

	public NewsMessage selectLastNewsMessage();
}
