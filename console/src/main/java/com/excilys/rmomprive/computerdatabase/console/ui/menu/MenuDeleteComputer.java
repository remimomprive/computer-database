package com.excilys.rmomprive.computerdatabase.console.ui.menu;

public class MenuDeleteComputer extends Menu {

  private static MenuDeleteComputer instance;

  /**
   * Private constructor for singleton.
   */
  private MenuDeleteComputer() {

  }

  @Override
  public void show() {
    // Ask the computer id from the user command line
    System.out.println("What's the computer id ?");
    int computerId = Menus.readInteger("The computer id should be an integer").get();

    if (this.companyService.deleteById(computerId)) {
      getLogger().info("Successfully deleted computer {}\n", computerId);
    } else {
      getLogger().error("An error happened while trying to delete computer {}\n", computerId);
    }
  }

  /**
   * @return The instance of MenuDeleteComputer in memory
   */
  public static MenuDeleteComputer getInstance() {
    if (instance == null) {
      instance = new MenuDeleteComputer();
    }

    return instance;
  }

}
