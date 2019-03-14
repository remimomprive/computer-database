package fr.excilys.rmomprive.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class WebConfiguration implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
    ctx.register(AppConfiguration.class);

    // Enable DispatcherServlet
    ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher",
        new DispatcherServlet(ctx));
    registration.setLoadOnStartup(1);
    registration.addMapping("/");
  }

}
