package com.saintgobain.ramses.selenium.helper;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.saintgobain.ramses.selenium.general.LocaterType;
import com.saintgobain.ramses.selenium.general.OperationType;
import com.saintgobain.ramses.selenium.util.LocalManager;

public class UIOperationHelper {

	public static final Logger logger = Logger.getLogger(UIOperationHelper.class);

	public static WebElement performLogin(OperationType op) {
		WebElement element = null;
		switch (op.getIntValue()) {
		case 1:
			element = getWebElement(OperationType.ELEMENT, "input[name='loginActionFormBean.login']", LocaterType.CSS);
			break;
		case 2:
			element = getWebElement(OperationType.ELEMENT, "input[name='loginActionFormBean.password']", LocaterType.CSS);
			break;
		case 8:
			element = getWebElement(OperationType.ELEMENT, "select[name='loginActionFormBean.idSelectedSite']", LocaterType.CSS);
			break;
		case 3:
			element = getWebElement(OperationType.ELEMENT, "input#Btn1", LocaterType.CSS);
			break;
		}
		return element;
	}

	public static WebElement getWebElement(OperationType operation, String objectName, LocaterType type) {
		WebElement element = null;
		WebDriver driver = LocalManager.LocalWebDriverManager.getDriver();
		switch (operation.getIntValue()) {
		case 7:
			element = driver.findElement(getObject(objectName, type));
			break;
		default:
			break;
		}
		return element;
	}

	public static void navigate(OperationType operation, String value) {
		WebDriver driver = LocalManager.LocalWebDriverManager.getDriver();
		switch (operation.getIntValue()) {
		case 6:
			driver.navigate().to(value);
			break;
		default:
			break;
		}
	}

	public static void performOperation(OperationType operation, String objectName, LocaterType type, String value) {
		WebDriver driver = LocalManager.LocalWebDriverManager.getDriver();
		switch (operation.getIntValue()) {
		case 3:
			driver.findElement(getObject(objectName, type)).click();
			break;
		case 4:
			driver.findElement(getObject(objectName, type)).getText();
			break;
		case 5:
			driver.findElement(getObject(objectName, type)).sendKeys(value);
			break;
		default:
			break;
		}
	}

	public static By getObject(String objectName, LocaterType type) {
		if (type.name().equalsIgnoreCase(LocaterType.XPATH.getName())) {
			return By.xpath(objectName);
		} else if (type.name().equalsIgnoreCase(LocaterType.CLASSNAME.getName())) {
			return By.className(objectName);
		} else if (type.name().equalsIgnoreCase(LocaterType.NAME.getName())) {
			return By.name(objectName);
		} else if (type.name().equalsIgnoreCase(LocaterType.CSS.getName())) {
			return By.cssSelector(objectName);
		} else if (type.name().equalsIgnoreCase(LocaterType.LINK.getName())) {
			return By.linkText(objectName);
		} else if (type.name().equalsIgnoreCase(LocaterType.PARTIALLINK.getName())) {
			return By.partialLinkText(objectName);
		} else if (type.name().equalsIgnoreCase(LocaterType.ID.getName())) {
			return By.id(objectName);
		} else {
			logger.error("[UIOperationHelper] - getObject - Wrong object type");
		}
		return null;
	}
}
