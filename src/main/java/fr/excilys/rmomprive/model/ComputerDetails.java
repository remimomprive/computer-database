package fr.excilys.rmomprive.model;

public class ComputerDetails {
	private Computer computer;
	private Company company;
	
	public ComputerDetails(Computer computer, Company company) {
		super();
		this.computer = computer;
		this.company = company;
	}

	public Computer getComputer() {
		return computer;
	}

	public void setComputer(Computer computer) {
		this.computer = computer;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "ComputerDetails [computer=" + computer + ", company=" + company + "]";
	}
}
