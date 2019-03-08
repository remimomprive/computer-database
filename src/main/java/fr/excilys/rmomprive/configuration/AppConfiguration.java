package fr.excilys.rmomprive.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// https://docs.spring.io/spring-data/jdbc/docs/1.1.0.M1/reference/html/#jdbc.repositories

@Configuration
@ComponentScan({"fr.excilys.rmomprive.service", "fr.excilys.rmomprive.persistence"})
public class AppConfiguration {

}
