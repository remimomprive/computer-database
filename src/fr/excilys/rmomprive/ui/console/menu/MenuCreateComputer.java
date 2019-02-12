package fr.excilys.rmomprive.ui.console.menu;

import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.ComputerService;

public class MenuCreateComputer extends MenuComputerForm {
	private static MenuCreateComputer instance;
	
	@Override
	public void show() {
		// Asks the user to fill a computer
		Computer computer = form();
		
		// Insert the computer into the database
		Computer createdComputer = ComputerService.getInstance().add(computer);
		
		// If the computer was inserted successfully
		if (createdComputer != null)
			System.out.println("Successfullly added " + createdComputer);
		// Else, an error happened
		else
			System.out.println("Error creating " + computer);
	}
	
	public static MenuCreateComputer getInstance() {
		if (instance == null)
			instance = new MenuCreateComputer();
		
		return instance;
	}
}
