package fr.excilys.rmomprive.ui.web;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.rmomprive.dto.IDto;
import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.mapper.ComputerMapper;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.pagination.Page;
import fr.excilys.rmomprive.service.ComputerService;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private int pageSize = 10;
  private int pageId = 1;

  /**
   * Processes the incoming request (retrieves data and passes them to the view).
   * @param request object that represents the request the client makes of the servlet
   * @param response object that represents the response the servlet returns to the client
   * @throws ServletException if the view .jsp file throws an exception
   * @throws IOException if the view .jsp file does not exist
   */
  private void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String pageSizeParam = request.getParameter("page_size");
    if (pageSizeParam != null) {
      try {
        pageSize = Integer.valueOf(pageSizeParam);
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }

    String pageIdParam = request.getParameter("page_id");
    if (pageIdParam != null) {
      try {
        pageId = Integer.valueOf(pageIdParam);
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }

    try {
      Page<IDto<Computer>> page = ComputerService.getInstance().getPage(this.pageId, this.pageSize).createDtoPage(ComputerMapper.getInstance());
      request.setAttribute("computers", page);
      request.setAttribute("computerCount", ComputerService.getInstance().getRowCount());
      request.setAttribute("pageSize", pageSize);
      request.setAttribute("pageId", pageId);
      request.setAttribute("pageCount", getPageCount());
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (InvalidPageIdException e) {
      e.printStackTrace();
    } catch (InvalidPageSizeException e) {
      e.printStackTrace();
    }

    RequestDispatcher dispatcher = request.getRequestDispatcher("/views/dashboard.jsp");
    dispatcher.forward(request, response);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    processRequest(request, response);
  }

  /**
   * Exposes the page count method from the ComputerService class to the jsp.
   * @return The page count value
   * @throws SQLException if an error while accessing to the database happened
   */
  public int getPageCount() throws SQLException {
    return ComputerService.getInstance().getPageCount(pageSize);
  }
}
