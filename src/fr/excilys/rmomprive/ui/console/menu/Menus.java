package fr.excilys.rmomprive.ui.console.menu;

import java.util.Scanner;

public class Menus {
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
}
