package com.wechatserver.util;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class Test {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@org.junit.Test
	public void test() {
		int[] i = { 1, 2, 3, 4, 5, 6, 7, 8 };
		int w = 0;
		StringBuilder sb = new StringBuilder();

		for (int j = 1; j <= 9999; j++) {
			for (int k : i) {
				w = j % k;
				sb.append(w);
			}
		}
		char[] c = sb.toString().toCharArray();
		Arrays.sort(c);
		System.out.println(c);
		String z = "" + c[0];
		for (int p = 0; p < c.length - 1; p++) {
			if (c[p] != c[p + 1]) {
				z += c[p + 1];
			}
		}
		System.out.println(z);
	}

}
