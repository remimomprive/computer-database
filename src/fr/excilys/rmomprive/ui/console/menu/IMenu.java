package fr.excilys.rmomprive.ui.console.menu;

import java.util.Scanner;

public abstract class IMenu {
	public abstract void show();
	
	protected String readValue() {
		Scanner scanner = new Scanner(System.in);
		return scanner. nextLine();
	}
}
