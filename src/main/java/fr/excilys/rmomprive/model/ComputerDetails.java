package fr.excilys.rmomprive.model;

public class ComputerDetails {
  private Computer computer;
  private Company company;

  /**
   * Create a ComputerDetails object with its fields.
   * @param computer The computer
   * @param company The company
   */
  public ComputerDetails(final Computer computer, final Company company) {
    this.computer = computer;
    this.company = company;
  }

  public Computer getComputer() {
    return computer;
  }

  public final void setComputer(final Computer computer) {
    this.computer = computer;
  }

  public Company getCompany() {
    return company;
  }

  public final void setCompany(final Company company) {
    this.company = company;
  }

  @Override
  public String toString() {
    return "ComputerDetails [computer=" + computer + ", company=" + company + "]";
  }
}
