package com.saintgobain.ramses.selenium.util;

import org.openqa.selenium.WebDriver;

public class LocalManager {

	public static class LocalStringBufferManager {
		private static ThreadLocal<StringBuffer> stringBuffer = new ThreadLocal<StringBuffer>();

		public static StringBuffer getStringBuffer() {
			if (stringBuffer.get() != null) {
				return stringBuffer.get();
			} else {
				setStringBuffer(new StringBuffer());
				return stringBuffer.get();
			}
		}

		public static void setStringBuffer(StringBuffer stBuffer) {
			stringBuffer.set(stBuffer);
		}
	}

	public static class LocalStringManager {
		private static ThreadLocal<String> timestamp = new ThreadLocal<String>();

		public static String getTimestamp() {
			return timestamp.get();
		}

		public static void setTimestamp(String ts) {
			timestamp.set(ts);
		}
	}

	public static class LocalWebDriverManager {
		private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

		public static WebDriver getDriver() {
			return webDriver.get();
		}

		public static void setWebDriver(WebDriver driver) {
			webDriver.set(driver);
		}
	}
}
