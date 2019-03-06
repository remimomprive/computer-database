package fr.excilys.rmomprive.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import fr.excilys.rmomprive.model.User;

@Configuration
public class AppConfiguration {
  @Bean
  public User user() {
    return new User();
  }
}
