package com.excilys.rmomprive.computerdatabase.validation;

import java.time.LocalDate;

import com.excilys.rmomprive.computerdatabase.core.Computer;

public class ComputerValidator {
  /**
   * Checks if the computer entity is valid, does nothing if it is the case, throws an exception if
   * not.
   *
   * @param computer The computer object we want to verify
   * @throws A child of ValidationException if the computer object is not valid
   */
  public static void validate(Computer computer) throws ValidationException {
    validateComputerName(computer.getName());
    validateDatePrecedence(computer.getIntroduced(), computer.getDiscontinued());
    validateEmptyDates(computer.getIntroduced(), computer.getDiscontinued());
  }

  /**
   * Checks if the name is not null or empty
   *
   * @param name The computer name
   * @throws InvalidNameException If the constraint is violated
   */
  private static void validateComputerName(String name) throws ValidationException {
    if (name == null || name.equals("")) {
      throw new ValidationException(ValidationException.ValidationType.INVALID_NAME);
    }
  }

  /**
   * Checks if discontinued is before discontinued
   *
   * @param introduced   The first date
   * @param discontinued The second date
   * @throws InvalidDatePrecedenceException If the constraint is violated
   */
  private static void validateDatePrecedence(LocalDate introduced, LocalDate discontinued)
      throws ValidationException {
    if (introduced != null && discontinued != null && introduced.isAfter(discontinued)) {
      throw new ValidationException(ValidationException.ValidationType.INVALID_DATE_PRECEDENCE);
    }
  }

  /**
   * Checks if introduced is not null when discontinued is not null
   *
   * @param introduced   The first date
   * @param discontinued T
   * @throws EmptyDateException If the constraint is violated
   */
  private static void validateEmptyDates(LocalDate introduced, LocalDate discontinued)
      throws ValidationException {
    if (introduced == null && discontinued != null) {
      throw new ValidationException(ValidationException.ValidationType.EMPTY_DATE);
    }
  }
}
