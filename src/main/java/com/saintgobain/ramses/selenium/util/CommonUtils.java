package com.saintgobain.ramses.selenium.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.google.common.base.Function;
import com.saintgobain.ramses.selenium.constants.URLConstants;
import com.saintgobain.ramses.selenium.general.OperationType;
import com.saintgobain.ramses.selenium.helper.ResourceHelper;
import com.saintgobain.ramses.selenium.helper.UIOperationHelper;
import com.thoughtworks.selenium.SeleniumException;

public class CommonUtils {

	private static String resultLog = ResourceHelper.getConfigBundleValue("result_log");
	private static String screenshotLog = ResourceHelper.getConfigBundleValue("screenshot_log");
	private static String errorScreenshotLog = ResourceHelper.getConfigBundleValue("errorScreenshot_log");

	public static WebDriver initDriver(String driverType) {
		System.out.println("Execute Before Test Method.");
		String timestamp = DateUtil.getFormat("HH:mm:ss").format(Calendar.getInstance().getTime()).toString().replace(":", ".");
		StringBuffer stringBuffer = LocalManager.LocalStringBufferManager.getStringBuffer();
		stringBuffer.append("\nStarting " + driverType.toUpperCase() + " driver...");
		WebDriver webDriver = LocalDriverFactory.createInstance(driverType);
		LocalManager.LocalWebDriverManager.setWebDriver(webDriver);
		// LocalManager.LocalStringBufferManager.setStringBuffer(stringBuffer);
		LocalManager.LocalStringManager.setTimestamp(timestamp);

		webDriver.manage().window().maximize();
		getReporter().append("\nImplicit Wait between each Element: " + 20 + " Seconds");
		webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		getReporter().append("\n" + driverType.toUpperCase() + " driver has started.");
		UIOperationHelper.navigate(OperationType.NAVIGATE, URLConstants.RAMSES_TEST_FR_URL);
		getReporter().append("\nOpen URL " + URLConstants.RAMSES_TEST_FR_URL);
		return webDriver;
	}

	public static WebDriver initDriverWithURL(String driverType, String URL) {
		System.out.println("Execute Before Test Method.");
		String timestamp = DateUtil.getFormat("HH:mm:ss").format(Calendar.getInstance().getTime()).toString().replace(":", ".");
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("\nStarting " + driverType + " driver...");

		WebDriver webDriver = LocalDriverFactory.createInstance(driverType);
		LocalManager.LocalWebDriverManager.setWebDriver(webDriver);
		LocalManager.LocalStringBufferManager.setStringBuffer(stringBuffer);
		LocalManager.LocalStringManager.setTimestamp(timestamp);

		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		getReporter().append("\n " + driverType + " driver has started.");
		webDriver.navigate().to(URL);
		getReporter().append("\nOpen URL " + URL);
		return webDriver;
	}

	public static void stopDriver() {
		System.out.println("Execute After Test Method.");
		WebDriver webDriver = LocalManager.LocalWebDriverManager.getDriver();
		if (webDriver != null) {
			LocalManager.LocalStringBufferManager.getStringBuffer().append("\nClosing URL " + URLConstants.RAMSES_TEST_FR_URL);
			webDriver.close();
			LocalManager.LocalStringBufferManager.getStringBuffer().append("\nQuit Driver Instance.");
			webDriver.quit();
		}
	}

	public static void stopDriverWithURL(String URL) {
		System.out.println("Execute After Test Method.");
		WebDriver webDriver = LocalManager.LocalWebDriverManager.getDriver();
		if (webDriver != null) {
			LocalManager.LocalStringBufferManager.getStringBuffer().append("\nClosing URL " + URL);
			webDriver.close();
			LocalManager.LocalStringBufferManager.getStringBuffer().append("\nQuit Driver Instance.");
			webDriver.quit();
		}
	}

	public void beforeMethod(Method m, ITestResult result) {
		System.out.println("----------------------------------------------------");
		System.out.println("Method : " + m.getName() + " has started...");
		getReporter().append("\n----------------------------------------------------");
		getReporter().append("\nMethod : " + m.getName() + " has started...");

	}

	public void afterMethod(Method m, ITestResult result) {

		System.out.println("Method : " + m.getName() + " has completed...");
		System.out.println("----------------------------------------------------");
		getReporter().append("\nMethod : " + m.getName() + " has completed...");
		getReporter().append("\n----------------------------------------------------");
		String str = "";
		for (Object param : result.getParameters()) {
			str = (String) param;
			System.out.println("Project : " + str);
		}
		System.out.println("Start generating logs...");
		if (!new File(resultLog + "/" + getTimeStamp()).exists()) {
			new File(resultLog + "/" + getTimeStamp()).mkdirs();
		}

		String logPath = new File(resultLog + "/" + getTimeStamp() + "/ReportLogs_" + str + ".txt").getAbsolutePath();
		FileWriter fw;
		BufferedWriter writer = null;
		try {
			fw = new FileWriter(logPath);
			writer = new BufferedWriter(fw);
			writer.append(getReporter().toString());
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Writing logs into " + logPath);

		StringBuffer strBuffer = new StringBuffer();
		LocalManager.LocalStringBufferManager.setStringBuffer(strBuffer);
	}

	public static void captureLogs(String logpath) {
		getReporter().append("\n----------------------------------------------------");
		System.out.println("Start generating logs...");
		String path = new File(resultLog + "/" + DateUtil.getCurrentDay()).getAbsolutePath();
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		String logPath = new File(path + "/ReportLogs_" + logpath + ".txt").getAbsolutePath();
		FileWriter fw;
		BufferedWriter writer = null;
		try {
			fw = new FileWriter(logPath);
			writer = new BufferedWriter(fw);
			writer.append(getReporter().toString());
			writer.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("Writing logs into " + logPath);
		getReporter().append("\nWriting logs into " + logPath);
		// StringBuffer strBuffer = new StringBuffer();
		// LocalManager.LocalStringBufferManager.setStringBuffer(strBuffer);
	}

	public static StringBuffer getReporter() {
		return LocalManager.LocalStringBufferManager.getStringBuffer();
	}

	public static StringBuffer getReport(String text) {
		return LocalManager.LocalStringBufferManager.getStringBuffer().append("\n" + text);
	}

	public static String getTimeStamp() {
		return LocalManager.LocalStringManager.getTimestamp();
	}

	public static WebDriver getDriverInstance() {
		if (LocalManager.LocalWebDriverManager.getDriver() == null) {
			System.out.println("driver is null in getDriverInstance method");
		}
		return LocalManager.LocalWebDriverManager.getDriver();
	}

	public static void setReporter() {
		LocalManager.LocalStringBufferManager.setStringBuffer(new StringBuffer());
	}

	public static void setTimeStamp() {
		LocalManager.LocalStringManager.setTimestamp(new String());
	}

	public static void setDriverInstance(WebDriver wd) {
		LocalManager.LocalWebDriverManager.setWebDriver(wd);
	}

	public static void setLocalManagerDriverInstance(WebDriver wd) {
		setReporter();
		setTimeStamp();
		setDriverInstance(wd);
	}

	public static void setLocalManagerInstance() {
		setReporter();
		setTimeStamp();
	}

	public static void captureLogsAndcloseDriver(String className, String resourceValue) {
		captureLogs(className);
		WebDriverUtil.closeIEDriver(resourceValue);
	}

	public static WebElement waitForWebElement(final By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(LocalManager.LocalWebDriverManager.getDriver()).withTimeout(300, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
		        .ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});

		return foo;
	};

	public static WebElement waitForWebElement(final WebElement element) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(LocalManager.LocalWebDriverManager.getDriver()).withTimeout(300, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
		        .ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					element.isDisplayed();
				} catch (Exception e) {
					return element;
				}
				return element;
			}
		});

		return foo;
	};

	public static void waitForNotWebElementPresent(final By locator) {

		WebDriverWait webDriverWait = new WebDriverWait(LocalManager.LocalWebDriverManager.getDriver(), 5400);

		webDriverWait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				try {
					driver.findElement(locator);
				} catch (NoSuchElementException e) {
					return true;
				} catch (SeleniumException se) {
					return true;
				}
				return false;
			}
		});
	}

	public static String captureErrorScreenShot(String folderpath) {
		getReporter().append("\nCapturing Error Screenshot.");
		File scrFile = ((TakesScreenshot) LocalManager.LocalWebDriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
		String path = new File(errorScreenshotLog + "/" + DateUtil.getCurrentDay() + "/" + folderpath).getAbsolutePath();
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		String screenshottimestamp = new Timestamp(Calendar.getInstance().getTime().getTime()).toString().replace(" ", "-").replace("-", "_").replace(":", "_").replace(".", "_");
		try {
			FileUtils.copyFile(scrFile, new File(path + "/" + screenshottimestamp + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		getReporter().append("\nError Screenshot location : " + path + "/" + screenshottimestamp + ".jpg");
		System.out.println("\nError Screenshot location : " + path);
		return path;
	}

	public static String captureScreenShotAndSave(String folderpath) {
		getReporter().append("\nCapturing screenshot.");
		File scrFile = ((TakesScreenshot) LocalManager.LocalWebDriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
		String path = new File(screenshotLog + "/" + DateUtil.getCurrentDay() + "/" + folderpath).getAbsolutePath();
		// LocalManager.LocalStringManager.getTimestamp()
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		String screenshottimestamp = new Timestamp(Calendar.getInstance().getTime().getTime()).toString().replace(" ", "-").replace("-", "_").replace(":", "_").replace(".", "_");
		try {
			FileUtils.copyFile(scrFile, new File(path + "/" + screenshottimestamp + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		getReporter().append("\nScreenshot location : " + path + "/" + screenshottimestamp + ".jpg");
		System.out.println("\nScreenshot location : " + path);
		return path;
	}

	public static void captureAllLogs(Logger logger, String value) {
		logger.info(value);
		getReport(value);
	}

	public static void captureLogs(Logger logger, String loggerValue, String reportValue) {
		logger.info(loggerValue);
		getReport(reportValue);
	}

}
