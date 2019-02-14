package fr.excilys.rmomprive.ui.console.menu;

import java.sql.Timestamp;

import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.model.Computer.ComputerBuilder;

public abstract class MenuComputerForm extends Menu {
	/**
	 * This method displays a form in order to fill a Computer
	 * @return The filled computer
	 */
	protected Computer form() {
		String name = Menus.readComputerName();
		
		System.out.println("What's the computer introduction date (YYYY-mm-dd HH:mm:ss) or null?");
		Timestamp introduced = Menus.readTimestamp(true);
		
		Timestamp discontinued = null;
		if (introduced != null) {
			System.out.println("What's the computer discontinuation date (YYYY-mm-dd HH:mm:ss) or null?");
			boolean validDiscontinuedDate = false;
			
			do { 
				discontinued = Menus.readTimestamp(true);
				validDiscontinuedDate = (discontinued != null) ? introduced.before(discontinued) : true;
				
				if (!validDiscontinuedDate)
					getLogger().error("The discontinued date should be after the intruduction date or NULL\n");
			} while (!validDiscontinuedDate);
		}
		
		int companyId = Menus.readCompanyId();

		Computer computer = new ComputerBuilder()
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyId(companyId)
				.build();
		
		return computer;
	}
}
