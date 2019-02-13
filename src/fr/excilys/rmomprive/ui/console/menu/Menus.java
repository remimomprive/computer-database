package fr.excilys.rmomprive.ui.console.menu;

import java.sql.Timestamp;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.rmomprive.service.CompanyService;
import fr.excilys.rmomprive.util.Dates;

public class Menus {
	private static Logger logger;
	
	static {
		Menus.logger = LoggerFactory.getLogger(Menus.class);
	}
	
	public static String readString() {
		Scanner scanner = new Scanner(System.in);
		return scanner. nextLine();
	}
	
	/***
	 * This methods asks the user to put an integer into the console until a valid integer is given
	 * @param message
	 * @return The given integer
	 */
	public static int readInteger(String message) {
		int returnValue = -1;
		boolean validValue = false;
		
		do {
			try {
				returnValue = Integer.valueOf(readString());
				validValue = true;
			} catch(NumberFormatException e) {
				System.out.println(message);
			}
		} while (!validValue);
		
		return returnValue;
	}
	
	/**
	 * Asks a String corresponding to the computer name
	 * @return The given String
	 */
	public static String readComputerName() {
		System.out.println("What's the computer name ?");
		String name = null;
		
		do {
			name = Menus.readString();
			
			if (name.equals(""))
				logger.error("The computer name should not be null\n");
		} while (name.equals(""));
		
		return name;
	}

	/**
	 * Asks the user a timestamp String until its format is valid
	 * @param nullable true if the user can set NULL to return a null Timestamp
	 * @return The String timestamp converted into a Timestamp object
	 */
	public static Timestamp readTimestamp(boolean nullable) {
		String timestampString;
		Timestamp timestamp = null;
		
		do {
			// Read a string value from the terminal
			timestampString = Menus.readString();
			
			// If the user sets null value and the value can be nullable
			if (nullable && timestampString.equals(""))
				timestamp = null;
			// If the String format is not valid
			else if (!Dates.isValidTimestamp(timestampString))
				logger.error("The timestamp format is not valid\n");
			// Else, the String format is not valid
			else
				timestamp = Timestamp.valueOf(timestampString);
		} while (!(Dates.isValidTimestamp(timestampString) || (nullable && timestampString != null)));
		
		return timestamp;
	}
	
	/**
	 * Ask a timestamp String, preventing the user to set null value
	 * @return The String timestamp converted into a Timestamp object
	 */
	public static Timestamp readTimestamp() {
		return Menus.readTimestamp(false);
	}
	
	/**
	 * Ask a company id until its value corresponds to an existing company id
	 * @return The given company id
	 */
	public static int readCompanyId() {
		System.out.println("What's the company id ?");
		int companyId = -1;
		
		do {
			companyId = Menus.readInteger("The company id should be an integer");
			
			if (!CompanyService.getInstance().checkExistenceById(companyId))
				logger.error("The company does not exist\n");
		} while (!CompanyService.getInstance().checkExistenceById(companyId));
		
		return companyId;
	}
}
