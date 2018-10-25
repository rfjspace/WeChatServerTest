package com.wechatserver.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * ClassName: PropertyLoadUtil
 * 
 * @Description: 读取Properties工具类
 */
public class ResourceLoadUtil {
	/***
	 * Properties文件读取
	 * 
	 * @param location
	 *            文件所在位置（ClassPath）
	 * @return Properties类
	 */
	public Properties propertyLoad(String location) {
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

	public File fileLoad(String location) {
		URL url = getClass().getResource(location);
		return new File(url.getFile());
	}
}
