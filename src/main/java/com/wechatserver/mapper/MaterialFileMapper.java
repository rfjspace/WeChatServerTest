package com.wechatserver.mapper;

import java.util.Map;

public interface MaterialFileMapper {
	public int insertFile(Map<String, String> fileInfo);

	public Map<String, String> selectFileByName(String fileName);

	public int deleteFileByName(String fileName);
}
