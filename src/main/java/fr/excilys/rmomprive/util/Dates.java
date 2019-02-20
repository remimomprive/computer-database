package fr.excilys.rmomprive.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Dates {
  /**
   * Parse the given date to a Date object.
   * @param dateString The String we want to convert
   * @return The Date output
   * @throws ParseException if the String format is not correct (yy-MM-dd)
   */
  public static Date parse(final String dateString) throws ParseException {
    DateFormat format =
        new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH);
    return format.parse(dateString);
  }

  /**
   * Check if the date String format is correct (yy-MM-dd).
   * @param dateString The String we want to validate
   * @return true if the date String format is correct, false if not
   */
  public static boolean isValidDate(final String dateString) {
    try {
      DateFormat format =
          new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH);
      format.parse(dateString);
    } catch (ParseException e) {
      return false;
    }

    return true;
  }
}
