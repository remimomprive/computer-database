package fr.excilys.rmomprive.ui.console.menu;

import java.sql.Timestamp;

import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.model.Computer.ComputerBuilder;
import fr.excilys.rmomprive.service.CompanyService;
import fr.excilys.rmomprive.util.Dates;

public abstract class MenuComputerForm implements IMenu {
	/**
	 * This method displays a form in order to fill a Computer
	 * @return The filled computer
	 */
	protected Computer form() {
		String name = askComputerName();
		
		System.out.println("What's the computer introduction date (YYYY-mm-dd HH:mm:ss) ?");
		Timestamp introduced = askTimestamp();
		
		/// TODO : check the behaviour if discontinued date is null
		System.out.println("What's the computer discontinuation date (YYYY-mm-dd HH:mm:ss) ?");
		Timestamp discontinued = null;
		do { 
			discontinued = askTimestamp(true);
			
			if (!introduced.before(discontinued))
				System.out.println("The discontinued date should be after the intruduction date or NULL");
		} while (!introduced.before(discontinued));
		
		int companyId = askCompanyId();

		Computer computer = new ComputerBuilder()
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyId(companyId)
				.build();
		
		return computer;
	}

	/**
	 * Asks a String corresponding to the computer name
	 * @return The given String
	 */
	private String askComputerName() {
		System.out.println("What's the computer name ?");
		String name = null;
		
		do {
			name = Menus.readString();
			
			if (name.equals(""))
				System.out.println("The computer name should not be null");
		} while (name.equals(""));
		return name;
	}

	/**
	 * Asks the user a timestamp String until its format is valid
	 * @param nullable true if the user can set NULL to return a null Timestamp
	 * @return The String timestamp converted into a Timestamp object
	 */
	private Timestamp askTimestamp(boolean nullable) {
		String timestampString;
		Timestamp timestamp = null;
		
		do {
			// Read a string value from the terminal
			timestampString = Menus.readString();
			
			// If the user sets "NULL" and the value can be nullable
			if (nullable && timestampString.contentEquals("NULL"))
				timestamp = null;
			// If the String format is not valid
			else if (!Dates.isValidTimestamp(timestampString))
				System.out.println("The timestamp format is not valid");
			// Else, the String format is not valid
			else
				timestamp = Timestamp.valueOf(timestampString);
		} while (!(Dates.isValidTimestamp(timestampString) || (nullable && timestampString != null)));
		
		return timestamp;
	}
	
	/**
	 * Ask a timestamp String, preventing the user to set null value
	 * @return The String timestamp converted into a Timestamp object
	 */
	private Timestamp askTimestamp() {
		return askTimestamp(false);
	}
	
	/**
	 * Ask a company id until its value corresponds to an existing company id
	 * @return The given company id
	 */
	private int askCompanyId() {
		System.out.println("What's the company id ?");
		int companyId = -1;
		
		do {
			companyId = Menus.readInteger("The company id should be an integer");
			
			if (!CompanyService.getInstance().checkExistenceById(companyId))
				System.out.println("The company does not exist");
		} while (!CompanyService.getInstance().checkExistenceById(companyId));
		return companyId;
	}
}
