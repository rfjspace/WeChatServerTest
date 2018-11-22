package com.wechatserver.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPOI {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testRead() throws Exception {
		final String SPACE = " ";
		final String LOCATION = "/home/user/Downloads/20181122113609.xlsx";
		File file = new File(LOCATION);
		List<TableExcel> list = POIExcelUtil.read(file, TableExcel.class);
		StringBuilder sb = new StringBuilder();
		for (TableExcel te : list) {
			sb.append(te.getOwners() + SPACE);
		}
		String str = sb.toString();
		String[] strArray = str.split(SPACE);
		List<String> aslist = Arrays.asList(strArray);
		Set<String> set = new HashSet<String>(aslist);
		String[] strArray1 = (String[]) set.toArray(new String[0]);
		for (String s : strArray1) {
			int count = 0;
			for (TableExcel te : list) {
				String[] owner = te.getOwners().split(SPACE);
				for (String o : owner) {
					if (s.equals(o)) {
						count++;
					}
				}
			}
			System.out.println(s + ":" + count);
		}
	}

	@Test
	public void testExport() throws Exception {
		final String LOCATION = "/home/user/Downloads/%s.xlsx";
		SimpleDateFormat format = new SimpleDateFormat("YYYYMMddHHmmss");
		String fileName = format.format(new Date());
		Map<String, List<TableExcel>> map = new HashMap<>();
		List<TableExcel> list = new ArrayList<>();

		for (int i = 0; i <= 50; i++) {
			TableExcel te = new TableExcel();
			te.setPatentID("P1234567890" + i);
			te.setOwners("ab abc de ff" + i);
			te.setContent("Content" + i);
			list.add(te);
		}
		map.put("fileName", list);
		POIExcelUtil.export(String.format(LOCATION, fileName), map);
	}
}
