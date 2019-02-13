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
			getLogger().info("Successfullly added " + createdComputer + "\n");
		// Else, an error happened
		else
			getLogger().error("Error creating " + computer + "\n");
	}
	
	public static MenuCreateComputer getInstance() {
		if (instance == null)
			instance = new MenuCreateComputer();
		
		return instance;
	}
}
