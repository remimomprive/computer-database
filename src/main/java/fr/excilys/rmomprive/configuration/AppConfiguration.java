package fr.excilys.rmomprive.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"fr.excilys.rmomprive.service", "fr.excilys.rmomprive.persistence"})
public class AppConfiguration {
  @Bean
  @ConfigurationProperties("app.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }
}
