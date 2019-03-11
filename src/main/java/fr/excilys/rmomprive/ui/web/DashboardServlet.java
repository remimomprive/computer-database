package fr.excilys.rmomprive.ui.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.excilys.rmomprive.dto.IDto;
import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.mapper.ComputerMapper;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.pagination.Page;
import fr.excilys.rmomprive.service.ICompanyService;
import fr.excilys.rmomprive.service.IComputerService;

import org.slf4j.Logger;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static int DEFAULT_PAGE_SIZE = 10;
  private static int DEFAULT_PAGE_ID = 1;
  private int pageSize = DEFAULT_PAGE_SIZE;
  private int pageId = DEFAULT_PAGE_ID;

  private static String DEFAULT_ORDER_BY = "name";
  private static String DEFAULT_ORDER_DIRECTION = "asc";
  private String orderBy = DEFAULT_ORDER_BY;
  private String orderDirection = DEFAULT_ORDER_DIRECTION;

  private Logger logger;

  @Autowired
  private ICompanyService companyService;

  @Autowired
  private IComputerService computerService;

  @Override
  public void init() throws ServletException {
    super.init();
    this.logger = LoggerFactory.getLogger(DashboardServlet.class);
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    System.out.print("SERVLET INIT");
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
    } else {
      pageSize = DEFAULT_PAGE_SIZE;
    }

    String pageIdParam = request.getParameter("page_id");
    if (pageIdParam != null) {
      try {
        pageId = Integer.valueOf(pageIdParam);
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    } else {
      pageId = DEFAULT_PAGE_ID;
    }

    String orderByParam = request.getParameter("order_by");
    if (orderByParam != null) {
      orderBy = orderByParam;
    } else {
      orderBy = DEFAULT_ORDER_BY;
    }

    String orderDirectionParam = request.getParameter("order_direction");
    if (orderDirectionParam != null) {
      orderDirection = orderDirectionParam;
    } else {
      orderDirection = DEFAULT_ORDER_DIRECTION;
    }

    Page<IDto<Computer>> page = null;
    int rowCount = 0;
    int pageCount = 0;

    String search = request.getParameter("search");

    try {
      if (search == null || search.equals("")) {
        search = "";
      }
      page = computerService.getByNameOrCompanyName(this.pageId, this.pageSize, search,
          this.orderBy, this.orderDirection).createDtoPage(ComputerMapper.getInstance());
      rowCount = computerService.getRowCount(search);
      pageCount = getPageCount(search);
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
    request.setAttribute("orderBy", orderBy);
    request.setAttribute("orderDirection", orderDirection);

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
      computerService.deleteByIds(ids);
      logger.info("Successfully deleted computers {}", idsString);
    } catch (SQLException e) {
      logger.error("An error happened while trying to delete computers {}", idsString);
    }
  }

  public int getPageCount(String search) throws SQLException {
    return computerService.getPageCount(pageSize, search);
  }

  /**
   * Exposes the page count method from the ComputerService class to the jsp.
   * 
   * @return The page count value
   * @throws SQLException if an error while accessing to the database happened
   */
  public int getPageCount() throws SQLException {
    return computerService.getPageCount(pageSize);
  }
}
