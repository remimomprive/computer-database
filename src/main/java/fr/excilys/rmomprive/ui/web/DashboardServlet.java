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

import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.pagination.Page;
import fr.excilys.rmomprive.service.ComputerService;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int pageSize = 10;
	private int pageId = 1;

	/// TODO : add dto
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageSizeParam = request.getParameter("page_size");
		if (pageSizeParam != null) {
			pageSize = Integer.valueOf(pageSizeParam);
		}

		String pageIdParam = request.getParameter("page_id");
		if (pageIdParam != null) {
			pageId = Integer.valueOf(pageIdParam);
		}

		try {
			Page<Computer> page = ComputerService.getInstance().getPage(this.pageId, this.pageSize);
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

	public int getPageCount() throws SQLException {
		return ComputerService.getInstance().getPageCount(pageSize);
	}
}
