package fr.excilys.rmomprive.ui.console.menu;

import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.ComputerService;

public class MenuCreateComputer extends MenuComputerForm {
	private static MenuCreateComputer instance;
	
	@Override
	public void show() {
		Computer computer = form();
		
		Computer createdComputer = ComputerService.getInstance().add(computer);
		
		if (createdComputer != null)
			System.out.println("Successfullly added " + createdComputer);
		else
			System.out.println("Error creating " + computer);
	}
	
	public static MenuCreateComputer getInstance() {
		if (instance == null)
			instance = new MenuCreateComputer();
		
		return instance;
	}
}
