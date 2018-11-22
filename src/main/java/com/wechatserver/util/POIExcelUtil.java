package com.wechatserver.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/***
 * 
 * @Description Excel文件处理读取，并转换为任意实体对象
 *
 */
public class POIExcelUtil {
	/***
	 * Excel文件读取，并转换为任意实体对象
	 * 
	 * @param file
	 *            要读取的文件
	 * @param clazz
	 *            实体类型（反射创建实体对象）
	 * @return 泛型集合
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> read(File file, Class<T> clazz) throws Exception {
		InputStream is = new FileInputStream(file);
		// apche的POI Workbook，excel文件兼容性
		Workbook excel = WorkbookFactory.create(is);
		Object obj = null;
		Method method = null;
		List<T> list = new ArrayList<T>();
		Iterator<Sheet> it = excel.sheetIterator();
		while (it.hasNext()) {
			Sheet sheet = it.next();
			StringBuffer th = new StringBuffer();
			for (Row row : sheet) {
				obj = clazz.newInstance();
				for (Cell cell : row) {
					String value = parseCellValue(cell);
					if (cell.getRowIndex() == 0) {
						th.append("set");
						th.append(toFristUpperCase(value));
						th.append(",");
					} else {
						String[] mName = String.valueOf(th).split(",");
						method = clazz.getDeclaredMethod(mName[cell.getColumnIndex()], String.class);
						method.invoke(obj, value);
					}
				}
				list.add((T) obj);
			}
		}
		list.remove(0);
		return list;
	}

	@SuppressWarnings("resource")
	public static <T> void export(String el, Map<String, List<T>> map) throws Exception {
		Workbook excel = new HSSFWorkbook();
		// excel样式的设置 TODO
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Sheet sheet = excel.createSheet(key);
			for (int rn = 0; rn < map.get(key).size(); rn++) {
				T t = map.get(key).get(rn);
				Method[] method = t.getClass().getDeclaredMethods();
				int cn = 0;
				Row rowH = null;
				if (rn == 0) {
					rowH = sheet.createRow(rn);
				}
				Row rowC = sheet.createRow(rn + 1);
				for (Method m : method) {
					String type = m.getReturnType().getName();
					if (!"void".equals(type)) {
						if (null != rowH) {
							String cName = m.getName().replaceFirst("get", "");
							Cell cell = rowH.createCell(cn);
							cell.setCellValue(toFristUpperCase(cName));
						}
						Cell cell = rowC.createCell(cn);
						String value = (String) m.invoke(t, null);
						cell.setCellValue(value);
						cn++;
					}
				}
			}
		}
		if (null != excel) {
			FileOutputStream fs = new FileOutputStream(el);
			excel.write(fs);
			fs.close();
		}
	}

	private static String toFristUpperCase(String str) {
		// 数组地址的引用，toString为地址
		char[] c = str.toCharArray();
		if (c[0] >= 'a' && c[0] <= 'z') {
			c[0] = (char) ((int) c[0] - 32);
		}
		return String.valueOf(c);
	}

	private static String parseCellValue(Cell cell) {
		String retCellValue = "";
		switch (cell.getCellType()) {
		case STRING:
			retCellValue = cell.getStringCellValue();
			break;
		case _NONE:
			retCellValue = "NONE";
			break;
		case BLANK:
			retCellValue = "";
			break;
		case ERROR:
			retCellValue = "ERROR";
			break;
		case FORMULA:
			retCellValue = "FORMULA";
			break;
		case NUMERIC:
			boolean isDate = DateUtil.isCellDateFormatted(cell);
			if (isDate) {
				SimpleDateFormat format = new SimpleDateFormat("HH:mm");
				Date date = cell.getDateCellValue();
				retCellValue = format.format(date);
			} else {
				retCellValue = String.valueOf((int) cell.getNumericCellValue());
			}
			break;
		case BOOLEAN:
			if (cell.getBooleanCellValue()) {
				retCellValue = "TRUE";
			} else {
				retCellValue = "FALSE";
			}
			break;
		default:
			break;
		}
		return retCellValue;
	}
}
