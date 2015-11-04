package com.saintgobain.ramses.selenium.general.testcase;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.saintgobain.ramses.selenium.constants.ActionKeys;
import com.saintgobain.ramses.selenium.general.OperationType;
import com.saintgobain.ramses.selenium.helper.UIOperationHelper;
import com.saintgobain.ramses.selenium.util.AbstractSeleniumTestCase;
import com.saintgobain.ramses.selenium.util.CommonUtils;

public class RamsesLoginCredentialTest extends AbstractSeleniumTestCase {

	private static final Logger logger = Logger.getLogger(RamsesLoginCredentialTest.class);

	@BeforeClass
	public static void init() {
		openBrowserIE();
	}

	@After
	public void tearDown() {
		captureDetails(CustomerInfoReminderTest.class.getSimpleName());
	}

	@AfterClass
	public static void stop() {
		stopDriver();
	}

	@Test
	public void valid_LoginCredential() {
		try {
			WebElement login = UIOperationHelper.performLogin(OperationType.LOGIN);
			login.sendKeys(ActionKeys.UserName);
			WebElement password = UIOperationHelper.performLogin(OperationType.PASSWORD);
			password.sendKeys(ActionKeys.Password);
			WebElement validate = UIOperationHelper.performLogin(OperationType.CLICK);
			validate.click();
			Assert.assertNotNull(validate);
		} catch (Exception ex) {
			logger.error("[RamsesLoginCredentialTest] - valid_LoginCredential " + ex);
			CommonUtils.captureErrorScreenShot(RamsesLoginCredentialTest.class.getSimpleName());
		}
	}

	@Test
	public void invalid_LoginCredential() {
		try {
			WebElement login = UIOperationHelper.performLogin(OperationType.LOGIN);
			login.sendKeys(ActionKeys.UserName);
			WebElement password = UIOperationHelper.performLogin(OperationType.PASSWORD);
			password.sendKeys("magerna1");

			WebElement validate = UIOperationHelper.performLogin(OperationType.CLICK);
			validate.click();
		} catch (Exception ex) {
			logger.error("[RamsesLoginCredentialTest] - invalid_LoginCredential " + ex);
			CommonUtils.captureErrorScreenShot(RamsesLoginCredentialTest.class.getSimpleName());
		}
	}
}
