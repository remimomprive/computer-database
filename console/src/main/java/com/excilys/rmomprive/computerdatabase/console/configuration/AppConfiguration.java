package com.excilys.rmomprive.computerdatabase.console.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "com.excilys.rmomprive.computerdatabase.service", "com.excilys.rmomprive.computerdatabase.persistence", "com.excilys.rmomprive.computerdatabase.webapp.controller", "com.excilys.rmomprive.computerdatabase.binding" })
public class AppConfiguration {
  @Bean
  LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("com.excilys.rmomprive.computerdatabase.core");
    return sessionFactory;
  }

  @Bean
  DataSource dataSource() {
    HikariConfig config = new HikariConfig("/hikari.properties");
    return new HikariDataSource(config);
  }
}
