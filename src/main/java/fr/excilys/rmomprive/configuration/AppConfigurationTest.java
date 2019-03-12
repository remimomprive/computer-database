package fr.excilys.rmomprive.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "fr.excilys.rmomprive.service", "fr.excilys.rmomprive.persistence" })
public class AppConfigurationTest {
  @Bean
  public DataSource dataSource() {
    HikariConfig config = new HikariConfig("/hikari.test.properties");
    return new HikariDataSource(config);
  }
}
