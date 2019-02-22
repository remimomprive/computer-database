package fr.excilys.rmomprive.dto;

import java.util.Optional;

import fr.excilys.rmomprive.model.Computer;

public class ComputerDto implements IDto<Computer> {
  private Optional<Long> id;
  private String name;
  private String introduced;
  private String discontinued;
  private Long companyId;
  private String companyName;

  /**
   * Constructs a ComputerDto object.
   * @param id           The Computer id
   * @param name         The Computer name
   * @param introduced   The Computer introduction date
   * @param discontinued The Computer discontinution date
   * @param companyId  The Computer company id
   * @param companyName  The Computer company name
   */
  public ComputerDto(Optional<Long> id, String name, String introduced, String discontinued,
      Long companyId, String companyName) {
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.companyId = companyId;
    this.companyName = companyName;
  }

  public Optional<Long> getId() {
    return id;
  }

  public void setId(Optional<Long> id) {
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

  public Long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(Long companyId) {
    this.companyId = companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  @Override
  public String toString() {
    return "ComputerDto [id=" + id + ", name=" + name + ", introduced=" + introduced
        + ", discontinued=" + discontinued + ", companyId=" + companyId + ", companyName="
        + companyName + "]";
  }

  /// TODO : add validation method
}
