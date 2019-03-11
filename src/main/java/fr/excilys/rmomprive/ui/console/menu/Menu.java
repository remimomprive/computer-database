package fr.excilys.rmomprive.ui.console.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.rmomprive.configuration.AppConfiguration;
import fr.excilys.rmomprive.service.ICompanyService;
import fr.excilys.rmomprive.service.IComputerService;
import fr.excilys.rmomprive.ui.console.Console;

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
