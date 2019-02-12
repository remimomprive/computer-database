package fr.excilys.rmomprive.ui.console;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import fr.excilys.rmomprive.ui.console.menu.MenuDeleteComputer;
import fr.excilys.rmomprive.ui.console.menu.MenuComputerForm;
import fr.excilys.rmomprive.ui.console.menu.MenuCreateComputer;
import fr.excilys.rmomprive.ui.console.menu.MenuDisplayComputerDetails;
import fr.excilys.rmomprive.ui.console.menu.MenuListCompanies;
import fr.excilys.rmomprive.ui.console.menu.MenuListComputers;
import fr.excilys.rmomprive.ui.console.menu.MenuUpdateComputer;

public class Console {
	/**
	 * Displays the choices
	 */
	private static void displayMenu() {
		System.out.println("=====");
		System.out.println("COMPUTER DATABASE MENU");
		System.out.println("-----");
		System.out.println("Choose an action");
		System.out.println("=====");
		System.out.println("1 - List computers");
		System.out.println("2 - List companies");
		System.out.println("3 - Show computer details (the detailed information of only one computer)");
		System.out.println("4 - Create a computer");
		System.out.println("5 - Update a computer");
		System.out.println("6 - Delete a computer");
		System.out.println("9 - Exit");
		System.out.println("=====");
	}
	
	/**
	 * Asks a choice until its value is correct
	 * @return The value of the choice
	 */
	private static int askChoice() {
    	List<Integer> validMenuOptions = Arrays.asList(1, 2, 3, 4, 5, 6, 9);
    	int choice = 0;
    	
    	// Ask the choice until the user selects a valid choice
    	do {
    		// Display the menu
    		displayMenu();
    		
    		// Ask the choice from the user
    		Scanner scanner = new Scanner(System.in);
			choice = Integer.valueOf(scanner. nextLine());
			
			// If the choice is not valid
			if (!validMenuOptions.contains(choice))
				System.out.println("Invalid choice");
		} while (!validMenuOptions.contains(choice));
    	
    	return choice;
	}
	
	public static void run() {
		int choice = -1;
    		
    	do {
    		choice = askChoice();
    		
	    	switch (choice) {
	    		// List all computers
				case 1:
					MenuListComputers.getInstance().show();
		    		break;
		    		
		    	// List all companies
	    		case 2:
	    			MenuListCompanies.getInstance().show();
		    		break;
		    		
		    	// Display infos for a computer
				case 3:
					MenuDisplayComputerDetails.getInstance().show();
	    			break;
	    			
				// Add a computer
				case 4:
					MenuCreateComputer.getInstance().show();
	    			break;
	    			
				// Add a computer
				case 5:
					MenuUpdateComputer.getInstance().show();
	    			break;
		    			
	    		// Delete the computer
				case 6:
					MenuDeleteComputer.getInstance().show();
					break;
					
		    	// Exit the program
	    		case 9:
	    			System.out.println("Bye");
	    			break;
	    	}
    	} while (choice != 9);
	}
}
