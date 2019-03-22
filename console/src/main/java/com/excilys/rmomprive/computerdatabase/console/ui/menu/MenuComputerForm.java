package com.excilys.rmomprive.computerdatabase.console.ui.menu;

import java.time.LocalDate;
import java.util.Optional;

import com.excilys.rmomprive.computerdatabase.core.Company;
import com.excilys.rmomprive.computerdatabase.core.Computer;
import com.excilys.rmomprive.computerdatabase.core.Computer.ComputerBuilder;
import com.excilys.rmomprive.computerdatabase.service.ICompanyService;
import com.excilys.rmomprive.computerdatabase.service.IComputerService;

public abstract class MenuComputerForm extends Menu {
  protected ICompanyService companyService;
  protected IComputerService computerService;

  public MenuComputerForm() {
    this.companyService = super.companyService;
    this.computerService = super.computerService;
  }

  /**
   * This method displays a form in order to fill a Computer.
   *
   * @return The filled computer
   */
  protected Computer form() {
    String name = Menus.readComputerName();

    System.out.println("What's the computer introduction date (YYYY-mm-dd) or null?");
    Optional<LocalDate> introduced = Menus.readDate(true);

    Optional<LocalDate> discontinued = Optional.empty();
    if (introduced.isPresent()) {
      System.out.println("What's the computer discontinuation date (YYYY-mm-dd) or null?");
      boolean validDiscontinuedDate = false;

      do {
        discontinued = Menus.readDate(true);
        validDiscontinuedDate = (discontinued.isPresent())
            ? introduced.get().isBefore(discontinued.get())
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
      Optional<Company> company = companyService.getById(companyId.get());
      if (company.isPresent()) {
        computerBuilder.setCompany(company.get());
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
