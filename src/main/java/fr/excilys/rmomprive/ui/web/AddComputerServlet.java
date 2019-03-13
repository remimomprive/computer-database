package fr.excilys.rmomprive.ui.web;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.excilys.rmomprive.dto.ComputerDto;
import fr.excilys.rmomprive.exception.ValidationException;
import fr.excilys.rmomprive.mapper.ComputerMapper;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.ICompanyService;
import fr.excilys.rmomprive.service.IComputerService;
import fr.excilys.rmomprive.validation.ComputerValidator;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private Logger LOGGER;

  @Autowired
  private ICompanyService companyService;

  @Autowired
  private IComputerService computerService;

  @Override
  public void init() throws ServletException {
    super.init();
    this.LOGGER = LoggerFactory.getLogger(AddComputerServlet.class);
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      Collection<Company> companies = companyService.getAll();
      request.setAttribute("companies", companies);
    } catch (DataAccessException e) {
      e.printStackTrace();
    }

    RequestDispatcher dispatcher = request.getRequestDispatcher("/views/addComputer.jsp");
    dispatcher.forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // Retrieve the form parameters
    String computerName = request.getParameter("computerName");
    String introduced = (String) request.getParameter("introduced");
    String discontinued = (String) request.getParameter("discontinued");
    String companyIdString = request.getParameter("companyId");

    Long companyId = null;

    // Parse the company id input
    if (companyIdString != null && !companyIdString.equals("")) {
      try {
        companyId = Long.parseLong(companyIdString);
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }

    // Retrieve the company name, if a company has been set
    Optional<Company> company = Optional.empty();
    String companyName = null;
    if (companyId != null) {
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
      Optional<Computer> insertedComputer = computerService.add(computer);

      // Write data to the page
      response.getWriter().write(computerDto.toString());
      response.getWriter().write(insertedComputer.toString());
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
      RequestDispatcher dispatcher = request.getRequestDispatcher("/views/500.jsp");
      dispatcher.forward(request, response);
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }
}
