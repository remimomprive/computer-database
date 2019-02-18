package fr.excilys.rmomprive.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Dates {
	public static Date parse(String dateString) throws ParseException {
		DateFormat format = new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH);
		return format.parse(dateString);
	}
	
	public static boolean isValidDate(String dateString) {
		try {
			DateFormat format = new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH);
			format.parse(dateString);
		} catch (ParseException e) {
			return false;
		}
		
		return true;
	}
}
