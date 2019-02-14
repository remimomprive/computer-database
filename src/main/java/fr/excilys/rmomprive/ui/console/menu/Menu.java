package fr.excilys.rmomprive.ui.console.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.rmomprive.ui.console.Console;

public abstract class Menu {
	private Logger logger;
	
	public Menu() {
		this.logger = LoggerFactory.getLogger(Console.class);
	}
	
	/**
	 * This method should display the form (with form() method) and add the business logic
	 */
	public abstract void show();

	protected Logger getLogger() {
		return logger;
	}
}
