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
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/computer/{id}/edit")
public class EditComputerController {
  private Logger LOGGER;

  private ICompanyService companyService;
  private IComputerService computerService;
  private ComputerMapper computerMapper;
  
  public EditComputerController(ICompanyService companyService, IComputerService computerService) {
    this.LOGGER = LoggerFactory.getLogger(EditComputerController.class);
    this.companyService = companyService;
    this.computerService = computerService;
  }

  @GetMapping
  public String get(@PathVariable("id") long id, Model model) {
    Collection<Company> companies = companyService.getAll();
    Optional<Computer> computer = computerService.getById(id);

    if (computer.isPresent()) {
      ComputerDto computerDto = this.computerMapper.mapFromEntity(computer.get());
      model.addAttribute("computer", computerDto);
      model.addAttribute("companies", companies);
    }

    return "editComputer";
  }

  @PostMapping
  public RedirectView post(@ModelAttribute("computer") @Valid ComputerDto computerDto,
      BindingResult result, Model model) {
    if (computerDto.getCompanyId() != null) {
      Optional<Company> company = companyService.getById(computerDto.getCompanyId());
      if (company.isPresent()) {
        computerDto.setCompanyName(company.get().getName());
      }
    }
    
    LOGGER.info(result.getFieldErrors().toString());

    // Get the entity thanks to the DTO
    Computer computer = this.computerMapper.mapFromDto(computerDto);

    try {
      // Try to insert the entity
      Computer updatedComputer = computerService.update(computer);

      // Write data to the page
      return new RedirectView("/computer-database/dashboard");
    } catch (ValidationException e) {
      // Show an error page
      LOGGER.error(e.getMessage());
      throw new ValidationException(e.getType());
    } catch (DataAccessException e) {
      LOGGER.error(e.getMessage());
      throw new DaoException();
    }
  }
}
