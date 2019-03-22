package com.excilys.rmomprive.computerdatabase.console.ui.menu;

import com.excilys.rmomprive.computerdatabase.core.Computer;

public class MenuUpdateComputer extends MenuComputerForm {
  private static MenuUpdateComputer instance;

  @Override
  public void show() {
    int computerId;
    System.out.println("What's the computer id ?");

    // Ask the computer id from the user command line
    computerId = Integer.valueOf(Menus.readString());

    Computer computer = form();
    computer.setId(computerId);

    Computer createdComputer = this.computerService.update(computer);

    if (createdComputer != null) {
      System.out.println("Successfullly updated " + createdComputer);
    } else {
      System.out.println("Error updating " + computer);
    }
  }

  /**
   * @return The instance of MenuUpdateComputer in memory
   */
  public static MenuUpdateComputer getInstance() {
    if (instance == null) {
      instance = new MenuUpdateComputer();
    }

    return instance;
  }

}
