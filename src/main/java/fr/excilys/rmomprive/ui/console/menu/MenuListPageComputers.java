package fr.excilys.rmomprive.ui.console.menu;

import org.springframework.dao.DataAccessException;

import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.pagination.Page;
import fr.excilys.rmomprive.util.Menus;

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
    } catch (DataAccessException e) {
      e.printStackTrace();
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
