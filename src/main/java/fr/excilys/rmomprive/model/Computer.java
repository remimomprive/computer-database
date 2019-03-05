package fr.excilys.rmomprive.model;

import java.time.LocalDate;

public class Computer {
  private long id;
  private String name;
  private LocalDate introduced;
  private LocalDate discontinued;
  private Company company;

  /**
   * Private constructor for builder.
   */
  private Computer() {

  }

  /**
   * Create a Computer object.
   * @param id The computer id
   * @param name The computer name
   * @param introduced The computer introduction date.
   * @param discontinued The computer discontinution date.
   * @param company The computer company.
   */
  public Computer(final long id, final String name, final LocalDate introduced, final LocalDate discontinued,
      final Company company) {
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.company = company;
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setIntroduced(final LocalDate introduced) {
    this.introduced = introduced;
  }

  public LocalDate getIntroduced() {
    return introduced;
  }

  public LocalDate getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(final LocalDate discontinued) {
    this.discontinued = discontinued;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(final Company company) {
    this.company = company;
  }

  @Override
  public String toString() {
    return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced
        + ", discontinued=" + discontinued + ", company=" + company + "]";
  }

  public static class ComputerBuilder {
    private long id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;

    /**
     * Build a Computer from the ComputerBuilder instance and returns it.
     * @return The created Computer object
     */
    public Computer build() {
      Computer computer = new Computer();

      computer.setId(this.id);
      computer.setName(this.name);
      computer.setIntroduced(this.introduced);
      computer.setDiscontinued(this.discontinued);
      computer.setCompany(this.company);

      return computer;
    }

    /**
     * @param id The computer id
     * @return The ComputerBuilder instance
     */
    public ComputerBuilder setId(long id) {
      this.id = id;
      return this;
    }

    /**
     * @param name The computer name
     * @return The ComputerBuilder instance
     */
    public ComputerBuilder setName(String name) {
      this.name = name;
      return this;
    }

    /**
     * @param introduced The computer introduction date
     * @return The ComputerBuilder instance
     */
    public ComputerBuilder setIntroduced(LocalDate introduced) {
      this.introduced = introduced;
      return this;
    }

    /**
     * @param discontinued The computer discontinution date
     * @return The ComputerBuilder instance
     */
    public ComputerBuilder setDiscontinued(LocalDate discontinued) {
      this.discontinued = discontinued;
      return this;
    }

    /**
     * @param company The computer company
     * @return The ComputerBuilder instance
     */
    public ComputerBuilder setCompany(Company company) {
      this.company = company;
      return this;
    }
  }
}
