package fr.excilys.rmomprive.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "fr.excilys.rmomprive.service", "fr.excilys.rmomprive.persistence", "fr.excilys.rmomprive.ui.web", "fr.excilys.rmomprive.mapper" })
public class AppConfiguration {  
  @Bean
  LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("fr.excilys.rmomprive.model");
    return sessionFactory;
  }

  @Bean
  DataSource dataSource() {
    HikariConfig config = new HikariConfig("/hikari.properties");
    return new HikariDataSource(config);
  }
}
