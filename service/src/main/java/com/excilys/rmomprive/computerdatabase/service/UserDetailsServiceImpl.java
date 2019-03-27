package com.excilys.rmomprive.computerdatabase.service;

import java.util.Optional;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.rmomprive.computerdatabase.core.User;
import com.excilys.rmomprive.computerdatabase.persistence.IUserDao;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private IUserDao userDao;

  public UserDetailsServiceImpl(IUserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<User> user = userDao.getByUsername(username);
    if (!user.isPresent()) {
      throw new UsernameNotFoundException(username);
    }

    return org.springframework.security.core.userdetails.User.builder()
        .username(username)
        .password(user.get().getPassword())
        .authorities(AuthorityUtils.createAuthorityList(user.get().getRole()))
        .build();
  }

  public Optional<User> findUser(String username) {
    return userDao.getByUsername(username);
  }

  public Optional<User> add(User user) {
    return userDao.add(user);
  }

}
