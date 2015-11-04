package com.saintgobain.ramses.selenium.general.screens;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;

import com.saintgobain.ramses.selenium.helper.ResourceHelper;
import com.saintgobain.ramses.selenium.util.CommonUtils;
import com.saintgobain.ramses.selenium.util.WebDriverUtil;

public class RamsesLogin {

	private static final Logger logger = Logger.getLogger(RamsesLogin.class);

	public static void main(String[] args) {
		WebDriver objBrowser = CommonUtils.initDriver(BrowserType.IE);
		try {
			logger.info(objBrowser.getTitle());
			WebElement login = objBrowser.findElement(By.cssSelector("input[name='loginActionFormBean.login']"));
			login.sendKeys("system");

			WebElement password = objBrowser.findElement(By.cssSelector("input[name='loginActionFormBean.password']"));
			password.sendKeys("magerna");

			WebElement validate = objBrowser.findElement(By.cssSelector("input#Btn1"));
			validate.click();
			CommonUtils.captureScreenShotAndSave(RamsesLogin.class.getSimpleName());
			objBrowser.quit();
			logger.info("Quit Browser...");
			logger.info("Close Driver...");
			CommonUtils.captureLogs(RamsesLogin.class.getSimpleName());
			WebDriverUtil.closeIEDriver(ResourceHelper.getConfigBundleValue("close_ie_driver_path"));
		} catch (Exception ex) {
			CommonUtils.captureErrorScreenShot(RamsesLogin.class.getSimpleName());
			logger.error(ex.getMessage());
		}
	}
}
