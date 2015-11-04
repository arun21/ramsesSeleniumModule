package com.saintgobain.ramses.selenium.general.testcase;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.saintgobain.ramses.selenium.constants.ActionKeys;
import com.saintgobain.ramses.selenium.constants.ActionValues;
import com.saintgobain.ramses.selenium.constants.URLConstants;
import com.saintgobain.ramses.selenium.general.LocaterType;
import com.saintgobain.ramses.selenium.general.OperationType;
import com.saintgobain.ramses.selenium.general.process.MailImplementionTest;
import com.saintgobain.ramses.selenium.helper.UIOperationHelper;
import com.saintgobain.ramses.selenium.helper.ValueSelectionHelper;
import com.saintgobain.ramses.selenium.util.AbstractSeleniumTestCase;
import com.saintgobain.ramses.selenium.util.CommonUtils;
import com.saintgobain.ramses.selenium.util.LocalManager;

public class MailImplementationTest extends AbstractSeleniumTestCase {

	private static final Logger logger = Logger.getLogger(MailImplementationTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		openBrowserIE();
	}

	@Before
	public void setUpLogin() {
		performRamsesLogin();
	}

	@After
	public void tearDown() {
		captureDetails(CustomerInfoReminderTest.class.getSimpleName());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		stopDriver();
	}

	@Test
	public void MailImplTest() {
		try {
			WebElement dropdown = UIOperationHelper.performLogin(OperationType.SELECT);
			ValueSelectionHelper.setValueForDropDown(dropdown, "651");

			WebElement validateSelect = UIOperationHelper.performLogin(OperationType.CLICK);
			validateSelect.click();

			reminderScreen(LocalManager.LocalWebDriverManager.getDriver());
		} catch (Exception ex) {
			CommonUtils.captureErrorScreenShot(MailImplementionTest.class.getSimpleName());
			logger.error("[MailImplementationTest] MailImplTest ", ex);
		}
	}

	private static void reminderScreen(WebDriver objBrowser) {
		try {
			UIOperationHelper.navigate(OperationType.NAVIGATE, URLConstants.RAMSES_TEST_FR_URL + ActionValues.REMINDER_SCREEN);
			boolean updateValue = Boolean.TRUE;
			List<WebElement> options = objBrowser.findElements(By.xpath("//*[starts-with(@id, '209440')]"));
			Random random = new Random();
			int index = random.nextInt(options.size());
			options.get(index).click();

			if (objBrowser.findElements(By.id("formatId")).size() != 0) {
				WebElement selectFormat = UIOperationHelper.getWebElement(OperationType.ELEMENT, "formatId", LocaterType.ID);
				ValueSelectionHelper.selectRandomIndexFromDropDown(selectFormat);
			}

			WebElement selectbtn = UIOperationHelper.getWebElement(OperationType.ELEMENT, "btnexport", LocaterType.ID);
			selectbtn.click();

			WebElement siteLink = objBrowser.findElement(By.tagName("u")).findElement(By.id("NoEmail"));
			// UIOperationHelper.getWebElement(OperationType.ELEMENT,
			// "id('NoSiteEmail')/p/span[2]/span/u", LocaterType.XPATH);
			WebElement siteContact = UIOperationHelper.getWebElement(OperationType.ELEMENT, "id('NoSiteContact')/p/span[2]/span/u", LocaterType.XPATH);
			// objBrowser.findElement(By.tagName("u")).findElement(By.id("NoContact"));
			WebElement customerLink = UIOperationHelper.getWebElement(OperationType.ELEMENT, "id('NoCustomerEmail')/p/span[2]/span/u", LocaterType.XPATH);
			// objBrowser.findElement(By.tagName("u")).findElement(By.id("NoCustEmail"));

			if (siteLink.getText().equals("Here") || siteLink.getText().equals("Ici")) {
				checkSiteMail(objBrowser, siteLink);
			} else if (siteContact.getText().equals("Here") || siteContact.getText().equals("Ici")) {
				checkSiteContact(objBrowser, siteContact);
			} else if (customerLink.getText().equals("Here") || customerLink.getText().equals("Ici")) {
				checkCustomerMail(objBrowser, customerLink);
			} else {
				updateValue = Boolean.FALSE;
				Robot object = new Robot();
				object.keyPress(KeyEvent.VK_TAB);
				object.keyRelease(KeyEvent.VK_TAB);
				object.keyPress(KeyEvent.VK_ENTER);
				object.keyRelease(KeyEvent.VK_ENTER);
			}
			if (updateValue) {
				reminderScreen(objBrowser);
			}
		} catch (Exception ex) {
			CommonUtils.captureErrorScreenShot(MailImplementionTest.class.getSimpleName());
			logger.error("[MailImplementationTest] reminderScreen ", ex);
		}
	}

	private static void checkCustomerMail(WebDriver objBrowser, WebElement customerLink) {
		try {
			customerLink.click();
			System.out.println(objBrowser.getCurrentUrl());
			WebElement mail = UIOperationHelper.getWebElement(OperationType.ELEMENT, "id('CustomerBean.comment')", LocaterType.XPATH);
			mail.sendKeys(ActionKeys.EXAMPLE_MAIL);

			WebElement modify = UIOperationHelper.getWebElement(OperationType.ELEMENT, "Btn4", LocaterType.NAME);
			modify.click();
		} catch (Exception ex) {
			CommonUtils.captureErrorScreenShot(MailImplementionTest.class.getSimpleName());
			logger.error("[MailImplementationTest] checkCustomerMail ", ex);
		}
	}

	private static void checkSiteContact(WebDriver objBrowser, WebElement siteContact) {
		try {
			siteContact.click();
			System.out.println(objBrowser.getCurrentUrl());
			WebElement mail = UIOperationHelper.getWebElement(OperationType.ELEMENT, "contactName", LocaterType.ID);
			mail.sendKeys(ActionKeys.EXAMPLE_CONTACT);

			WebElement modify = UIOperationHelper.getWebElement(OperationType.ELEMENT, "id('site')/fieldset[5]/input[1]", LocaterType.XPATH);
			modify.click();
		} catch (Exception ex) {
			CommonUtils.captureErrorScreenShot(MailImplementionTest.class.getSimpleName());
			logger.error("[MailImplementationTest] checkSiteContact ", ex);
		}
	}

	private static void checkSiteMail(WebDriver objBrowser, WebElement siteLink) {
		try {
			siteLink.click();
			System.out.println(objBrowser.getCurrentUrl());
			WebElement mail = UIOperationHelper.getWebElement(OperationType.ELEMENT, "mail", LocaterType.ID);
			mail.sendKeys(ActionKeys.EXAMPLE_MAIL);

			WebElement modify = UIOperationHelper.getWebElement(OperationType.ELEMENT, "id('site')/fieldset[5]/input[1]", LocaterType.XPATH);
			modify.click();
		} catch (Exception ex) {
			CommonUtils.captureErrorScreenShot(MailImplementionTest.class.getSimpleName());
			logger.error("[MailImplementationTest] checkSiteMail ", ex);
		}
	}
}
