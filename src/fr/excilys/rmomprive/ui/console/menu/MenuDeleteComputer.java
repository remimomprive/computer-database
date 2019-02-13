package fr.excilys.rmomprive.ui.console.menu;

import fr.excilys.rmomprive.service.ComputerService;

public class MenuDeleteComputer extends IMenu {

	private static MenuDeleteComputer instance;
	
	@Override
	public void show() {
		// Ask the computer id from the user command line
		System.out.println("What's the computer id ?");
		int computerId = Menus.readInteger("The computer id should be an integer");
		
		if(ComputerService.getInstance().deleteById(computerId))
			System.out.printf("Successfully deleted computer %d\n", computerId);
		else
			System.out.printf("An error happened while trying to delete computer %d\n", computerId);
	}
	
	public static MenuDeleteComputer getInstance() {
		if (instance == null)
			instance = new MenuDeleteComputer();
		
		return instance;
	}

}
