package fr.excilys.rmomprive.model;

public class Company implements Entity {
	private int id;
	private String name;
	
	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
}
