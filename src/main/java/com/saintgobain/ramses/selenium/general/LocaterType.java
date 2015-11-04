package com.saintgobain.ramses.selenium.general;

public enum LocaterType {

	XPATH(1), CLASSNAME(2), CSS(3), NAME(4), LINK(5), PARTIALLINK(6), ID(7);

	private int intValue;

	private LocaterType(int intValue) {
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
