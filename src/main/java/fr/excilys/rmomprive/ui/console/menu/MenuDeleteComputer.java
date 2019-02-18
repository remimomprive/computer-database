package fr.excilys.rmomprive.ui.console.menu;

import java.sql.SQLException;

import fr.excilys.rmomprive.service.ComputerService;

public class MenuDeleteComputer extends Menu {

	private static MenuDeleteComputer instance;
	
	@Override
	public void show() {
		// Ask the computer id from the user command line
		System.out.println("What's the computer id ?");
		int computerId = Menus.readInteger("The computer id should be an integer");
		
		try {
			if(ComputerService.getInstance().deleteById(computerId))
				getLogger().info("Successfully deleted computer %d\n", computerId);
			else
				getLogger().error("An error happened while trying to delete computer %d\n", computerId);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static MenuDeleteComputer getInstance() {
		if (instance == null)
			instance = new MenuDeleteComputer();
		
		return instance;
	}

}
