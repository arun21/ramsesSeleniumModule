package com.saintgobain.ramses.selenium.launcher;

import org.apache.log4j.Logger;

import com.saintgobain.ramses.selenium.helper.SeleniumFactory;
import com.saintgobain.ramses.selenium.helper.SeleniumType;

public class Launcher {
	private final static Logger logger = Logger.getLogger(Launcher.class);

	public static void main(String[] args) {
		String batchType = args[0];
		logger.info("Start for selenium type = " + batchType);
		AbstractRamsesSelenium batchToLaunch = SeleniumFactory.getInstance().getBatch(SeleniumType.valueOf(batchType));
		try {
			batchToLaunch.run();
		} catch (Exception ex) {
			logger.error("An exception occured : ", ex);
		}
		logger.info("End for selenium type = " + batchType);
	}
}
