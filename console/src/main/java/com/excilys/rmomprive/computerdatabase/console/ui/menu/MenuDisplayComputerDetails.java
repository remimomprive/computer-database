package com.excilys.rmomprive.computerdatabase.console.ui.menu;

import java.util.Optional;

import com.excilys.rmomprive.computerdatabase.core.ComputerDetails;

public class MenuDisplayComputerDetails extends Menu {

  private static MenuDisplayComputerDetails instance;

  /**
   * Private constructor for singleton.
   */
  private MenuDisplayComputerDetails() {

  }

  @Override
  public void show() {
    // Ask the computer id from the user command line
    System.out.println("What's the computer id ?");
    int computerId = Menus.readInteger("The computer id should be an integer").get();

    // Retrieve the computer details
    Optional<ComputerDetails> computerDetails = this.computerService.getDetailsByComputerId(computerId);

    if (computerDetails.isPresent()) {
      System.out.println(computerDetails);
    } else {
      getLogger().error("This computer does not exist\n");
    }
  }

  /**
   * @return The instance of MenuDisplayComputerDetails in memory
   */
  public static MenuDisplayComputerDetails getInstance() {
    if (instance == null) {
      instance = new MenuDisplayComputerDetails();
    }

    return instance;
  }

}
