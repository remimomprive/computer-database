package fr.excilys.rmomprive.ui.console.menu;

import java.sql.Timestamp;

import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.model.Computer.ComputerBuilder;
import fr.excilys.rmomprive.service.CompanyService;
import fr.excilys.rmomprive.service.ComputerService;
import fr.excilys.rmomprive.util.Dates;

public class MenuCreateComputer extends IMenu {

	private static MenuCreateComputer instance;
	
	@Override
	public void show() {
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
		
		Computer createdComputer = ComputerService.getInstance().add(computer);
		
		if (createdComputer != null)
			System.out.println("Successfullly added " + createdComputer);
		else
			System.out.println("Error creating " + computer);
	}
	
	private String askComputerName() {
		System.out.println("What's the computer name ?");
		String name = null;
		
		do {
			name = readValue();
			
			if (name.equals("")) {
				System.out.println("The computer name should not be null");
			}
		} while (name.equals(""));
		return name;
	}

	private Timestamp askTimestamp(boolean nullable) {
		String timestampString;
		Timestamp timestamp = null;
		
		do {
			timestampString = readValue();
			if (nullable && timestampString.contentEquals("NULL"))
				timestamp = null;
			else if (!Dates.isValidTimestamp(timestampString))
				System.out.println("The timestamp format is not valid");
			else
				timestamp = Timestamp.valueOf(timestampString);
		} while (!(Dates.isValidTimestamp(timestampString) || (nullable && timestampString != null)));
		
		return timestamp;
	}
	
	private Timestamp askTimestamp() {
		return askTimestamp(false);
	}
	
	private int askCompanyId() {
		System.out.println("What's the company id ?");
		int companyId = -1;
		
		do {
			String companyIdString = readValue();
			companyId = Integer.valueOf(companyIdString);
			
			if (!CompanyService.getInstance().checkExistenceById(companyId))
				System.out.println("The company does not exist");
		} while (!CompanyService.getInstance().checkExistenceById(companyId));
		return companyId;
	}
	
	public static MenuCreateComputer getInstance() {
		if (instance == null)
			instance = new MenuCreateComputer();
		
		return instance;
	}

}
