package fr.excilys.rmomprive.ui;

import java.util.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import fr.excilys.rmomprive.controller.CompanyController;
import fr.excilys.rmomprive.controller.ComputerController;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.model.ComputerBuilder;

public class Console 
{
	/**
	 * Displays the choices
	 */
	private static void displayMenu() {
		System.out.println("=====");
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
	 * Displays the choices and asks the user to select one
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
			Scanner scanner = new Scanner(System. in);
			String input = scanner. nextLine();
			choice = Integer.valueOf(input);
			
			// If the choice is not valid
			if (!validMenuOptions.contains(choice))
				System.out.println("Invalid choice");
		} while (!validMenuOptions.contains(choice));
    	
    	return choice;
	}
	
    public static void main(String[] args)
    {
    	int choice = askChoice();
    	
    	Scanner scanner;
    	String input;
    	int computerId;
    		
    	switch (choice) {
    		// List all computers
			case 1:
				Collection<Computer> computers = ComputerController.getInstance().getAll();
	    		System.out.println(computers);
	    		break;
	    		
	    	// List all companies
    		case 2:
	    		Collection<Company> companies = CompanyController.getInstance().getAll();
	    		System.out.println(companies);
	    		break;
	    		
	    	// Display infos for a computer
			case 3:
				System.out.println("What's the computer id ?");
				
				// Ask the computer id from the user command line
				scanner = new Scanner(System. in);
				input = scanner. nextLine();
				computerId = Integer.valueOf(input);
				
				// Retrieve the computer
				Computer computer = ComputerController.getInstance().getById(computerId);
				if (computer != null)
					System.out.println(computer);
				else
					System.out.println("This computer does not exist");
				
    			break;
    			
			// Add a computer
			case 4:
				System.out.println("What's the computer id ?");
				
				ComputerController.getInstance().add(
					new ComputerBuilder()
						.setName("name")
						.setIntroduced(new Timestamp(new Date().getTime()))
						.setCompanyId(1)
						.build()
				);
				
    			break;
    			
    		// Delete the computer
			case 6:
				System.out.println("What's the computer id ?");
				
				// Ask the computer id from the user command line
				scanner = new Scanner(System. in);
				input = scanner. nextLine();
				computerId = Integer.valueOf(input);
				
				if(ComputerController.getInstance().deleteById(computerId)) {
					System.out.printf("Successfully deleted computer %d", computerId);
				}
				else {
					System.out.printf("An error happened while trying to delete computer %d", computerId);
				}
				
				break;
				
	    	// Exit the program
    		case 9:
    			System.out.println("Bye");
    			break;
    	}
    }
}
