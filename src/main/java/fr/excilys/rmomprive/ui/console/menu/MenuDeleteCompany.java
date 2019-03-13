package fr.excilys.rmomprive.ui.console.menu;

import org.springframework.dao.DataAccessException;

import fr.excilys.rmomprive.util.Menus;

public class MenuDeleteCompany extends Menu {

  private static MenuDeleteCompany instance;

  /**
   * Private constructor for singleton.
   */
  private MenuDeleteCompany() {

  }

  @Override
  public void show() {
    // Ask the company id from the user command line
    System.out.println("What's the company id ?");
    int companyId = Menus.readInteger("The company id should be an integer").get();

    try {
      if (this.companyService.deleteById(companyId)) {
        getLogger().info("Successfully deleted company {} and its computers\n", companyId);
      } else {
        getLogger().error("An error happened while trying to delete company {}\n", companyId);
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return The instance of MenuDeleteCompany in memory
   */
  public static MenuDeleteCompany getInstance() {
    if (instance == null) {
      instance = new MenuDeleteCompany();
    }

    return instance;
  }

}
