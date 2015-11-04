package com.saintgobain.ramses.selenium.general;

public enum TestStatus {
	PASS(1), FAIL(2), SKIP(3);

	private int intValue;

	private TestStatus(int intValue) {
		this.setIntValue(intValue);
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public String getName() {
		return this.name();
	}
}
