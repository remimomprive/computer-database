package com.excilys.rmomprive.computerdatabase.persistence;

import java.util.Optional;

import com.excilys.rmomprive.computerdatabase.core.User;

public interface IUserDao {
  /**
   * @param username The username
   * @return The User, if exists
   */
  public Optional<User> getByUsername(String username);
  
  /**
   * @param computer The entity we want to persist
   * @return The persisted entity if it was successfully added, no data if an error persisting the
   *         entity happened
   */
  Optional<User> add(User user);
}
