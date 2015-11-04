package com.saintgobain.ramses.selenium.helper;

import com.saintgobain.ramses.selenium.launcher.AbstractRamsesSelenium;
import com.saintgobain.ramses.selenium.launcher.FileIntegration;

public class SeleniumFactory {
	private static SeleniumFactory instance;

	private SeleniumFactory() {

	}

	public static SeleniumFactory getInstance() {

		if (instance == null) {
			instance = new SeleniumFactory();
		}
		return instance;
	}

	public AbstractRamsesSelenium getBatch(SeleniumType type) {
		if (type == SeleniumType.SELENIUM_TEST) {
			return new FileIntegration();
		} else {
			return null;
		}
	}
}
