package fr.excilys.rmomprive.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Dates {
  private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

  /**
   * Parse the given date to a Date object.
   * 
   * @param dateString The String we want to convert
   * @return The Date output
   * @throws ParseException if the String format is not correct (yyyy-MM-dd)
   */
  public static Date parse(final String dateString) throws ParseException {
    return format.parse(dateString);
  }

  /**
   * Parse a date object for printing.
   *
   * @param date The date object
   * @return The output string
   */
  public static String parse(Date date) {
    if (date != null) {
      return format.format(date);
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
      format.parse(dateString);
    } catch (ParseException e) {
      return false;
    }

    return true;
  }
}
