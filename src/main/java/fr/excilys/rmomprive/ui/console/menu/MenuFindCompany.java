package fr.excilys.rmomprive.ui.console.menu;

import java.util.List;
import fr.excilys.rmomprive.exception.DaoException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.util.Menus;

public class MenuFindCompany extends Menu {

  private static MenuFindCompany instance;

  /**
   * Private constructor for singleton.
   */
  private MenuFindCompany() {

  }

  @Override
  public void show() {
    // Ask the company name from the user command line
    System.out.println("What's the company name ?");
    String companyName = Menus.readString();

    try {
      List<Company> companies = this.companyService.getByName(companyName);
      System.out.println(companies);
    } catch (DaoException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return The instance of MenuDeleteComputer in memory
   */
  public static MenuFindCompany getInstance() {
    if (instance == null) {
      instance = new MenuFindCompany();
    }

    return instance;
  }

}
