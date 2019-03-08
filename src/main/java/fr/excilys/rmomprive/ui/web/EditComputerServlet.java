package fr.excilys.rmomprive.ui.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.excilys.rmomprive.dto.ComputerDto;
import fr.excilys.rmomprive.exception.ValidationException;
import fr.excilys.rmomprive.mapper.ComputerMapper;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.CompanyService;
import fr.excilys.rmomprive.service.ComputerService;
import fr.excilys.rmomprive.validation.ComputerValidator;

@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  @Autowired
  private CompanyService companyService;
  
  @Autowired
  private ComputerService computerService;
  
  @Override
  public void init() throws ServletException {
    super.init();
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String computerId = request.getParameter("computerId");
    Optional<Computer> computer = Optional.empty();
    Collection<Company> companies = new ArrayList<>();

    try {
      computer = computerService.getById(Long.parseLong(computerId));
      companies = companyService.getAll();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (computer.isPresent()) {
      ComputerDto computerDto = ComputerMapper.getInstance().mapFromEntity(computer.get());
      request.setAttribute("computer", computerDto);
      request.setAttribute("companies", companies);
    }

    RequestDispatcher dispatcher = request.getRequestDispatcher("/views/editComputer.jsp");
    dispatcher.forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // Retrieve the form parameters
    Long computerId = Long.valueOf(request.getParameter("computerId"));
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
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    // Create the DTO
    ComputerDto computerDto = new ComputerDto(Optional.of(computerId), computerName, introduced,
        discontinued, companyId, companyName);
    // Get the entity thanks to the DTO
    Computer computer = ComputerMapper.getInstance().mapFromDto(computerDto);

    try {
      // Validate the entity
      ComputerValidator.validate(computer);
      // Try to insert the entity
      Computer updatedComputer = computerService.update(computer);

      // Write data to the page
      response.getWriter().write(computerDto.toString());
      response.getWriter().write(updatedComputer.toString());
    } catch (ValidationException e) {
      // Show an error page
      RequestDispatcher dispatcher = request.getRequestDispatcher("/views/500.jsp");
      dispatcher.forward(request, response);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
