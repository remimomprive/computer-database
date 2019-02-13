package fr.excilys.rmomprive.ui.console.menu;

import java.util.Collection;

import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.persistence.InvalidPageSizeException;
import fr.excilys.rmomprive.service.ComputerService;
import fr.excilys.rmomprive.service.Page;

public class MenuListComputers extends IMenu {

	private static MenuListComputers instance;
	
	@Override
	public void show() {
		System.out.println("What is the page size ?");
		int pageSize = -1;
		do {
			try {
				pageSize = Integer.valueOf(readValue());
			} catch(NumberFormatException e) {
				System.out.println("The page size should be an int");
			}
		} while (pageSize < 1);
		
		System.out.println("What is the page id ?");
		int pageId = -1;
		do {
			try {
				pageId = Integer.valueOf(readValue());
			} catch(NumberFormatException e) {
				System.out.println("The page id should be an int");
			}
		} while (pageId < 1);
		
		Page<Computer> computers = null;
		
		try {
			computers = ComputerService.getInstance().getPage(pageId, pageSize);
		} catch (InvalidPageIdException | InvalidPageSizeException e) {
			System.out.println("The page parameters are not valid");
		}
		
		if (computers != null)
			System.out.println(computers);
	}
	
	public static MenuListComputers getInstance() {
		if (instance == null)
			instance = new MenuListComputers();
		
		return instance;
	}

}
