package fr.excilys.rmomprive.ui.console.menu;

import java.sql.SQLException;
import java.util.Optional;

import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.ComputerService;

public class MenuCreateComputer extends MenuComputerForm {
	private static MenuCreateComputer instance;
	
	@Override
	public void show() {
		// Asks the user to fill a computer
		Computer computer = form();
		
		// Insert the computer into the database
		try {
			Optional<Computer> createdComputer = ComputerService.getInstance().add(computer);
					
			// If the computer was inserted successfully
			if (createdComputer.isPresent())
				getLogger().info("Successfullly added {} \n");
			// Else, an error happened
			else
				getLogger().error("Error creating {} \n", computer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static MenuCreateComputer getInstance() {
		if (instance == null)
			instance = new MenuCreateComputer();
		
		return instance;
	}
}
