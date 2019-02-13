package fr.excilys.rmomprive.ui.console.menu;

import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.ComputerService;
import fr.excilys.rmomprive.service.Page;

public class MenuListPageComputers extends IMenu {

	private static MenuListPageComputers instance;
	
	@Override
	public void show() {
		// Ask for a valid integer
		System.out.println("What is the page size ?");
		int pageSize = Menus.readInteger("The page size should be an integer");
		
		// Ask for a valid integer
		System.out.println("What is the page id ?");
		int pageId = Menus.readInteger("The page id should be an int");
		
		Page<Computer> computers = null;
		
		try {
			computers = ComputerService.getInstance().getPage(pageId, pageSize);
		} catch (InvalidPageIdException | InvalidPageSizeException e) {
			System.out.println("The page parameters are not valid");
		}
		
		if (computers != null)
			System.out.println(computers);
	}

	public static MenuListPageComputers getInstance() {
		if (instance == null)
			instance = new MenuListPageComputers();
		
		return instance;
	}

}
