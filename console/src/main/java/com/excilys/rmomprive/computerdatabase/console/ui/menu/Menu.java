package com.excilys.rmomprive.computerdatabase.console.ui.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.rmomprive.computerdatabase.console.configuration.AppConfiguration;
import com.excilys.rmomprive.computerdatabase.console.ui.Console;
import com.excilys.rmomprive.computerdatabase.service.ICompanyService;
import com.excilys.rmomprive.computerdatabase.service.IComputerService;

public abstract class Menu {
  private final Logger LOGGER;
  protected ICompanyService companyService;
  protected IComputerService computerService;

  /**
   * Protected constructor for abstract class.
   */
  protected Menu() {
    this.LOGGER = LoggerFactory.getLogger(Console.class);
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    this.companyService = context.getBean(ICompanyService.class);
    this.computerService = context.getBean(IComputerService.class);
  }

  /**
   * This method should display the form (with form() method) and add the business logic.
   */
  public abstract void show();

  protected Logger getLogger() {
    return LOGGER;
  }
}
