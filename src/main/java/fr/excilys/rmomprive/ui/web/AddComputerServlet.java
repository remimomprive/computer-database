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
import fr.excilys.rmomprive.mapper.ComputerMapper;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.service.CompanyService;
import fr.excilys.rmomprive.service.ComputerService;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private int pageSize = 10;
  private int pageId = 1;

  /**
   * Processes the incoming request (retrieves data and passes them to the view).
   *
   * @param request  object that represents the request the client makes of the servlet
   * @param response object that represents the response the servlet returns to the client
   * @throws ServletException if the view .jsp file throws an exception
   * @throws IOException      if the view .jsp file does not exist
   */
  private void processRequest(HttpServletRequest request, HttpServletResponse response)
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
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String computerName = request.getParameter("computerName");
    String introduced = (String) request.getParameter("introduced");
    String discontinued = (String) request.getParameter("discontinued");
    Optional<Long> companyId = Optional.empty();

    String companyIdString = request.getParameter("companyId");
    if (companyIdString != null) {
      try {
        companyId = Optional.of(Long.parseLong(companyIdString));
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }

    /// TODO : replace with computer dto validation method
    if (!introduced.equals("")) {
      try {
        Optional<Company> company = companyId.isPresent()
            ? CompanyService.getInstance().getById(companyId.get())
            : Optional.empty();
        Optional<String> companyName = company.isPresent() ? Optional.of(company.get().getName())
            : Optional.empty();

        ComputerDto computerDto = new ComputerDto(Optional.empty(), computerName, introduced,
            discontinued, companyId, companyName);
        response.getWriter().write(computerDto.toString());
        try {
          Optional<Computer> insertedComputer = ComputerService.getInstance()
              .add(ComputerMapper.getInstance().mapFromDto(computerDto));

          if (insertedComputer.isPresent()) {
            response.getWriter().write(insertedComputer.toString());
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e1) {
        e1.printStackTrace();
      }
    }
  }
}
