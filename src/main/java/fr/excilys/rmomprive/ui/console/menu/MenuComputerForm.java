package fr.excilys.rmomprive.ui.console.menu;

import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.model.Computer.ComputerBuilder;
import fr.excilys.rmomprive.service.CompanyService;
import fr.excilys.rmomprive.util.Menus;

public abstract class MenuComputerForm extends Menu {
  /**
   * This method displays a form in order to fill a Computer.
   * @return The filled computer
   */
  protected Computer form() {
    String name = Menus.readComputerName();

    System.out.println("What's the computer introduction date (YYYY-mm-dd) or null?");
    Optional<Date> introduced = Menus.readDate(true);

    Optional<Date> discontinued = Optional.empty();
    if (introduced.isPresent()) {
      System.out.println("What's the computer discontinuation date (YYYY-mm-dd) or null?");
      boolean validDiscontinuedDate = false;

      do {
        discontinued = Menus.readDate(true);
        validDiscontinuedDate = (discontinued.isPresent())
            ? introduced.get().before(discontinued.get())
            : true;

        if (!validDiscontinuedDate) {
          getLogger()
              .error("The discontinued date should be after the intruduction date or NULL\n");
        }
      } while (!validDiscontinuedDate);
    }

    Optional<Integer> companyId = Menus.readCompanyId();

    ComputerBuilder computerBuilder = new ComputerBuilder().setName(name);

    if (companyId.isPresent()) {
      Optional<Company> company;
      try {
        company = CompanyService.getInstance().getById(companyId.get());
        if (company.isPresent()) {
          computerBuilder.setCompany(company.get());
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }

      if (introduced.isPresent()) {
        computerBuilder.setIntroduced(introduced.get());
      }
      if (discontinued.isPresent()) {
        computerBuilder.setDiscontinued(discontinued.get());
      }
    }
    return computerBuilder.build();
  }
}
