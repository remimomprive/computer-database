package com.excilys.rmomprive.computerdatabase.binding;

import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.rmomprive.computerdatabase.core.Company;
import com.excilys.rmomprive.computerdatabase.core.Computer;
import com.excilys.rmomprive.computerdatabase.core.Computer.ComputerBuilder;
import com.excilys.rmomprive.computerdatabase.service.ICompanyService;
import com.excilys.rmomprive.computerdatabase.service.ICompanyService;
import com.excilys.rmomprive.computerdatabase.persistence.Page;

@Component
public class ComputerMapper implements IMapper<Computer> {
  private ICompanyService companyService;
  private Logger LOGGER;

  public ComputerMapper(ICompanyService companyService) {
    this.companyService = companyService;
    this.LOGGER = LoggerFactory.getLogger(ComputerMapper.class);
  }

  @Override
  public ComputerDto mapFromEntity(Computer computer) {
    return new ComputerDto(computer.getId(), computer.getName(),
        Dates.parse(computer.getIntroduced()), Dates.parse(computer.getDiscontinued()),
        computer.getCompany() != null ? computer.getCompany().getId() : null,
        computer.getCompany() != null ? computer.getCompany().getName() : null);
  }

  /**
   * @param dto The computer dto
   * @return The computer entity from a computer dto
   */
  public Computer mapFromDto(ComputerDto dto) {
    ComputerBuilder computerBuilder = new ComputerBuilder().setName(dto.getName());

    try {
      if (dto.getId() != null) {
        computerBuilder.setId(dto.getId());
      }

      if (!dto.getIntroduced().equals("")) {
        computerBuilder.setIntroduced(Dates.parse(dto.getIntroduced()));
      }

      if (!dto.getDiscontinued().equals("")) {
        computerBuilder.setDiscontinued(Dates.parse(dto.getDiscontinued()));
      }

      if (dto.getCompanyId() != null) {
        Optional<Company> company = companyService.getById(dto.getCompanyId());
        if (company.isPresent()) {
          computerBuilder.setCompany(company.get());
        }
      }
    } catch (DateTimeParseException | NumberFormatException e) {
      LOGGER.error(e.getMessage());
    }

    return computerBuilder.build();
  }

  /**
   * Create the dto page from a entity page.
   *
   * @param mapper The entity mapper
   * @return The page
   */
  public Page<ComputerDto> createDtoPage(Page<Computer> page) {
    List<ComputerDto> data = new ArrayList<>();
    for (Computer computer : page.getContent()) {
      data.add(mapFromEntity(computer));
    }

    return new Page<ComputerDto>(data, page.getPageId(), page.getPageSize(), page.isPrevious(), page.isNext());
  }
}
