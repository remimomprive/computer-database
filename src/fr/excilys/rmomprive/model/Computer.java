package fr.excilys.rmomprive.model;

import java.sql.Timestamp;

public class Computer implements Entity {
	private int id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private int companyId;
	
	private Computer() {
		
	}
	
	public Computer(int id, String name, Timestamp introduced, Timestamp discontinued, int companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}
	
	public Timestamp getIntroduced() {
		return introduced;
	}
	
	public Timestamp getDiscontinued() {
		return discontinued;
	}
	
	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}
	
	public int getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", companyId=" + companyId + "]";
	}
	
	public static class ComputerBuilder {
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
}
