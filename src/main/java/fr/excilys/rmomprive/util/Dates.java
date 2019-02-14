package fr.excilys.rmomprive.util;

import java.sql.Timestamp;

public class Dates {
	public static boolean isValidTimestamp(String timestamp) {
		try {
			Timestamp.valueOf(timestamp);
		} catch (IllegalArgumentException e) {
			return false;
		}
		
		return true;
	}
}
