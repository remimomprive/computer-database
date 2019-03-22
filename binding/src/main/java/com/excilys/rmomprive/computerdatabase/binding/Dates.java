package com.excilys.rmomprive.computerdatabase.binding;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Dates {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  /**
   * Parse the given date to a Date object.
   *
   * @param dateString The String we want to convert
   * @return The Date output
   * @throws ParseException if the String format is not correct (yyyy-MM-dd)
   */
  public static LocalDate parse(final String dateString) throws DateTimeParseException {
    return LocalDate.parse(dateString, formatter);
  }

  /**
   * Parse a date object for printing.
   *
   * @param date The date object
   * @return The output string
   */
  public static String parse(LocalDate date) {
    if (date != null) {
      return date.format(formatter);
    }

    return "";
  }

  /**
   * Check if the date String format is correct (yy-MM-dd).
   *
   * @param dateString The String we want to validate
   * @return true if the date String format is correct, false if not
   */
  public static boolean isValidDate(final String dateString) {
    try {
      LocalDate.parse(dateString, formatter);
    } catch (DateTimeParseException e) {
      return false;
    }

    return true;
  }
}
