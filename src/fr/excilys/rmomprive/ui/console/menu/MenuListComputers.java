package fr.excilys.rmomprive.ui.console.menu;

import java.util.Collection;

import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.ComputerService;

public class MenuListComputers extends IMenu {

	private static MenuListComputers instance;
	
	@Override
	public void show() {
		Collection<Computer> computers = ComputerService.getInstance().getAll();
		System.out.println(computers);
	}
	
	public static MenuListComputers getInstance() {
		if (instance == null)
			instance = new MenuListComputers();
		
		return instance;
	}

}
