package com.saintgobain.ramses.selenium.workbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataHelper {

	private static final Logger logger = Logger.getLogger(DataHelper.class);
	public static HashMap<String, String> storeValues = new HashMap<String, String>();

	public static List<HashMap<String, String>> getdata(String path) {

		List<HashMap<String, String>> mydata = new ArrayList<>();
		try {
			FileInputStream fs = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(fs);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Row HeaderRow = sheet.getRow(0);

			for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
				Row currentRow = sheet.getRow(i);
				HashMap<String, String> currentHash = new HashMap<String, String>();
				for (int j = 0; j < currentRow.getPhysicalNumberOfCells(); j++) {
					Cell currentCell = currentRow.getCell(j);
					switch (currentCell.getCellType()) {
					case Cell.CELL_TYPE_BLANK:
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.println(currentCell.getStringCellValue() + "\t");
						currentHash.put(HeaderRow.getCell(j).getStringCellValue(), currentCell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						System.out.println(currentCell.getNumericCellValue() + "\t");
						currentHash.put(HeaderRow.getCell(j).getStringCellValue(), String.valueOf(currentCell.getNumericCellValue()));
						break;
					}
				}
				mydata.add(currentHash);
			}
			fs.close();
		} catch (Exception ex) {
			logger.error("[DataHelper] - getdata " + ex);
		}

		return mydata;

	}
}
