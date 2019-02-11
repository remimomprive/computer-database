package fr.excilys.rmomprive.model;

import java.sql.Timestamp;

public class ComputerBuilder {
	private int id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private int companyId;
	
	public Computer build() {
		Computer computer = new Computer();
		
		computer.setId(this.id);
		computer.setName(this.name);
		computer.setIntroduced(this.introduced);
		computer.setDiscontinued(this.discontinued);
		computer.setCompanyId(this.companyId);
		
		return computer;
	}

	public ComputerBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public ComputerBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public ComputerBuilder setIntroduced(Timestamp timestamp) {
		this.introduced = timestamp;
		return this;
	}

	public ComputerBuilder setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
		return this;
	}

	public ComputerBuilder setCompanyId(int companyId) {
		this.companyId = companyId;
		return this;
	}
}
