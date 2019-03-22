package com.excilys.rmomprive.computerdatabase.console.ui.menu;

import java.util.Collection;

import com.excilys.rmomprive.computerdatabase.core.Computer;

public class MenuListComputers extends Menu {

  private static MenuListComputers instance;

  /**
   * Private constructor for singleton.
   */
  private MenuListComputers() {

  }

  @Override
  public void show() {
    Collection<Computer> computers = this.computerService.getAll();

    if (computers != null) {
      System.out.println(computers);
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
