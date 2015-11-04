package com.saintgobain.ramses.selenium.general.screens;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.Select;

import com.saintgobain.ramses.selenium.constants.SeleniumConstants;
import com.saintgobain.ramses.selenium.helper.ResourceHelper;
import com.saintgobain.ramses.selenium.util.CommonUtils;

public class RamsesSiteSelect {

	private static final Logger logger = Logger.getLogger(RamsesSiteSelect.class);

	public static void main(String[] args) {

		System.setProperty(SeleniumConstants.WEB_IEDRIVER, SeleniumConstants.IE_DRIVER_PATH);
		WebDriver objBrowser = CommonUtils.initDriver(BrowserType.IE);
		try {
			WebElement login = objBrowser.findElement(By.cssSelector("input[name='loginActionFormBean.login']"));
			login.sendKeys("system");

			WebElement password = objBrowser.findElement(By.cssSelector("input[name='loginActionFormBean.password']"));
			password.sendKeys("magerna");
			WebElement validateLogin = objBrowser.findElement(By.cssSelector("input#Btn1"));

			validateLogin.click();

			WebElement dropdown = objBrowser.findElement(By.cssSelector("select[name='loginActionFormBean.idSelectedSite']"));
			Select siteDropDown = new Select(dropdown);
			siteDropDown.selectByValue("651");

			WebElement validateSelect = objBrowser.findElement(By.cssSelector("input#Btn1"));
			validateSelect.click();
			objBrowser.quit();
			CommonUtils.captureAllLogs(logger, "Quit Browser");
			CommonUtils.captureAllLogs(logger, "Close Driver...");
			CommonUtils.captureLogsAndcloseDriver(RamsesSiteSelect.class.getSimpleName(), ResourceHelper.getConfigBundleValue("close_ie_driver_path"));
		} catch (Exception ex) {
			CommonUtils.captureErrorScreenShot(RamsesSiteSelect.class.getSimpleName());
			logger.error(ex.getMessage());
		}

	}
}
