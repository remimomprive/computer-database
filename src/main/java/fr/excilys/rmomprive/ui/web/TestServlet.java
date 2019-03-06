package fr.excilys.rmomprive.ui.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.rmomprive.configuration.AppConfiguration;
import fr.excilys.rmomprive.model.User;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    User user = context.getBean(User.class);
    
    LoggerFactory.getLogger(TestServlet.class).info(Logger.GLOBAL_LOGGER_NAME);

    response.getWriter().print(user.getName());
  }
}
