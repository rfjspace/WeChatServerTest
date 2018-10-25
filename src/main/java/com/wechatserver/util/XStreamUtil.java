package com.wechatserver.util;

import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wechatserver.entry.message.response.Article;
import com.wechatserver.entry.message.response.NewsMessage;

/**
 * ClassName: XStreamUtil
 * 
 * @Description: 实体类与xml相互转换工具
 */
public class XStreamUtil {
	/***
	 * 实体类转换为xml
	 * 
	 * @param entry
	 *            实体类
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String toXML(Object entry) {
		// 创建XStream对象，
		XStream xStream = new XStream(new XppDriver() {
			@Override // 覆写XppDriver类的createWriter方法
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					boolean cdata = true;

					@Override // 覆写PrettyPrintWriter类的writeText方法，将写入文本cdata格式化
					protected void writeText(QuickWriter writer, String text) {
						if (cdata) {
							// CDATA忽略检查，任何字符都以文本形式表现
							writer.write("<![CDATA[");
							writer.write(text);
							writer.write("]]>");
						} else {
							writer.write(text);
						}
					}
				};
			}
		});
		// 将类名节点映射为“<xml>”
		Class entryClass = entry.getClass();
		xStream.alias("xml", entry.getClass());
		// NewsMessage数组tag的映射
		if (entryClass.getSimpleName().equals(NewsMessage.class.getSimpleName())) {
			xStream.alias("item", Article.class);
		}
		return xStream.toXML(entry).replaceAll("[\\s]", "");
	}

	/***
	 * xml转Object
	 * 
	 * @param xmlStr
	 *            xml字符串
	 * @param classType
	 *            Object类型
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Object fromXML(String xmlStr, Class classType) {
		XStream xStream = new XStream();
		// 将类名节点映射为“<xml>”
		xStream.alias("xml", classType);
		// NewsMessage数组tag的映射
		if (classType.getSimpleName().equals(NewsMessage.class.getSimpleName())) {
			xStream.alias("item", Article.class);
		}
		return xStream.fromXML(xmlStr);
	}
}
