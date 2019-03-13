package fr.excilys.rmomprive.ui.console.menu;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import fr.excilys.rmomprive.model.Company;

public class MenuListCompanies extends Menu {

  private static MenuListCompanies instance;

  /**
   * Private constructor for singleton.
   */
  private MenuListCompanies() {

  }

  @Override
  public void show() {
    try {
      Collection<Company> companies = this.companyService.getAll();
      System.out.println(companies);
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return The instance of MenuListCompanies in memory
   */
  public static MenuListCompanies getInstance() {
    if (instance == null) {
      instance = new MenuListCompanies();
    }

    return instance;
  }

}
