package com.excilys.rmomprive.computerdatabase.webapp.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.rmomprive.computerdatabase.core.User;
import com.excilys.rmomprive.computerdatabase.service.UserDetailsServiceImpl;

@Controller
@RequestMapping("/register")
public class RegisterController {
  private Logger LOGGER;

  private UserDetailsServiceImpl userDetailsService;
  private PasswordEncoder passwordEncoder;

  public RegisterController(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
    this.LOGGER = LoggerFactory.getLogger(RegisterController.class);
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping
  public String get(Model model) {
    return "register";
  }

  @PostMapping
  public String post(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("password_repeat") String passwordRepeat) {
    if (password != null && passwordRepeat != null && password.equals(passwordRepeat)) {
      Optional<User> user =  userDetailsService.findUser(username);
      
      if (!user.isPresent()) {
        User userToPersist = new User();
        userToPersist.setUsername(username);
        userToPersist.setPassword(passwordEncoder.encode(password));
        userToPersist.setRole("ROLE_USER");
        
        userDetailsService.add(userToPersist);
        return "redirect:/computer-database/login";
      }

    }
    return "redirect:/computer-database/register";
  }
}
