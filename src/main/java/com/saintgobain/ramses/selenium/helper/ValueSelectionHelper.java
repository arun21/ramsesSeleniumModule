package com.saintgobain.ramses.selenium.helper;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ValueSelectionHelper {

	public static void selectRandomIndexFromDropDown(WebElement dropdown) {
		Select objSel = new Select(dropdown);
		List<WebElement> weblist = objSel.getOptions();
		int iCnt = weblist.size();
		Random num = new Random();
		int iSelect = num.nextInt(iCnt);
		objSel.selectByIndex(iSelect);
	}

	public static void setValueForDropDown(WebElement dropdown, String Id) {
		Select objSel = new Select(dropdown);
		objSel.selectByValue(Id);
	}
}
