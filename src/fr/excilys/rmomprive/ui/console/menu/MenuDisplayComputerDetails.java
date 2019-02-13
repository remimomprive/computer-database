package fr.excilys.rmomprive.ui.console.menu;

import fr.excilys.rmomprive.exception.EntityNotFoundException;
import fr.excilys.rmomprive.model.ComputerDetails;
import fr.excilys.rmomprive.service.ComputerService;

public class MenuDisplayComputerDetails extends Menu {

	private static MenuDisplayComputerDetails instance;
	
	@Override
	public void show() {
		// Ask the computer id from the user command line
		System.out.println("What's the computer id ?");
		int computerId = Menus.readInteger("The computer id should be an integer");
		
		// Retrieve the computer details
		ComputerDetails computerDetails = null;
		
		try {
			computerDetails = ComputerService.getInstance().getDetailsByComputerId(computerId);
		} catch (EntityNotFoundException e) {
			getLogger().error("This computer does not exist\n");
		}
		
		if (computerDetails != null)
			System.out.println(computerDetails);
	}
	
	public static MenuDisplayComputerDetails getInstance() {
		if (instance == null)
			instance = new MenuDisplayComputerDetails();
		
		return instance;
	}

}
