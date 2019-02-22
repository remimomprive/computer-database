package fr.excilys.rmomprive.ui.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.rmomprive.dto.ComputerDto;
import fr.excilys.rmomprive.exception.ValidationException;
import fr.excilys.rmomprive.mapper.ComputerMapper;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.CompanyService;
import fr.excilys.rmomprive.service.ComputerService;
import fr.excilys.rmomprive.validation.ComputerValidator;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      Collection<Company> companies = CompanyService.getInstance().getAll();
      request.setAttribute("companies", companies);
    } catch (SQLException e) {
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
        company = CompanyService.getInstance().getById(companyId);
        companyName = company.isPresent() ? company.get().getName() : null;
      } catch (SQLException e) {
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
      Optional<Computer> insertedComputer = ComputerService.getInstance().add(computer);

      // Write data to the page
      response.getWriter().write(computerDto.toString());
      response.getWriter().write(insertedComputer.toString());
    } catch (ValidationException e) {
      // Show an error page
      RequestDispatcher dispatcher = request.getRequestDispatcher("/views/500.jsp");
      dispatcher.forward(request, response);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
