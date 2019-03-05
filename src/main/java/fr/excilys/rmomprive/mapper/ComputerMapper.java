package fr.excilys.rmomprive.mapper;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import fr.excilys.rmomprive.dto.ComputerDto;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.model.Computer.ComputerBuilder;
import fr.excilys.rmomprive.service.CompanyService;
import fr.excilys.rmomprive.util.Dates;

public class ComputerMapper implements IMapper<Computer> {
  private static ComputerMapper instance;

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
        Optional<Company> company = CompanyService.getInstance().getById(dto.getCompanyId());
        if (company.isPresent()) {
          computerBuilder.setCompany(company.get());
        }
      }
    } catch (DateTimeParseException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return computerBuilder.build();
  }

  /**
   * Parse a date object for printing.
   *
   * @param dateString The date object
   * @return The output string
   */
  private Date parseStringToDate(String dateString) {
    if (dateString != null && !dateString.equals("")) {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
      try {
        return format.parse(dateString);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    return null;
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