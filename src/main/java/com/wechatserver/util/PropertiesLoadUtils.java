package com.wechatserver.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoadUtils {
	public  Properties propertiesLoad(String location) {
		InputStream in;
		Properties proper = new Properties();
		try {
			in =  getClass().getResourceAsStream(location);
			proper.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proper;
	}

}
