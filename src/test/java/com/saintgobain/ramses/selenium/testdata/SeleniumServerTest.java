package com.saintgobain.ramses.selenium.testdata;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestBase;

public class SeleniumServerTest extends SeleneseTestBase {

	private static final Logger logger = Logger.getLogger(SeleniumServerTest.class);
	public static SeleniumServer server;
	private static DefaultSelenium selenium;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			server = new SeleniumServer();
			server.start();
		} catch (Exception ex) {
			logger.error("[RamsesLoginTest] - SeleniumServer instance: " + ex);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		selenium.stop();
		server.stop();
	}

	@Test
	public void testSearchGoogle() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://www.google.co.in/");
		selenium.start();
		selenium.open("http://www.google.co.in/");
		selenium.type("q", "Selenium with JUnit");
	}

	private void init_browser() {
		try {
			int seleniumPort = Integer.parseInt(System.getProperty("selenium.port", "4444"));
			String browser = System.getProperty("seleniumBrowser", "*firefox");// iexplore
			String serverUrl = System.getProperty("serverUrl", "http://10.87.54.29:8484");
			selenium = new DefaultSelenium("localhost", seleniumPort, browser, serverUrl);
			selenium.start();
		} catch (Exception ex) {
			logger.error("[RamsesLoginTest] - init_browser: " + ex);
		}
	}

	@Test
	public void testRamsesLogin() {
		init_browser();
		selenium.windowMaximize();
		selenium.open("/RamsesTestFR/");
		selenium.type("input[name='loginActionFormBean.login']", "system");
		selenium.type("input[name='loginActionFormBean.password']", "magerna");
		selenium.click("input#Btn1");
		selenium.waitForPageToLoad("30000");
	}

}
