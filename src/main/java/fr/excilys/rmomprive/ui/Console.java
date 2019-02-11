package fr.excilys.rmomprive.ui;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import fr.excilys.rmomprive.controller.CompanyController;
import fr.excilys.rmomprive.controller.ComputerController;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;

public class Console 
{
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
	
	private static int askChoice() {
    	List<Integer> validMenuOptions = Arrays.asList(1, 2, 3, 4, 5, 6, 9);
    	int choice = 0;
    	
    	do {
    		displayMenu();
    		
			Scanner scanner = new Scanner(System. in);
			String input = scanner. nextLine();
			scanner.close();
			choice = Integer.valueOf(input);
			
			if (!validMenuOptions.contains(choice))
				System.out.println("Invalid choice");
		} while (!validMenuOptions.contains(choice));
    	
    	return choice;
	}
	
    public static void main(String[] args)
    {
    	int choice = askChoice();
    		
    	switch (choice) {
			case 1:
				Collection<Computer> computers = ComputerController.getInstance().getAll();
	    		System.out.println(computers);
	    		break;
    		case 2:
	    		Collection<Company> companies = CompanyController.getInstance().getAll();
	    		System.out.println(companies);
	    		break;
    		case 9:
    			System.out.println("Bye");
    			break;
    	}
    }
}
