package fr.excilys.rmomprive.mapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.rmomprive.configuration.AppConfiguration;
import fr.excilys.rmomprive.dto.ComputerDto;
import fr.excilys.rmomprive.exception.DaoException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.model.Computer.ComputerBuilder;
import fr.excilys.rmomprive.service.ICompanyService;
import fr.excilys.rmomprive.util.Dates;

public class ComputerMapper implements IMapper<Computer> {
  private static ComputerMapper instance;
  private static ICompanyService companyService;
  private static Logger LOGGER;
  
  static {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    ComputerMapper.companyService =  context.getBean(ICompanyService.class);
    ComputerMapper.LOGGER = LoggerFactory.getLogger(ComputerMapper.class);
  }

  @Override
  public ComputerDto mapFromEntity(Computer computer) {
    return new ComputerDto(Optional.of(computer.getId()), computer.getName(),
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
      if (dto.getId().isPresent()) {
        computerBuilder.setId(dto.getId().get());
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
    } catch (DateTimeParseException | NumberFormatException | DaoException e) {
      LOGGER.error(e.getMessage());
    }

    return computerBuilder.build();
  }

  /**
   * @return The instance of ComputerMapper in memory
   */
  public static ComputerMapper getInstance() {
    if (instance == null) {
      instance = new ComputerMapper();
    }
    return instance;
  }
}