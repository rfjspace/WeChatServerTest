package com.wechatserver.mapper;

import com.wechatserver.entry.info.AccountInfo;

public interface AccountInfoMapper {
	public int insert(AccountInfo aInfo);

	public AccountInfo selectByUsername(String username);

	public AccountInfo selectByOpenId(String openId);

	public int updateOpenId(AccountInfo aInfo);

}
