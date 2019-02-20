package fr.excilys.rmomprive.mapper;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import fr.excilys.rmomprive.dto.ComputerDto;
import fr.excilys.rmomprive.dto.IDto;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.model.Computer.ComputerBuilder;
import fr.excilys.rmomprive.service.CompanyService;
import fr.excilys.rmomprive.service.ComputerService;

public class ComputerMapper implements IMapper<Computer> {
  private static ComputerMapper instance;

  @Override
  public ComputerDto mapFromEntity(Computer computer) {
    return new ComputerDto(String.valueOf(computer.getId()), computer.getName(),
        parseDateToString(computer.getIntroduced()), parseDateToString(computer.getDiscontinued()),
        computer.getCompany() != null ? computer.getCompany().getName() : "");
  }

  public Computer mapFromDto(ComputerDto dto) {
    ComputerBuilder computerBuilder =
        new ComputerBuilder()
        .setId(Long.parseLong(dto.getId()))
        .setName(dto.getName())
        .setIntroduced(parseStringToDate(dto.getIntroduced()))
        .setDiscontinued(parseStringToDate(dto.getDiscontinued()));

    try {
      Optional<Company> company = CompanyService.getInstance()
          .getByName(dto.getCompanyName());

      if (company.isPresent()) {
        computerBuilder.setCompany(company.get());
      }
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
   * @param date The date object
   * @return The output string
   */
  private String parseDateToString(Date date) {
    if (date != null) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return String.format("%d/%d/%d", calendar.get(Calendar.DAY_OF_MONTH),
          calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
    }

    return "";
  }

  /**
   * Parse a date object for printing.
   * @param date The date object
   * @return The output string
   */
  private Date parseStringToDate(String dateString) {
    if (dateString != null && !dateString.equals("")) {
      DateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
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