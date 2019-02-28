package fr.excilys.rmomprive.ui.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
  private Logger logger;

  public void init(ServletConfig config) throws ServletException {
    this.logger = LoggerFactory.getLogger(DashboardServlet.class);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

    Page<IDto<Computer>> page = null;
    int rowCount = 0;
    int pageCount = 0;

    String search = request.getParameter("search");

    try {
      if (search != null && !search.equals("")) {
        page = ComputerService.getInstance()
            .getByNameOrCompanyName(this.pageId, this.pageSize, search)
            .createDtoPage(ComputerMapper.getInstance());
        rowCount = ComputerService.getInstance().getRowCount(search);
        pageCount = getPageCount(search);
      } else {
        page = ComputerService.getInstance().getPage(this.pageId, this.pageSize)
            .createDtoPage(ComputerMapper.getInstance());
        rowCount = ComputerService.getInstance().getRowCount();
        pageCount = getPageCount();
      }
    } catch (InvalidPageIdException e) {
      e.printStackTrace();
    } catch (InvalidPageSizeException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    request.setAttribute("computers", page);
    request.setAttribute("computerCount", rowCount);
    request.setAttribute("pageSize", pageSize);
    request.setAttribute("pageId", pageId);
    request.setAttribute("pageCount", pageCount);
    request.setAttribute("search", search);

    RequestDispatcher dispatcher = request.getRequestDispatcher("/views/dashboard.jsp");
    dispatcher.forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String selection = request.getParameter("selection");
    String[] idsString = selection.split(",");
    List<Long> ids = new ArrayList();
    for (String id : idsString) {
      ids.add(Long.valueOf(id));
    }

    try {
      ComputerService.getInstance().deleteByIds(ids);
      logger.info("Successfully deleted computers {}", idsString);
    } catch (SQLException e) {
      logger.error("An error happened while trying to delete computers {}", idsString);
    }
  }

  public int getPageCount(String search) throws SQLException {
    return ComputerService.getInstance().getPageCount(pageSize, search);
  }

  /**
   * Exposes the page count method from the ComputerService class to the jsp.
   * 
   * @return The page count value
   * @throws SQLException if an error while accessing to the database happened
   */
  public int getPageCount() throws SQLException {
    return ComputerService.getInstance().getPageCount(pageSize);
  }
}
