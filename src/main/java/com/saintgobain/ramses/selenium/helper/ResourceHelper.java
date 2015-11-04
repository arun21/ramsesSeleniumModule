package com.saintgobain.ramses.selenium.helper;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ResourceHelper {

	private static final Logger logger = Logger.getLogger(ResourceHelper.class);

	public static String getConfigBundleValue(String propertyKey) {
		return ResourceBundle.getBundle("cfg_selenium").getString(propertyKey);
	}

	public static String getApplicationResourceValue(String propertyKey) {
		String language = "en";
		String propertyValue;
		try {
			propertyValue = ResourceBundle.getBundle("ApplicationRessources_" + language).getString(propertyKey);
		} catch (MissingResourceException ex) {
			propertyValue = ResourceBundle.getBundle("ApplicationRessources_en").getString(propertyKey);
			language = "en";
			logger.warn("Cant find " + "ApplicationRessources_" + language + ". use default ApplicationRessources_en");
		}
		return propertyValue;
	}
}
