package fr.excilys.rmomprive.ui.console.menu;

import java.sql.SQLException;
import java.util.Collection;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.ComputerService;

public class MenuListComputers extends Menu {

	private static MenuListComputers instance;
	
	@Override
	public void show() {		
		try {
			Collection<Computer> computers = ComputerService.getInstance().getAll();
			
			if (computers != null)
				System.out.println(computers);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static MenuListComputers getInstance() {
		if (instance == null)
			instance = new MenuListComputers();
		
		return instance;
	}

}
