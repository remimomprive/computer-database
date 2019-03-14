package fr.excilys.rmomprive.ui.web;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import fr.excilys.rmomprive.dto.ComputerDto;
import fr.excilys.rmomprive.exception.DaoException;
import fr.excilys.rmomprive.exception.ValidationException;
import fr.excilys.rmomprive.mapper.ComputerMapper;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.ICompanyService;
import fr.excilys.rmomprive.service.IComputerService;
import fr.excilys.rmomprive.validation.ComputerValidator;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {
  private Logger LOGGER;

  private ICompanyService companyService;
  private IComputerService computerService;

  public AddComputerController(ICompanyService companyService, IComputerService computerService) {
    this.LOGGER = LoggerFactory.getLogger(DashboardController.class);
    this.companyService = companyService;
    this.computerService = computerService;
  }

  @GetMapping
  public String get(Model model) {
    Collection<Company> companies = companyService.getAll();
    model.addAttribute("companies", companies);

    return "addComputer";
  }

  @PostMapping
  public RedirectView post(
      @RequestParam(name = "computerName", defaultValue = "") String computerName,
      @RequestParam(name = "introduced", defaultValue = "") String introduced,
      @RequestParam(name = "discontinued", defaultValue = "") String discontinued,
      @RequestParam(name = "companyId", defaultValue = "-1") long companyId, Model model) {
    // Retrieve the company name, if a company has been set
    Optional<Company> company = Optional.empty();
    String companyName = null;
    if (companyId != -1) {
      try {
        company = companyService.getById(companyId);
        companyName = company.isPresent() ? company.get().getName() : null;
      } catch (DataAccessException e) {
        e.printStackTrace();
      }
    }

    // Create the DTO
    ComputerDto computerDto = new ComputerDto(Optional.empty(), computerName, introduced,
        discontinued, companyId, companyName);
    // Get the entity thanks to the DTO
    Computer computer = ComputerMapper.getInstance().mapFromDto(computerDto);

    try {
      // Validate the entity
      ComputerValidator.validate(computer);
      // Try to insert the entity
      computerService.add(computer);

      // Write data to the page
      return new RedirectView("/computer-database/dashboard");
    } catch (ValidationException e) {
      // Log the error
      switch (e.getType()) {
      case EMPTY_DATE:
        LOGGER.error("If disconution date is valid, introduction date must be provided");
        break;
      case INVALID_NAME:
        LOGGER.error("The computer name should not be null");
        break;
      case INVALID_DATE_PRECEDENCE:
        LOGGER.error("The introduction date shoud be before the discontinution date");
        break;
      }

      // Show an error page
      throw new ValidationException(e.getType());
    } catch (DataAccessException e) {
      LOGGER.error(e.getMessage());
      throw new DaoException();
    }
  }

  public int getPageCount(int pageSize, String search) throws DataAccessException {
    return computerService.getPageCount(pageSize, search);
  }
}
