package fr.excilys.rmomprive.ui.console.menu;

import fr.excilys.rmomprive.controller.ComputerController;

public class MenuDeleteComputer extends IMenu {

	private static MenuDeleteComputer instance;
	
	@Override
	public void show() {
		int computerId;
		System.out.println("What's the computer id ?");
		
		// Ask the computer id from the user command line
		computerId = Integer.valueOf(readValue());
		
		if(ComputerController.getInstance().deleteById(computerId))
			System.out.printf("Successfully deleted computer %d", computerId);
		else
			System.out.printf("An error happened while trying to delete computer %d", computerId);
	}
	
	public static MenuDeleteComputer getInstance() {
		if (instance == null)
			instance = new MenuDeleteComputer();
		
		return instance;
	}

}
