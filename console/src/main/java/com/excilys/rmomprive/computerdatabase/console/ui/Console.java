package com.excilys.rmomprive.computerdatabase.console.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.rmomprive.computerdatabase.console.ui.menu.MenuChoice;
import com.excilys.rmomprive.computerdatabase.console.ui.menu.MenuCreateComputer;
import com.excilys.rmomprive.computerdatabase.console.ui.menu.MenuDeleteCompany;
import com.excilys.rmomprive.computerdatabase.console.ui.menu.MenuDeleteComputer;
import com.excilys.rmomprive.computerdatabase.console.ui.menu.MenuDisplayComputerDetails;
import com.excilys.rmomprive.computerdatabase.console.ui.menu.MenuFindCompany;
import com.excilys.rmomprive.computerdatabase.console.ui.menu.MenuListCompanies;
import com.excilys.rmomprive.computerdatabase.console.ui.menu.MenuListComputers;
import com.excilys.rmomprive.computerdatabase.console.ui.menu.MenuListPageComputers;
import com.excilys.rmomprive.computerdatabase.console.ui.menu.MenuUpdateComputer;
import com.excilys.rmomprive.computerdatabase.console.ui.menu.Menus;

public class Console {
  private static Logger logger;

  static {
    Console.logger = LoggerFactory.getLogger(Console.class);
  }

  /**
   * Displays the choices.
   */
  private static void displayMenu() {
    System.out.println("=====");
    System.out.println("COMPUTER DATABASE MENU");
    System.out.println("-----");
    System.out.println("Choose an action");
    System.out.println("=====");
    System.out.printf("%d - List computers\n", MenuChoice.LIST_COMPUTERS.getId());
    System.out.printf("%d - List a computer page\n", MenuChoice.LIST_COMPUTER_PAGES.getId());
    System.out.printf("%d - List companies\n", MenuChoice.LIST_COMPANIES.getId());
    System.out.printf(
        "%d - Show computer details (the detailed information of only one computer)\n",
        MenuChoice.SHOW_COMPUTER_DETAILS.getId());
    System.out.printf("%d - Create a computer\n", MenuChoice.CREATE_COMPUTER.getId());
    System.out.printf("%d - Update a computer\n", MenuChoice.UPDATE_COMPUTER.getId());
    System.out.printf("%d - Delete a computer\n", MenuChoice.DELETE_COMPUTER.getId());
    System.out.printf("%d - Find a company by name\n", MenuChoice.FIND_COMPANY_BY_NAME.getId());
    System.out.printf("%d - Delete a company by its id\n", MenuChoice.DELETE_COMPANY_BY_ID.getId());
    System.out.printf("%d - Exit\n", MenuChoice.EXIT.getId());
    System.out.println("=====");
  }

  /**
   * Asks a choice until its value is correct.
   *
   * @return The value of the choice
   */
  private static MenuChoice askChoice() {
    MenuChoice choice = null;
    int choiceInt;

    // Ask the choice until the user selects a valid choice
    do {
      // Display the menu
      displayMenu();

      // Ask the choice from the user
      choiceInt = Menus.readInteger("The choice should be an integer").get();
      choice = MenuChoice.getById(choiceInt);

      // If the choice is not valid
      if (choice == null) {
        logger.error("Invalid choice");
      }
    } while (choice == null);

    return choice;
  }

  /**
   * Displays the menu.
   */
  public static void run() {
    MenuChoice choice = null;

    do {
      choice = askChoice();

      switch (choice) {
      // List all computers
      case LIST_COMPUTERS:
        MenuListComputers.getInstance().show();
        break;

      case LIST_COMPUTER_PAGES:
        MenuListPageComputers.getInstance().show();
        break;

      // List all companies
      case LIST_COMPANIES:
        MenuListCompanies.getInstance().show();
        break;

      // Display infos for a computer
      case SHOW_COMPUTER_DETAILS:
        MenuDisplayComputerDetails.getInstance().show();
        break;

      // Add a computer
      case CREATE_COMPUTER:
        MenuCreateComputer.getInstance().show();
        break;

      // Add a computer
      case UPDATE_COMPUTER:
        MenuUpdateComputer.getInstance().show();
        break;

      // Delete the computer
      case DELETE_COMPUTER:
        MenuDeleteComputer.getInstance().show();
        break;

      // Find a company by its name
      case FIND_COMPANY_BY_NAME:
        MenuFindCompany.getInstance().show();
        break;

      // Delete a company by its company
      case DELETE_COMPANY_BY_ID:
        MenuDeleteCompany.getInstance().show();
        break;

      // Exit the program
      case EXIT:
        System.out.println("Bye");
        break;
      }
    } while (choice != MenuChoice.EXIT);
  }
}
