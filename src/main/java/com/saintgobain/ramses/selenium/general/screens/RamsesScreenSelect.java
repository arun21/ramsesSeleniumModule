package com.saintgobain.ramses.selenium.general.screens;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.Select;

import com.saintgobain.ramses.selenium.constants.URLConstants;
import com.saintgobain.ramses.selenium.helper.ResourceHelper;
import com.saintgobain.ramses.selenium.util.CommonUtils;

public class RamsesScreenSelect {

	private static final Logger logger = Logger.getLogger(RamsesScreenSelect.class);

	public static void main(String[] args) {
		WebDriver objBrowser = CommonUtils.initDriver(BrowserType.IE);
		try {
			CommonUtils.captureAllLogs(logger, objBrowser.getTitle());
			WebElement login = objBrowser.findElement(By.cssSelector("input[name='loginActionFormBean.login']"));
			login.sendKeys("system");
			CommonUtils.captureAllLogs(logger, "Username Entered");

			WebElement password = objBrowser.findElement(By.cssSelector("input[name='loginActionFormBean.password']"));
			password.sendKeys("magerna");
			CommonUtils.captureAllLogs(logger, "Password Entered");

			WebElement validateLogin = objBrowser.findElement(By.cssSelector("input#Btn1"));
			validateLogin.click();
			CommonUtils.captureAllLogs(logger, "Validate Button Clicked");

			WebElement dropdown = objBrowser.findElement(By.cssSelector("select[name='loginActionFormBean.idSelectedSite']"));
			Select siteDropDown = new Select(dropdown);
			siteDropDown.selectByValue("651"); // climaver
			CommonUtils.captureAllLogs(logger, "site selected from dropdown");

			WebElement validateSelect = objBrowser.findElement(By.cssSelector("input#Btn1"));
			validateSelect.click();
			CommonUtils.captureAllLogs(logger, "Validate Button Clicked - site selected");

			objBrowser.navigate().to(URLConstants.RAMSES_TEST_FR_URL + "populateUser.action");
			CommonUtils.captureAllLogs(logger, "Navigate to User Screen: " + URLConstants.RAMSES_TEST_FR_URL + "populateUser.action");
			objBrowser.quit();
			CommonUtils.captureAllLogs(logger, "Quit Browser");
			CommonUtils.captureAllLogs(logger, "Close Driver...");
			CommonUtils.captureLogsAndcloseDriver(RamsesScreenSelect.class.getSimpleName(), ResourceHelper.getConfigBundleValue("close_ie_driver_path"));
		} catch (Exception ex) {
			CommonUtils.captureErrorScreenShot(RamsesScreenSelect.class.getSimpleName());
			logger.error(ex.getMessage());
		}
	}
}
