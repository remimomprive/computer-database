package fr.excilys.rmomprive.servlet;

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

import fr.excilys.rmomprive.service.ComputerService;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
        try {
        	request.setAttribute("computers", ComputerService.getInstance().getAll());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/dashboard.jsp");
        dispatcher.forward(request, response);
    }
 
    @Override
    protected void doGet(
      HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
 
        processRequest(request, response);
    }
}
