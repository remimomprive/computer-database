package com.excilys.rmomprive.computerdatabase.console.ui.menu;

import java.util.List;

import com.excilys.rmomprive.computerdatabase.core.Company;

public class MenuFindCompany extends Menu {

  private static MenuFindCompany instance;

  /**
   * Private constructor for singleton.
   */
  private MenuFindCompany() {

  }

  @Override
  public void show() {
    // Ask the company name from the user command line
    System.out.println("What's the company name ?");
    String companyName = Menus.readString();

    List<Company> companies = this.companyService.getByName(companyName);
    System.out.println(companies);
  }

  /**
   * @return The instance of MenuDeleteComputer in memory
   */
  public static MenuFindCompany getInstance() {
    if (instance == null) {
      instance = new MenuFindCompany();
    }

    return instance;
  }

}
