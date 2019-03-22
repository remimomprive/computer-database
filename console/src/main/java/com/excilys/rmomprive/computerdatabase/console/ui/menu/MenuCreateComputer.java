package com.excilys.rmomprive.computerdatabase.console.ui.menu;

import java.util.Optional;

import org.springframework.dao.DataAccessException;

import com.excilys.rmomprive.computerdatabase.core.Computer;


public class MenuCreateComputer extends MenuComputerForm {
  private static MenuCreateComputer instance;

  /**
   * Private constructor for singleton.
   */
  private MenuCreateComputer() {

  }

  @Override
  public void show() {
    // Asks the user to fill a computer
    Computer computer = form();

    // Insert the computer into the database
    try {
      Optional<Computer> createdComputer = this.computerService.add(computer);

      // If the computer was inserted successfully
      if (createdComputer.isPresent()) {
        getLogger().info("Successfullly added {}\n", createdComputer.get());
      } else { // Else, an error happened
        getLogger().error("Error creating {}\n", computer);
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return The instance of MenuCreateComputer in memory
   */
  public static MenuCreateComputer getInstance() {
    if (instance == null) {
      instance = new MenuCreateComputer();
    }

    return instance;
  }
}
