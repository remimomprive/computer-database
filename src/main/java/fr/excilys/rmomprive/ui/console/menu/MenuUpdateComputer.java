package fr.excilys.rmomprive.ui.console.menu;

import java.sql.SQLException;

import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.ComputerService;
import fr.excilys.rmomprive.util.Menus;

public class MenuUpdateComputer extends MenuComputerForm {

  private static MenuUpdateComputer instance;

  /**
   * Private constructor for singleton.
   */
  private MenuUpdateComputer() {

  }

  @Override
  public void show() {
    int computerId;
    System.out.println("What's the computer id ?");

    // Ask the computer id from the user command line
    computerId = Integer.valueOf(Menus.readString());

    Computer computer = form();
    computer.setId(computerId);

    try {
      Computer createdComputer = ComputerService.getInstance().update(computer);

      if (createdComputer != null) {
        System.out.println("Successfullly updated " + createdComputer);
      } else {
        System.out.println("Error updating " + computer);
      }
    } catch (SQLException e) {
      e.printStackTrace();
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
