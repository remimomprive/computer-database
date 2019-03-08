package fr.excilys.rmomprive.ui.console.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.rmomprive.configuration.AppConfiguration;
import fr.excilys.rmomprive.service.CompanyService;
import fr.excilys.rmomprive.service.ComputerService;
import fr.excilys.rmomprive.ui.console.Console;

public abstract class Menu {
  private Logger logger;
  protected CompanyService companyService;
  protected ComputerService computerService;

  /**
   * Protected constructor for abstract class.
   */
  protected Menu() {
    this.logger = LoggerFactory.getLogger(Console.class);
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    this.companyService = context.getBean(CompanyService.class);
    this.computerService = context.getBean(ComputerService.class);
  }

  /**
   * This method should display the form (with form() method) and add the business logic.
   */
  public abstract void show();

  protected Logger getLogger() {
    return logger;
  }
}
