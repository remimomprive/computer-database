package fr.excilys.rmomprive.ui.console.menu;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import fr.excilys.rmomprive.model.Computer;

public class MenuListComputers extends Menu {

  private static MenuListComputers instance;

  /**
   * Private constructor for singleton.
   */
  private MenuListComputers() {

  }

  @Override
  public void show() {
    try {
      Collection<Computer> computers = this.computerService.getAll();

      if (computers != null) {
        System.out.println(computers);
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return The instance of MenuListComputers in memory
   */
  public static MenuListComputers getInstance() {
    if (instance == null) {
      instance = new MenuListComputers();
    }

    return instance;
  }

}
