package com.saintgobain.ramses.selenium.workbook;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class WriteExcelFile {

	static Workbook wbook;
	static WritableWorkbook wwbCopy;
	static WritableSheet shSheet;

	public void setValueIntoCell(String strSheetName, int iColumnNumber, int iRowNumber, String strData) throws WriteException {
		WritableSheet wshTemp = wwbCopy.getSheet(strSheetName);
		Label labTemp = new Label(iColumnNumber, iRowNumber, strData);

		try {
			wshTemp.addCell(labTemp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeFile() {
		try {
			wwbCopy.write();
			wwbCopy.close();
			wbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
