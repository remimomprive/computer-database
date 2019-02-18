package fr.excilys.rmomprive.model;

public class Company {
	private long id;
	private String name;
	
	public Company(final long id, final String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
}
