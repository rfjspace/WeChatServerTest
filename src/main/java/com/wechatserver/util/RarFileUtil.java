package com.wechatserver.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

public class RarFileUtil {
	public static void unRar(String srcRarPath, String dstDirectoryPath) throws Exception {
		if (!srcRarPath.toLowerCase().endsWith(".rar")) {
			throw new Exception("Rar文件不存在！");
		}
		File dst = new File(dstDirectoryPath);
		if (!dst.exists()) {
			dst.mkdirs();
		}
		Archive arc = new Archive(new File(srcRarPath));
		File out = null;
		if (arc != null) {
			arc.getMainHeader().print();
			for (FileHeader fh : arc.getFileHeaders()) {
				if (RarFileUtil.checkCN(fh.getFileNameW())) {
					out = new File(dstDirectoryPath + File.separator + fh.getFileNameW());
				} else {
					out = new File(dstDirectoryPath + File.separator + fh.getFileNameString());
				}
				System.out.println(out);
				if (fh.isDirectory()) {
					out.mkdirs();
				} else {
					if (!out.exists()) {
						if (!out.getParentFile().exists()) {
							out.getParentFile().mkdirs();
						}
						out.createNewFile();
						FileOutputStream fos = new FileOutputStream(out);
						arc.extractFile(fh, fos);
						fos.close();
					}
				}
			}
			arc.close();
		}
	}

	private static boolean checkCN(String str) {
		String regEx = "[\\\\u4e00-\\\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			return true;
		}
		return false;
	}

}
