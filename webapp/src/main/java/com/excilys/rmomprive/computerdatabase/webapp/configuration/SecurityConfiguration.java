package com.excilys.rmomprive.computerdatabase.webapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.excilys.rmomprive.computerdatabase.service.UserDetailsServiceImpl;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private UserDetailsServiceImpl userDetailsService;
  
  public SecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
    this.userDetailsService = userDetailsService;
  }
  
  @Override
  protected void configure(final HttpSecurity http) throws Exception { 
    http
    .csrf().disable()
    .authorizeRequests()
    .antMatchers("/login*").permitAll()
    .antMatchers("/register*").permitAll()
    //.anyRequest().authenticated()
    .and()
    .formLogin()
    .and()
    .logout();
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(authenticationProvider());
  }
   
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(encoder());
      return authProvider;
  }
  
  @Bean
  public PasswordEncoder encoder() {
      return new BCryptPasswordEncoder(11);
  }
}
