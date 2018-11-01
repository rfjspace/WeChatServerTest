package com.wechatserver.mapper;

import com.wechatserver.entry.info.UserInfo;

public interface UserInfoMapper {
	public int insert(UserInfo uInfo);

	public UserInfo selectByOpenId(String openId);

	public int updateByOpenId(UserInfo uInfo);

}
