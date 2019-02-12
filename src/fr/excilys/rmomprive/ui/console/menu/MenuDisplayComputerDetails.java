package fr.excilys.rmomprive.ui.console.menu;

import fr.excilys.rmomprive.model.ComputerDetails;
import fr.excilys.rmomprive.service.ComputerService;

public class MenuDisplayComputerDetails extends IMenu {

	private static MenuDisplayComputerDetails instance;
	
	@Override
	public void show() {
		int computerId;
		System.out.println("What's the computer id ?");
		
		// Ask the computer id from the user command line
		computerId = Integer.valueOf(readValue());
		
		// Retrieve the computer details
		ComputerDetails computerDetails = ComputerService.getInstance().getDetailsByComputerId(computerId);
		if (computerDetails.getComputer() != null)
			System.out.println(computerDetails);
		else
			System.out.println("This computer does not exist");
	}
	
	public static MenuDisplayComputerDetails getInstance() {
		if (instance == null)
			instance = new MenuDisplayComputerDetails();
		
		return instance;
	}

}
