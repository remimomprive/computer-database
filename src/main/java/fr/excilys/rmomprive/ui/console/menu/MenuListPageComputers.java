package fr.excilys.rmomprive.ui.console.menu;

import java.sql.SQLException;

import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.ComputerService;
import fr.excilys.rmomprive.service.Page;

public class MenuListPageComputers extends Menu {

	private static MenuListPageComputers instance;
	
	@Override
	public void show() {
		// Ask for a valid integer
		System.out.println("What is the page size ?");
		int pageSize = Menus.readInteger("The page size should be an integer");
		
		// Ask for a valid integer
		System.out.println("What is the page id ?");
		int pageId = Menus.readInteger("The page id should be an int");
		
		try {
			Page<Computer> computers = ComputerService.getInstance().getPage(pageId, pageSize);
			
			if (computers != null)
				System.out.println(computers);
		} catch (InvalidPageIdException | InvalidPageSizeException e) {
			getLogger().error("The page parameters are not valid\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static MenuListPageComputers getInstance() {
		if (instance == null)
			instance = new MenuListPageComputers();
		
		return instance;
	}

}
