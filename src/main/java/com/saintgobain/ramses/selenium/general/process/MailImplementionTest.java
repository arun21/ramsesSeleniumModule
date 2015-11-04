package com.saintgobain.ramses.selenium.general.process;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.saintgobain.ramses.selenium.constants.ActionKeys;
import com.saintgobain.ramses.selenium.constants.ActionValues;
import com.saintgobain.ramses.selenium.constants.SeleniumConstants;
import com.saintgobain.ramses.selenium.constants.URLConstants;
import com.saintgobain.ramses.selenium.helper.ValueSelectionHelper;
import com.saintgobain.ramses.selenium.util.CommonUtils;
import com.saintgobain.ramses.selenium.util.WebDriverUtil;

public class MailImplementionTest {

	private static final Logger logger = Logger.getLogger(MailImplementionTest.class);

	public static void main(String[] args) {
		WebDriver objBrowser = CommonUtils.initDriver(BrowserType.IE);
		try {
			WebElement login = objBrowser.findElement(By.cssSelector("input[name='loginActionFormBean.login']"));
			login.sendKeys(ActionKeys.UserName);
			WebElement password = objBrowser.findElement(By.cssSelector("input[name='loginActionFormBean.password']"));
			password.sendKeys(ActionKeys.Password);
			WebElement validateLogin = objBrowser.findElement(By.cssSelector("input#Btn1"));
			validateLogin.click();

			WebElement dropdown = objBrowser.findElement(By.cssSelector("select[name='loginActionFormBean.idSelectedSite']"));
			ValueSelectionHelper.setValueForDropDown(dropdown, "651");// 651-FR

			WebElement validateSelect = objBrowser.findElement(By.cssSelector("input#Btn1"));
			validateSelect.click();

			reminderScreen(objBrowser);

			objBrowser.quit();
			WebDriverUtil.closeIEDriver(SeleniumConstants.CLOSE_IE_DRIVER);
		} catch (Exception ex) {
			CommonUtils.captureErrorScreenShot(MailImplementionTest.class.getSimpleName());
			logger.error(ex.getMessage());
		}
	}

	private static void reminderScreen(WebDriver objBrowser) {

		// WebElement dashboard =
		// objBrowser.findElement(By.xpath("id('nav')/li[2]/a"));
		// dashboard.click();
		// WebElement reminder =
		// objBrowser.findElement(By.xpath("id('nav')/li[2]/ul/li[2]/a/span"));
		// reminder.click();

		objBrowser.navigate().to(URLConstants.RAMSES_TEST_FR_URL + ActionValues.REMINDER_SCREEN);
		WebDriverWait wait = new WebDriverWait(objBrowser, 10);
		boolean updateValue = Boolean.TRUE;
		List<WebElement> options = objBrowser.findElements(By.xpath("//*[starts-with(@id, '209440')]"));
		// 209440-FR-CLIMVER | 383768-SC-SCANGLAS
		Random random = new Random();
		int index = random.nextInt(options.size());
		options.get(index).click();
		// WebElement oCheckBox =
		// objBrowser.findElement(By.cssSelector("input[id='209440']"));

		if (objBrowser.findElements(By.xpath("id('formatId')")).size() != 0) {
			WebElement selectFormat = objBrowser.findElement(By.xpath("id('formatId')"));
			ValueSelectionHelper.selectRandomIndexFromDropDown(selectFormat);
		}

		WebElement selectbtn = objBrowser.findElement(By.xpath("id('btnexport')"));
		selectbtn.click();

		WebElement siteLink = objBrowser.findElement(By.xpath("id('NoSiteEmail')/p/span[2]/span/u"));
		WebElement siteContact = objBrowser.findElement(By.xpath("id('NoSiteContact')/p/span[2]/span/u"));
		WebElement customerLink = objBrowser.findElement(By.xpath("id('NoCustomerEmail')/p/span[2]/span/u"));

		if (siteLink.getText().equals("Here") || siteLink.getText().equals("Ici")) {
			checkSiteMail(objBrowser, siteLink);
		} else if (siteContact.getText().equals("Here") || siteContact.getText().equals("Ici")) {
			checkSiteContact(objBrowser, siteContact);
		} else if (customerLink.getText().equals("Here") || customerLink.getText().equals("Ici")) {
			checkCustomerMail(objBrowser, customerLink);
		} else {
			updateValue = Boolean.FALSE;
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div[3]/div/button")));
			WebElement mail = objBrowser.findElement(By.xpath("/html/body/div[4]/div[3]/div/button"));
			mail.click();
		}
		if (updateValue) {
			reminderScreen(objBrowser);
		}
	}

	private static void checkCustomerMail(WebDriver objBrowser, WebElement customerLink) {
		customerLink.click();
		System.out.println(objBrowser.getCurrentUrl());
		WebElement mail = objBrowser.findElement(By.xpath("id('CustomerBean.comment')"));
		mail.sendKeys(ActionKeys.EXAMPLE_MAIL);

		WebElement modify = objBrowser.findElement(By.name("Btn4"));
		modify.click();

	}

	private static void checkSiteContact(WebDriver objBrowser, WebElement siteContact) {
		siteContact.click();
		System.out.println(objBrowser.getCurrentUrl());
		WebElement mail = objBrowser.findElement(By.xpath("id('contactName')"));
		mail.sendKeys(ActionKeys.EXAMPLE_CONTACT);

		WebElement modify = objBrowser.findElement(By.xpath("id('site')/fieldset[5]/input[1]"));
		modify.click();
	}

	private static void checkSiteMail(WebDriver objBrowser, WebElement siteLink) {
		siteLink.click();
		System.out.println(objBrowser.getCurrentUrl());
		WebElement mail = objBrowser.findElement(By.xpath("id('mail')"));
		mail.sendKeys(ActionKeys.EXAMPLE_MAIL);

		WebElement modify = objBrowser.findElement(By.xpath("id('site')/fieldset[5]/input[1]"));
		modify.click();
	}
}
