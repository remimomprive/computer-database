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
import fr.excilys.rmomprive.model.Computer.ComputerBuilder;
import fr.excilys.rmomprive.util.Dates;

public class Console 
{
	/**
	 * Read a value from console and returns it
	 * @return The value given by the user
	 */
	private static String readValue() {
		Scanner scanner = new Scanner(System.in);
		return scanner. nextLine();
	}
	
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
			choice = Integer.valueOf(readValue());
			
			// If the choice is not valid
			if (!validMenuOptions.contains(choice))
				System.out.println("Invalid choice");
		} while (!validMenuOptions.contains(choice));
    	
    	return choice;
	}

    public static void main(String[] args) {
    	int choice = askChoice();
    		
    	switch (choice) {
    		// List all computers
			case 1:
				menuListComputers();
	    		break;
	    		
	    	// List all companies
    		case 2:
    			menuListCompanies();
	    		break;
	    		
	    	// Display infos for a computer
			case 3:
				menuDisplayComputerDetails();
    			break;
    			
			// Add a computer
			case 4:
				menuCreateComputer();
    			break;
    			
    		// Delete the computer
			case 6:
				menuDeleteComputer();
				break;
				
	    	// Exit the program
    		case 9:
    			System.out.println("Bye");
    			break;
    	}
    }

	private static void menuDeleteComputer() {
		int computerId;
		System.out.println("What's the computer id ?");
		
		// Ask the computer id from the user command line
		computerId = Integer.valueOf(readValue());
		
		if(ComputerController.getInstance().deleteById(computerId))
			System.out.printf("Successfully deleted computer %d", computerId);
		else
			System.out.printf("An error happened while trying to delete computer %d", computerId);
	}

	private static void menuCreateComputer() {
		System.out.println("What's the computer name ?");
		String name = null;
		
		do {
			name = readValue();
			
			if (name.equals("")) {
				System.out.println("The computer name should not be null");
			}
		} while (name.equals(""));
		
		System.out.println("What's the computer introduction date (YYYY-mm-dd HH:mm:ss) ?");
		String introducedString;
		Timestamp introduced = null;
		
		do {
			introducedString = readValue();
			if (!Dates.isValidTimestamp(introducedString))
				System.out.println("The timestamp format is not valid");
			else
				introduced = Timestamp.valueOf(introducedString);
		} while (!Dates.isValidTimestamp(introducedString));
		
		System.out.println("What's the computer discontinuation date (YYYY-mm-dd HH:mm:ss) ?");
		String discontinuedString;
		Timestamp discontinued = null;
		do {
			do {
				discontinuedString = readValue();
				if (discontinuedString.equals("NULL"))
					discontinued = null;
				else if (!Dates.isValidTimestamp(discontinuedString))
					System.out.println("The timestamp format is not valid");
				else
					discontinued = Timestamp.valueOf(discontinuedString);
			} while (!(Dates.isValidTimestamp(discontinuedString) || discontinuedString != null));
			
			if (!introduced.before(discontinued))
				System.out.println("The discontinued date should be after the intruduction date or NULL");
		} while (!introduced.before(discontinued));
		
		System.out.println("What's the company id ?");
		int companyId = -1;
		
		do {
			String companyIdString = readValue();
			companyId = Integer.valueOf(companyIdString);
			
			if (!CompanyController.getInstance().checkExistenceById(companyId))
				System.out.println("The company does not exist");
		} while (!CompanyController.getInstance().checkExistenceById(companyId));

		Computer computer2 = new ComputerBuilder()
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyId(companyId)
				.build();
		
		Computer createdComputer = ComputerController.getInstance().add(computer2);
		
		if (createdComputer != null)
			System.out.println("Successfullly added " + createdComputer);
		else
			System.out.println("Error creating " + computer2);
	}

	private static void menuDisplayComputerDetails() {
		int computerId;
		System.out.println("What's the computer id ?");
		
		// Ask the computer id from the user command line
		computerId = Integer.valueOf(readValue());
		
		// Retrieve the computer
		Computer computer = ComputerController.getInstance().getById(computerId);
		if (computer != null)
			System.out.println(computer);
		else
			System.out.println("This computer does not exist");
	}

	private static void menuListCompanies() {
		Collection<Company> companies = CompanyController.getInstance().getAll();
		System.out.println(companies);
	}

	private static void menuListComputers() {
		Collection<Computer> computers = ComputerController.getInstance().getAll();
		System.out.println(computers);
	}
}