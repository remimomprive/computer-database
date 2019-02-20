package fr.excilys.rmomprive.dto;

import java.util.Date;

public class ComputerDto {
  private String name;
  private Date introduced;
  private Date discontinued;
  private String companyName;

  /**
   * Constructs a ComputerDto object.
   * @param name The Computer name
   * @param introduced The Computer introduction date
   * @param discontinued The Computer discontinution date
   * @param companyName The Computer company name
   */
  public ComputerDto(String name, Date introduced, Date discontinued, String companyName) {
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.companyName = companyName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getIntroduced() {
    return introduced;
  }

  public void setIntroduced(Date introduced) {
    this.introduced = introduced;
  }

  public Date getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(Date discontinued) {
    this.discontinued = discontinued;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
}
