package fr.excilys.rmomprive.validation;

import fr.excilys.rmomprive.exception.ValidationException;
import fr.excilys.rmomprive.model.Computer;

public class ComputerValidator {
  /**
   * Checks if the computer entity is valid, does nothing if it is the case, throws an exception if
   * not.
   *
   * @param computer The computer object we want to verify
   * @throws ValidationException if the computer object is not valid
   */
  public static void validate(Computer computer) throws ValidationException {
    if (
    // Empty computer name
    (computer.getName() == null || computer.getName().equals("")) ||
    // Introduced after discontinued
        (computer.getIntroduced() != null && computer.getDiscontinued() != null
            && computer.getIntroduced().after(computer.getDiscontinued()))
        ||
        // Introduced empty but discontinued filled
        (computer.getIntroduced() == null && computer.getDiscontinued() != null)) {
      throw new ValidationException();
    }
  }
}
