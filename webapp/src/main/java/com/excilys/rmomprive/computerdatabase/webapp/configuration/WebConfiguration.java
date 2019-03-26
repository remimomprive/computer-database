package com.excilys.rmomprive.computerdatabase.webapp.configuration;

import java.util.Locale;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@ComponentScan({ "com.excilys.rmomprive.computerdatabase.service", "com.excilys.rmomprive.computerdatabase.persistence", "com.excilys.rmomprive.computerdatabase.webapp.controller",
    "com.excilys.rmomprive.computerdatabase.binding" })
public class WebConfiguration implements WebMvcConfigurer {
  @Bean
  LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("com.excilys.rmomprive.computerdatabase.core");
    sessionFactory.setHibernateProperties(getHibernateProperties());
    return sessionFactory;
  }

  private Properties getHibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.hbm2ddl.auto", "update");
    return properties;
  }

  @Bean
  DataSource dataSource() {
    HikariConfig config = new HikariConfig("/hikari.properties");
    return new HikariDataSource(config);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/js/**").addResourceLocations("/static/js/");
    registry.addResourceHandler("/static/css/**").addResourceLocations("/static/css/");
    registry.addResourceHandler("/static/fonts/**").addResourceLocations("/static/fonts/");
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    interceptor.setParamName("locale");
    registry.addInterceptor(interceptor);
  }

  @Bean
  ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setViewClass(JstlView.class);
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("/i18n/cdb");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Bean
  public LocaleResolver localeResolver() {
    CookieLocaleResolver resolver = new CookieLocaleResolver();
    resolver.setDefaultLocale(new Locale("en"));
    resolver.setCookieName("locale-cookie");
    resolver.setCookieMaxAge(4800);
    return resolver;
  }
}
