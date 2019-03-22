package com.excilys.rmomprive.computerdatabase.console.ui.menu;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.rmomprive.computerdatabase.binding.Dates;
import com.excilys.rmomprive.computerdatabase.console.configuration.AppConfiguration;
import com.excilys.rmomprive.computerdatabase.service.CompanyService;

public class Menus {
  private static Logger logger;
  private static CompanyService companyService;

  static {
    Menus.logger = LoggerFactory.getLogger(Menus.class);
    
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    Menus.companyService =  context.getBean(CompanyService.class);
  }

  /**
   * Reads a String value from the command line.
   * @return The given String
   */
  public static String readString() {
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  /***
   * This methods asks the user to put an integer into the console until a
   * valid integer is given or a null value is given.
   * @param nullable true if the integer can be null
   * @param message the integer String value
   * @return The given integer
   */
  public static Optional<Integer> readInteger(boolean nullable, String message) {
    boolean validValue = false;
    String readString = null;

    do {
      readString = readString();

      if (!readString.equals("")) {
        try {
          return Optional.of(Integer.valueOf(readString));
        } catch (NumberFormatException e) {
          System.out.println(message);
        }
      } else if (nullable) {
        return Optional.empty();
      }
    } while (!validValue);

    return Optional.empty();
  }

  /**
   * This methods asks the user to put an integer into the console until a
   * valid integer is given.
   * @param message the integer String value
   * @return The given integer
   */
  public static Optional<Integer> readInteger(String message) {
    return readInteger(false, message);
  }

  /**
   * Asks a String corresponding to the computer name.
   * @return The given String
   */
  public static String readComputerName() {
    System.out.println("What's the computer name ?");
    String name = null;

    do {
      name = Menus.readString();

      if (name.equals("")) {
        logger.error("The computer name should not be null\n");
      }
    } while (name.equals(""));

    return name;
  }

  /**
   * Asks the user a date String until its format is valid.
   * @param nullable true if the user can set NULL to return a null Date
   * @return The String date converted into a Date object
   */
  public static Optional<LocalDate> readDate(boolean nullable) {
    String dateString;

    do {
      // Read a string value from the terminal
      dateString = Menus.readString();

      // If the user sets null value and the value can be nullable
      if (nullable && dateString.equals("")) {
        return Optional.empty();
      } else if (!Dates.isValidDate(dateString)) { // If the String format is not valid
        logger.error("The timestamp format is not valid\n");
      } else { // Else, the String format is not valid
        try {
          return Optional.of(Dates.parse(dateString));
        } catch (DateTimeParseException e) {
          e.printStackTrace();
        }
      }
    } while (!(Dates.isValidDate(dateString) || (nullable && dateString.equals(""))));

    return Optional.empty();
  }

  /**
   * Ask a date String, preventing the user to set null value.
   * @return The String date converted into a Date object
   */
  public static Optional<LocalDate> readDate() {
    return Menus.readDate(false);
  }

  /**
   * Ask a company id until its value corresponds to an existing company id.
   * @return The given company id
   * @throws SQLException
   */
  public static Optional<Integer> readCompanyId() {
    System.out.println("What's the company id ?");
    Optional<Integer> companyId = Optional.empty();

    do {
      companyId = Menus.readInteger(true, "The company id should be an integer");

      if (companyId.isPresent()
          && !companyService.checkExistenceById(companyId.get())) {
        logger.error("The company does not exist\n");
      }
    } while (companyId.isPresent()
        && !companyService.checkExistenceById(companyId.get()));

    return companyId;
  }
}
