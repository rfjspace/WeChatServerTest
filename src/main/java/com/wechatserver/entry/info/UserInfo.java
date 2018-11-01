package com.wechatserver.entry.info;

public class UserInfo {
	// 用户的唯一标识
	private String openid;
	// 用户昵称
	private String nickname;
	// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String sex;
	// 用户个人资料填写的省份
	private String province;
	// 普通用户个人资料填写的城市
	private String city;
	// 国家，如中国为CN
	private String country;
	// 用户头像
	private String headimgurl;
	// 用户特权信息
	private String privilege;
	// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	private String unionid;
	//语言
	private String language;

	public String getOpenId() {
		return openid;
	}

	public void setOpenId(String openId) {
		this.openid = openId;
	}

	public String getNickName() {
		return nickname;
	}

	public void setNickName(String nickName) {
		this.nickname = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadImgurl() {
		return headimgurl;
	}

	public void setHeadImgurl(String headImgurl) {
		this.headimgurl = headImgurl;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getUnionId() {
		return unionid;
	}

	public void setUnionId(String unionId) {
		this.unionid = unionId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
