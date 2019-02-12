package fr.excilys.rmomprive.ui.console.menu;

import java.util.Collection;

import fr.excilys.rmomprive.controller.CompanyController;
import fr.excilys.rmomprive.model.Company;

public class MenuListCompanies extends IMenu {

	private static MenuListCompanies instance;
	
	@Override
	public void show() {
		Collection<Company> companies = CompanyController.getInstance().getAll();
		System.out.println(companies);
	}
	
	public static MenuListCompanies getInstance() {
		if (instance == null)
			instance = new MenuListCompanies();
		
		return instance;
	}

}
