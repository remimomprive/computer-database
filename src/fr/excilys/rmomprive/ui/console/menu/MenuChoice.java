package fr.excilys.rmomprive.ui.console.menu;

public enum MenuChoice {
	LIST_COMPUTERS(1),
	LIST_COMPANIES(2),
	SHOW_COMPUTER_DETAILS(3),
	CREATE_COMPUTER(4),
	UPDATE_COMPUTER(5),
	DELETE_COMPUTER(6),
	EXIT(7);
	
	private final int id;
	
	MenuChoice(int id) {
		this.id = id;
	}
	
	public static MenuChoice getById(int id) {
		MenuChoice[] menuChoices = MenuChoice.values();
		int i = 0;
		
		while (i < menuChoices.length) {
			if (menuChoices[i].getId() == id)
				return menuChoices[i];
			else
				i++;
		}
		
		return null;
	}
	
	public final int getId() {
		return this.id;
	}
}
