package com.wechatserver.util;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wechatserver.global.GlobalVariables;

public class TestUpAndDown {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testUp() {
		GlobalVariables.accessToken = WeChatApiUtil.getToken("wx29bdb41bfe33b029", "338b417ce51eaaf3dcf08f7f979c3397");
		File file04 = new ResourceLoadUtil().fileLoad("/voices/voice01.mp3");
		String mediaId04 = WeChatApiUtil.getUploadMediaId(file04, "voice");
		System.out.println(mediaId04);
	}

}
