package fr.excilys.rmomprive.ui.console.menu;

import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.ComputerService;

public class MenuUpdateComputer extends MenuComputerForm {

	private static MenuUpdateComputer instance;
	
	@Override
	public void show() {
		int computerId;
		System.out.println("What's the computer id ?");
		
		// Ask the computer id from the user command line
		computerId = Integer.valueOf(readValue());
		
		Computer computer = form();
		computer.setId(computerId);
		
		Computer createdComputer = ComputerService.getInstance().update(computer);
		
		if (createdComputer != null)
			System.out.println("Successfullly added " + createdComputer);
		else
			System.out.println("Error creating " + computer);
	}
	
	public static MenuUpdateComputer getInstance() {
		if (instance == null)
			instance = new MenuUpdateComputer();
		
		return instance;
	}

}
