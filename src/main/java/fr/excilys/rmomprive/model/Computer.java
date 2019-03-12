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
   * 
   * @param id           The computer id
   * @param name         The computer name
   * @param introduced   The computer introduction date.
   * @param discontinued The computer discontinution date.
   * @param company      The computer company.
   */
  public Computer(final long id, final String name, final LocalDate introduced,
      final LocalDate discontinued, final Company company) {
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((company == null) ? 0 : company.hashCode());
    result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Computer)) {
      return false;
    }
    Computer other = (Computer) obj;
    if (company == null) {
      if (other.company != null) {
        return false;
      }
    } else if (!company.equals(other.company)) {
      return false;
    }
    if (discontinued == null) {
      if (other.discontinued != null) {
        return false;
      }
    } else if (!discontinued.equals(other.discontinued)) {
      return false;
    }
    if (id != other.id) {
      return false;
    }
    if (introduced == null) {
      if (other.introduced != null) {
        return false;
      }
    } else if (!introduced.equals(other.introduced)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }



  public static class ComputerBuilder {
    private long id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;

    /**
     * Build a Computer from the ComputerBuilder instance and returns it.
     * 
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
