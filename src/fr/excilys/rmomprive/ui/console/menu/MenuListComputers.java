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
		int pageSize = Integer.valueOf(readValue());
		
		System.out.println("What is the page id ?");
		int pageId = Integer.valueOf(readValue());
		
		Page<Computer> computers = null;
		
		try {
			computers = ComputerService.getInstance().getPage(pageId, pageSize);
		} catch (InvalidPageIdException e) {
			e.printStackTrace();
		} catch (InvalidPageSizeException e) {
			e.printStackTrace();
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
