package com.saintgobain.ramses.selenium.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import com.saintgobain.ramses.selenium.constants.SeleniumConstants;

class LocalDriverFactory {

	public static WebDriver createInstance(String browserName) {
		WebDriver driver = null;
		if (browserName.equals(BrowserType.FIREFOX)) {
			System.setProperty(SeleniumConstants.WEB_FIREFOX_DRIVER, SeleniumConstants.FIREFOX_DRIVER_PATH);
			driver = new FirefoxDriver();
			return driver;
		}
		if (browserName.equals(BrowserType.IE)) {
			System.setProperty(SeleniumConstants.WEB_IEDRIVER, SeleniumConstants.IE_DRIVER_PATH);
			driver = new InternetExplorerDriver();
			return driver;
		}
		if (browserName.equals(BrowserType.CHROME)) {
			System.setProperty(SeleniumConstants.WEB_CHROME_DRIVER, SeleniumConstants.CHROME_DRIVER_PATH);
			driver = new ChromeDriver();
			return driver;
		}
		return driver;
	}
}
