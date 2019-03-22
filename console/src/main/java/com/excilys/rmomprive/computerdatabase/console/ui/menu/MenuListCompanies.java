package com.excilys.rmomprive.computerdatabase.console.ui.menu;

import java.util.Collection;

import com.excilys.rmomprive.computerdatabase.core.Company;

public class MenuListCompanies extends Menu {

  private static MenuListCompanies instance;

  /**
   * Private constructor for singleton.
   */
  private MenuListCompanies() {

  }

  @Override
  public void show() {
    Collection<Company> companies = this.companyService.getAll();
    System.out.println(companies);
  }

  /**
   * @return The instance of MenuListCompanies in memory
   */
  public static MenuListCompanies getInstance() {
    if (instance == null) {
      instance = new MenuListCompanies();
    }

    return instance;
  }

}
