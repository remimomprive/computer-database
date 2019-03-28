package com.excilys.rmomprive.computerdatabase.webapp.controller;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.rmomprive.computerdatabase.binding.ComputerDto;
import com.excilys.rmomprive.computerdatabase.binding.ComputerMapper;
import com.excilys.rmomprive.computerdatabase.validation.ComputerValidator;
import com.excilys.rmomprive.computerdatabase.validation.ValidationException;
import com.excilys.rmomprive.computerdatabase.core.Company;
import com.excilys.rmomprive.computerdatabase.core.Computer;
import com.excilys.rmomprive.computerdatabase.service.ICompanyService;
import com.excilys.rmomprive.computerdatabase.service.IComputerService;

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

  @Secured("ROLE_ADMIN")
  @GetMapping
  public String get(Model model) {
    Collection<Company> companies = companyService.getAll();

    model.addAttribute("computer", new ComputerDto());
    model.addAttribute("companies", companies);

    return "addComputer";
  }

  @Secured("ROLE_ADMIN")
  @PostMapping
  public RedirectView post(@Valid @ModelAttribute("computer") ComputerDto computerDto, BindingResult result) {
    if (computerDto.getCompanyId() != null) {
      Optional<Company> company = companyService.getById(computerDto.getCompanyId());
      if (company.isPresent()) {
        computerDto.setCompanyName(company.get().getName());
      }
    }

    LOGGER.info("ComputerDto validation infos :");
    LOGGER.info(computerDto.toString());
    LOGGER.info(String.valueOf(result.hasErrors()));

    try {
      // Try to insert the entity
      computerService.add(computerDto);

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
    }
  }

  public int getPageCount(int pageSize, String search) {
    return computerService.getPageCount(pageSize, search);
  }
}
