package com.excilys.rmomprive.computerdatabase.webapp.controller;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/computer/{id}/edit")
public class EditComputerController {
  private Logger LOGGER;

  private ICompanyService companyService;
  private IComputerService computerService;

  public EditComputerController(ICompanyService companyService, IComputerService computerService) {
    this.LOGGER = LoggerFactory.getLogger(EditComputerController.class);
    this.companyService = companyService;
    this.computerService = computerService;
  }

  @GetMapping
  public String get(@PathVariable("id") long id, Model model) {
    Collection<Company> companies = companyService.getAll();
    Optional<ComputerDto> computerDto = computerService.getById(id);

    if (computerDto.isPresent()) {
      model.addAttribute("computer", computerDto.get());
      model.addAttribute("companies", companies);
    }

    return "editComputer";
  }

  @PostMapping
  public RedirectView post(@ModelAttribute("computer") @Valid ComputerDto computerDto, BindingResult result, Model model) {
    if (computerDto.getCompanyId() != null) {
      Optional<Company> company = companyService.getById(computerDto.getCompanyId());
      if (company.isPresent()) {
        computerDto.setCompanyName(company.get().getName());
        LOGGER.error(String.valueOf(company));
      }
    }

    LOGGER.info(result.getFieldErrors().toString());

    try {
      // Try to insert the entity
      ComputerDto updatedComputer = computerService.update(computerDto);

      // Write data to the page
      return new RedirectView("/computer-database/dashboard");
    } catch (ValidationException e) {
      // Show an error page
      LOGGER.error(e.getMessage());
      throw new ValidationException(e.getType());
    }
  }
}
