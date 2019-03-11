package fr.excilys.rmomprive.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "fr.excilys.rmomprive.service", "fr.excilys.rmomprive.persistence" })
public class AppConfiguration {
  @Bean
  public DataSource dataSource() {
    HikariConfig config = new HikariConfig("/hikari.properties");
    return new HikariDataSource(config);
  }
}
