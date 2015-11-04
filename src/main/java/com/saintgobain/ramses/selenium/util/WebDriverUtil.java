package com.saintgobain.ramses.selenium.util;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.saintgobain.ramses.selenium.general.LocaterType;
import com.saintgobain.ramses.selenium.helper.UIOperationHelper;

public class WebDriverUtil {

	private static final Logger logger = Logger.getLogger(WebDriverUtil.class);

	public static WebElement waitWebElement(String object, LocaterType type, long timeOut) {
		WebDriverWait wait = new WebDriverWait(LocalManager.LocalWebDriverManager.getDriver(), timeOut);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(UIOperationHelper.getObject(object, type)));
		return element;
	}

	public static void closeIEDriver(String path) {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process p = runtime.exec(path);
			InputStream is = p.getInputStream();
			int i = 0;
			while ((i = is.read()) != -1) {
				System.out.print((char) i);
			}
		} catch (Exception ex) {
			logger.error("[WebDriverUtil] - closeIEDriver ", ex);
		}
	}

}
