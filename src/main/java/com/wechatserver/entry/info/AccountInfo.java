package com.wechatserver.entry.info;

public class AccountInfo {
	// 网站帐号
	private String username;
	// 网站密码
	private String password;
	// 创建时间
	private String createTime;
	// 绑定的微信号
	private String openId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
