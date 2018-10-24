package com.wechatserver.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * ClassName: PropertyLoadUtil
 * 
 * @Description: 读取Properties工具类
 */
public class PropertyLoadUtil {
	/***
	 * Properties文件读取
	 * 
	 * @param location
	 *            文件所在位置（ClassPath）
	 * @return Properties类
	 */
	public Properties load(String location) {
		InputStream in;
		Properties proper = new Properties();
		try {
			in = getClass().getResourceAsStream(location);
			proper.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proper;
	}

}
