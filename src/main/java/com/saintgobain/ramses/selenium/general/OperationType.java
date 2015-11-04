package com.saintgobain.ramses.selenium.general;

public enum OperationType {

	LOGIN(1), PASSWORD(2), CLICK(3), GETTEXT(4), SETTEXT(5), NAVIGATE(6), ELEMENT(7), SELECT(8);

	private int intValue;

	private OperationType(int intValue) {
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
