package com.wechatserver.util;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestRarFile {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() throws Exception {
		String srcRarPath = "/home/user/Downloads/sfdlzwdsbpdf_20180702.rar";
		String dstDirectoryPath = "/home/user/Downloads/sfdlzwdsbpdf_20180702";
		RarFileUtil.unRar(srcRarPath, dstDirectoryPath);
	}

}
