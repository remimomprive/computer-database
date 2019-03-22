package com.excilys.rmomprive.computerdatabase.binding;

import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.excilys.rmomprive.computerdatabase.core.Computer;

public class ComputerDto implements IDto<Computer> {
  private Long id;
  @NotBlank
  private String name;
  @DateTimeFormat(pattern = "dd-MMM-yyyy")
  private String introduced;
  @DateTimeFormat(pattern = "dd-MMM-yyyy")
  private String discontinued;
  private Long companyId;
  private String companyName;

  /**
   * Constructs a ComputerDto object.
   *
   * @param id           The Computer id
   * @param name         The Computer name
   * @param introduced   The Computer introduction date
   * @param discontinued The Computer discontinution date
   * @param companyId    The Computer company id
   * @param companyName  The Computer company name
   */
  public ComputerDto(Long id, String name, String introduced, String discontinued, Long companyId,
      String companyName) {
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.companyId = companyId;
    this.companyName = companyName;
  }

  public ComputerDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
}
