package fr.excilys.rmomprive.ui.console.menu;

import java.sql.SQLException;
import java.util.Collection;

import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.service.CompanyService;

public class MenuListCompanies extends Menu {

	private static MenuListCompanies instance;
	
	@Override
	public void show() {
		try {
			Collection<Company> companies = CompanyService.getInstance().getAll();
			System.out.println(companies);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static MenuListCompanies getInstance() {
		if (instance == null)
			instance = new MenuListCompanies();
		
		return instance;
	}

}
