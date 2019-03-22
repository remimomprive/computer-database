package com.excilys.rmomprive.computerdatabase.console.ui.menu;

import com.excilys.rmomprive.computerdatabase.core.Computer;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageIdException;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageSizeException;
import com.excilys.rmomprive.computerdatabase.persistence.Page;

public class MenuListPageComputers extends Menu {

  private static MenuListPageComputers instance;

  /**
   * Private constructor for singleton.
   */
  private MenuListPageComputers() {

  }

  @Override
  public void show() {
    // Ask for a valid integer
    System.out.println("What is the page size ?");
    int pageSize = Menus.readInteger("The page size should be an integer").get();

    // Ask for a valid integer
    System.out.println("What is the page id ?");
    int pageId = Menus.readInteger("The page id should be an int").get();

    try {
      Page<Computer> computers = this.computerService.getPage(pageId, pageSize);

      if (computers != null) {
        System.out.println(computers);
      }
    } catch (InvalidPageIdException | InvalidPageSizeException e) {
      getLogger().error("The page parameters are not valid\n");
    }
  }

  /**
   * @return The instance of MenuUpdateComputer in memory
   */
  public static MenuListPageComputers getInstance() {
    if (instance == null) {
      instance = new MenuListPageComputers();
    }

    return instance;
  }

}
