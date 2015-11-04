package com.saintgobain.ramses.selenium.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;

import com.saintgobain.ramses.selenium.constants.ActionKeys;
import com.saintgobain.ramses.selenium.general.OperationType;
import com.saintgobain.ramses.selenium.helper.ResourceHelper;
import com.saintgobain.ramses.selenium.helper.UIOperationHelper;
import com.thoughtworks.selenium.SeleneseTestBase;

public abstract class AbstractSeleniumTestCase extends SeleneseTestBase {

	private static final Logger logger = Logger.getLogger(AbstractSeleniumTestCase.class);
	private static WebDriver driver = null;

	public static String getBundle(String path) {
		return ResourceHelper.getConfigBundleValue(path);
	}

	public static WebDriver openBrowserIE() {
		try {
			driver = CommonUtils.initDriver(BrowserType.IE);
		} catch (Exception ex) {
			logger.error("[AbstractSeleniumTestCase] - openBrowserIE " + ex);
		}
		return driver;
	}

	public static WebDriver openBrowserFireFox() {
		try {
			driver = CommonUtils.initDriver(BrowserType.FIREFOX);
		} catch (Exception ex) {
			logger.error("[AbstractSeleniumTestCase] - openBrowserFireFox " + ex);
		}
		return driver;
	}

	public static WebDriver openBrowserWithURL(String browser, String url) {
		try {
			driver = CommonUtils.initDriverWithURL(browser, url);
		} catch (Exception ex) {
			logger.error("[AbstractSeleniumTestCase] - openBrowserWithURL " + ex);
		}
		return driver;
	}

	public static void stopDriver() {
		try {
			CommonUtils.stopDriver();
			WebDriverUtil.closeIEDriver(getBundle("close_ie_driver_path"));
		} catch (Exception ex) {
			logger.error("[AbstractSeleniumTestCase] - stopDriver " + ex);
		}
	}

	public static void stopDriverWithURL(String URL) {
		try {
			CommonUtils.stopDriverWithURL(URL);
			WebDriverUtil.closeIEDriver(getBundle("close_ie_driver_path"));
		} catch (Exception ex) {
			logger.error("[AbstractSeleniumTestCase] - stopDriverWithURL " + ex);
		}
	}

	public static void performRamsesLogin() {
		try {
			WebElement login = UIOperationHelper.performLogin(OperationType.LOGIN);
			login.sendKeys(ActionKeys.UserName);
			setLog("Username 'system' entered");
			WebElement password = UIOperationHelper.performLogin(OperationType.PASSWORD);
			password.sendKeys(ActionKeys.Password);
			setLog("Password 'magerna' entered");
			WebElement validate = UIOperationHelper.performLogin(OperationType.CLICK);
			validate.click();
			setLog("Validate button clicked");
		} catch (Exception ex) {
			logger.error("[AbstractSeleniumTestCase] - performRamsesLogin ", ex);
		}
	}

	public static void setLog(String value) {
		try {
			CommonUtils.getReport(value);
		} catch (Exception ex) {
			logger.error("[AbstractSeleniumTestCase] - setLog ", ex);
		}
	}

	public static void captureDetails(String className) {
		try {
			CommonUtils.captureLogs(className);
		} catch (Exception ex) {
			logger.error("[AbstractSeleniumTestCase] - captureDetails ", ex);
		}
	}

}
