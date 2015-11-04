package com.saintgobain.ramses.selenium.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

	public static String getCurrentDay() {
		String date = StringUtils.EMPTY;
		try {
			SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date MyDate = newDateFormat.parse(newDateFormat.format(DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.DATE)));
			newDateFormat.applyPattern("EEEE d MMM yyyy");
			date = newDateFormat.format(MyDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static SimpleDateFormat getFormat(String str) {
		SimpleDateFormat newDateFormat = new SimpleDateFormat(str);
		return newDateFormat;

	}
}
