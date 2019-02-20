package fr.excilys.rmomprive.dto;

import fr.excilys.rmomprive.model.Computer;

public class ComputerDto implements IDto<Computer> {
  private String id;
  private String name;
  private String introduced;
  private String discontinued;
  private String companyName;

  /**
   * Constructs a ComputerDto object.
   * @param name The Computer name
   * @param introduced The Computer introduction date
   * @param discontinued The Computer discontinution date
   * @param companyName The Computer company name
   */
  public ComputerDto(String id, String name, String introduced, String discontinued, String companyName) {
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.companyName = companyName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIntroduced() {
    return introduced;
  }

  public void setIntroduced(String introduced) {
    this.introduced = introduced;
  }

  public String getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(String discontinued) {
    this.discontinued = discontinued;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  @Override
  public String toString() {
    return "ComputerDto [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
        + discontinued + ", companyName=" + companyName + "]";
  }
}
