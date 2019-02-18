package fr.excilys.rmomprive.validation;

import fr.excilys.rmomprive.exception.ValidationException;
import fr.excilys.rmomprive.model.Computer;

public class ComputerValidator {

	public static void validate(Computer computer) throws ValidationException {
		if (computer.getName() == null || computer.getName().equals(""))
			throw new ValidationException();
		if (computer.getIntroduced() != null && computer.getDiscontinued() != null && computer.getIntroduced().after(computer.getDiscontinued()))
			throw new ValidationException();
	}

}
