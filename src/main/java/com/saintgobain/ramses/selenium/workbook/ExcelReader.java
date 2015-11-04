package com.saintgobain.ramses.selenium.workbook;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelReader {

	public static Sheet wrksheet;

	public static Workbook wrkbook = null;

	public static Hashtable<String, Integer> dict = new Hashtable<String, Integer>();

	public ExcelReader(String ExcelSheetPath, String ExcelSheet) throws IOException, BiffException {

		try {
			wrkbook = Workbook.getWorkbook(new File(ExcelSheetPath));
			wrksheet = wrkbook.getSheet(ExcelSheet);

		} catch (IOException e) {
			throw new IOException();
		}
	}

	public static int rowCount() {
		return wrksheet.getRows();
	}

	public static String readCell(int column, int row) {
		return wrksheet.getCell(column, row).getContents();
	}

	public static void columnDictionary() {
		for (int col = 0; col < wrksheet.getColumns(); col++) {
			dict.put(readCell(col, 0), col);
		}
	}

	public static int getCell(String colName) {
		try {
			int value;
			value = ((Integer) dict.get(colName)).intValue();
			return value;
		} catch (NullPointerException e) {
			return (0);
		}
	}

}
