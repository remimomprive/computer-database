package fr.excilys.rmomprive.ui.console;

import java.util.Scanner;

import fr.excilys.rmomprive.ui.console.menu.MenuDeleteComputer;
import fr.excilys.rmomprive.ui.console.menu.MenuChoice;
import fr.excilys.rmomprive.ui.console.menu.MenuCreateComputer;
import fr.excilys.rmomprive.ui.console.menu.MenuDisplayComputerDetails;
import fr.excilys.rmomprive.ui.console.menu.MenuListCompanies;
import fr.excilys.rmomprive.ui.console.menu.MenuListComputers;
import fr.excilys.rmomprive.ui.console.menu.MenuListPageComputers;
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
		System.out.printf("%d - List computers\n", MenuChoice.LIST_COMPUTERS.getId());
		System.out.printf("%d - List a computer page\n", MenuChoice.LIST_COMPUTER_PAGES.getId());
		System.out.printf("%d - List companies\n", MenuChoice.LIST_COMPANIES.getId());
		System.out.printf("%d - Show computer details (the detailed information of only one computer)\n", MenuChoice.SHOW_COMPUTER_DETAILS.getId());
		System.out.printf("%d - Create a computer\n", MenuChoice.CREATE_COMPUTER.getId());
		System.out.printf("%d - Update a computer\n", MenuChoice.UPDATE_COMPUTER.getId());
		System.out.printf("%d - Delete a computer\n", MenuChoice.DELETE_COMPUTER.getId());
		System.out.printf("%d - Exit\n", MenuChoice.EXIT.getId());
		System.out.println("=====");
	}
	
	/**
	 * Asks a choice until its value is correct
	 * @return The value of the choice
	 */
	private static MenuChoice askChoice() {
    	MenuChoice choice = null;
    	int choiceInt;
    	
    	// Ask the choice until the user selects a valid choice
    	do {
    		// Display the menu
    		displayMenu();
    		
    		// Ask the choice from the user
    		Scanner scanner = new Scanner(System.in);
    		choiceInt = Integer.valueOf(scanner. nextLine());
    		choice = MenuChoice.getById(choiceInt);
			
			// If the choice is not valid
			if (choice == null)
				System.out.println("Invalid choice");
		} while (choice == null);
    	
    	return choice;
	}
	
	public static void run() {
		MenuChoice choice = null;
    		
    	do {
    		choice = askChoice();
    		
	    	switch (choice) {
	    		// List all computers
				case LIST_COMPUTERS:
					MenuListComputers.getInstance().show();
		    		break;
		    		
				case LIST_COMPUTER_PAGES:
					MenuListPageComputers.getInstance().show();
		    		break;
		    		
		    	// List all companies
	    		case LIST_COMPANIES:
	    			MenuListCompanies.getInstance().show();
		    		break;
		    		
		    	// Display infos for a computer
				case SHOW_COMPUTER_DETAILS:
					MenuDisplayComputerDetails.getInstance().show();
	    			break;
	    			
				// Add a computer
				case CREATE_COMPUTER:
					MenuCreateComputer.getInstance().show();
	    			break;
	    			
				// Add a computer
				case UPDATE_COMPUTER:
					MenuUpdateComputer.getInstance().show();
	    			break;
		    			
	    		// Delete the computer
				case DELETE_COMPUTER:
					MenuDeleteComputer.getInstance().show();
					break;
					
		    	// Exit the program
	    		case EXIT:
	    			System.out.println("Bye");
	    			break;
	    	}
    	} while (choice != MenuChoice.EXIT);
	}
}
