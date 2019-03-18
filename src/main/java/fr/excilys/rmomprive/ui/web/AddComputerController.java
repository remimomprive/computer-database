package fr.excilys.rmomprive.ui.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    model.addAttribute("computer", new ComputerDto());
    model.addAttribute("companies", companies);

    return "addComputer";
  }

  @PostMapping
  public RedirectView post(@Valid @ModelAttribute("computer") ComputerDto computerDto,
      BindingResult result) {
    if (computerDto.getCompanyId() != null) {
      Optional<Company> company = companyService.getById(computerDto.getCompanyId());
      if (company.isPresent()) {
        computerDto.setCompanyName(company.get().getName());
      }
    }

    LOGGER.info("ComputerDto validation infos :");
    LOGGER.info(computerDto.toString());
    LOGGER.info(String.valueOf(result.hasErrors()));

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
