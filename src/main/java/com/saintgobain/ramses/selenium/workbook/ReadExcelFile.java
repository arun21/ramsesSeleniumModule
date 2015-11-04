package com.saintgobain.ramses.selenium.workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile {

	private static final Logger logger = Logger.getLogger(ReadExcelFile.class);

	public static void printExcelData(String Path, String sheet) throws BiffException, IOException {
		FileInputStream fs = new FileInputStream(Path);
		Workbook wb;
		try {
			wb = (Workbook) WorkbookFactory.create(fs);
			Sheet sh = wb.getSheet(sheet);
			int totalNoOfRows = sh.getRows();
			int totalNoOfCols = sh.getColumns();

			for (int row = 0; row < totalNoOfRows; row++) {
				for (int col = 0; col < totalNoOfCols; col++) {
					System.out.print(sh.getCell(col, row).getContents() + "\t");
				}
				System.out.println();
			}
		} catch (Exception ex) {
			logger.error("printExcelData " + ex.getMessage());
		}
	}

	public static XSSFSheet getSheet(String folderPath) {
		FileInputStream file = null;
		XSSFSheet sheet = null;
		try {
			file = new FileInputStream(new File(folderPath));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheetAt(0);
			file.close();
		} catch (Exception ex) {
			logger.error("getSheet " + ex.getMessage());
		}
		return sheet;
	}

	public static XSSFWorkbook getWorkbook(String folderPath) {
		FileInputStream file = null;
		XSSFWorkbook workbook = null;
		try {
			file = new FileInputStream(new File(folderPath));
			workbook = new XSSFWorkbook(file);
			file.close();
		} catch (Exception ex) {
			logger.error("getWorkbook " + ex.getMessage());
		}
		return workbook;
	}

	public static Workbook getWorkBookType(String filePath) {
		Workbook workbook = null;
		File file = new File(filePath);
		String extn = FilenameUtils.getExtension(filePath);
		if (extn.equalsIgnoreCase("xls") || extn.equalsIgnoreCase("xlsx")) {
			try {
				workbook = (Workbook) WorkbookFactory.create(new FileInputStream(file));
			} catch (Exception ex) {
				logger.error("getWorkBookType " + ex.getMessage());
			}
		}
		return workbook;
	}
}
