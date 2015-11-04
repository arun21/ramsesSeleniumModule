package com.saintgobain.ramses.selenium.general.testcase;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.saintgobain.ramses.selenium.general.OperationType;
import com.saintgobain.ramses.selenium.helper.UIOperationHelper;
import com.saintgobain.ramses.selenium.helper.ValueSelectionHelper;
import com.saintgobain.ramses.selenium.util.AbstractSeleniumTestCase;
import com.saintgobain.ramses.selenium.util.CommonUtils;

public class CustomerInfoReminderTest extends AbstractSeleniumTestCase {

	private static final Logger logger = Logger.getLogger(CustomerInfoReminderTest.class);

	@Before
	public void setUpLogin() {
		openBrowserIE();
		performRamsesLogin();
	}

	@After
	public void tearDown() {
		stopDriver();
		captureDetails(this.getClass().getSimpleName());
	}

	@Test
	public void testCustomerInfoReminder1() {
		try {
			WebElement dropdown = UIOperationHelper.performLogin(OperationType.SELECT);
			ValueSelectionHelper.setValueForDropDown(dropdown, "653");
			setLog("[testCustomerInfoReminder1] :Site selected [MOA   Lorient] from drop down");
			WebElement validateSelect = UIOperationHelper.performLogin(OperationType.CLICK);
			validateSelect.click();
			setLog("[testCustomerInfoReminder1] :Ramses login successful");
			Assert.assertNotNull(validateSelect);
		} catch (Exception ex) {
			logger.error("[CustomerInfoReminderTest] - testCustomerInfoReminder1 ", ex);
			CommonUtils.captureErrorScreenShot(this.getClass().getSimpleName());
		}
	}

	@Test
	public void testCustomerInfoReminder2() {
		try {
			WebElement dropdown = UIOperationHelper.performLogin(OperationType.SELECT);
			ValueSelectionHelper.setValueForDropDown(dropdown, "656");
			setLog("[testCustomerInfoReminder2] :Site selected [MOA Saint-Brieurc] from drop down");
			WebElement validateSelect = UIOperationHelper.performLogin(OperationType.CLICK);
			validateSelect.click();
			setLog("[testCustomerInfoReminder2] :Ramses login successful");
			Assert.assertNotNull(validateSelect);
		} catch (Exception ex) {
			logger.error("[CustomerInfoReminderTest] - testCustomerInfoReminder2 ", ex);
			CommonUtils.captureErrorScreenShot(this.getClass().getSimpleName());
		}
	}
}
