package fr.excilys.rmomprive.ui.console.menu;

import java.sql.SQLException;
import java.util.Optional;

import fr.excilys.rmomprive.model.ComputerDetails;
import fr.excilys.rmomprive.service.ComputerService;
import fr.excilys.rmomprive.util.Menus;

public class MenuDisplayComputerDetails extends Menu {

	private static MenuDisplayComputerDetails instance;
	
	@Override
	public void show() {
		// Ask the computer id from the user command line
		System.out.println("What's the computer id ?");
		int computerId = Menus.readInteger("The computer id should be an integer");
		
		// Retrieve the computer details
		try {
			Optional<ComputerDetails> computerDetails = ComputerService.getInstance().getDetailsByComputerId(computerId);
			
			if (computerDetails.isPresent())
				System.out.println(computerDetails);
			else
				getLogger().error("This computer does not exist\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static MenuDisplayComputerDetails getInstance() {
		if (instance == null)
			instance = new MenuDisplayComputerDetails();
		
		return instance;
	}

}
