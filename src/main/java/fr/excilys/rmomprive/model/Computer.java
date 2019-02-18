package fr.excilys.rmomprive.model;

import java.sql.Timestamp;

public class Computer {
	private long id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private long companyId;
	
	private Computer() {
		
	}
	
	public Computer(long id, String name, Timestamp introduced, Timestamp discontinued, long companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
	
	public long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", companyId=" + companyId + "]";
	}
	
	public static class ComputerBuilder {
		private long id;
		private String name;
		private Timestamp introduced;
		private Timestamp discontinued;
		private long companyId;
		
		public Computer build() {
			Computer computer = new Computer();
			
			computer.setId(this.id);
			computer.setName(this.name);
			computer.setIntroduced(this.introduced);
			computer.setDiscontinued(this.discontinued);
			computer.setCompanyId(this.companyId);
			
			return computer;
		}

		public ComputerBuilder setId(long id) {
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

		public ComputerBuilder setCompanyId(long companyId) {
			this.companyId = companyId;
			return this;
		}
	}
}
