package fr.excilys.rmomprive.mapper;

import java.util.Calendar;
import java.util.Date;
import fr.excilys.rmomprive.dto.ComputerDto;
import fr.excilys.rmomprive.dto.IDto;
import fr.excilys.rmomprive.model.Computer;

public class ComputerMapper implements IMapper<Computer> {
  private static ComputerMapper instance;

  @Override
  public ComputerDto mapFromEntity(Computer computer) {
    return new ComputerDto(computer.getName(), parseDate(computer.getIntroduced()),
        parseDate(computer.getDiscontinued()),
        computer.getCompany() != null ? computer.getCompany().getName() : "");
  }

  @Override
  public Computer mapFromDto(IDto<Computer> dto) {
    return null;
  }

  /**
   * Parse a date object for printing.
   * @param date The date object
   * @return The output string
   */
  private String parseDate(Date date) {
    if (date != null) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return String.format("%d/%d/%d", calendar.get(Calendar.DAY_OF_MONTH),
          calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
    }

    return "";
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
