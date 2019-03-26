package com.excilys.rmomprive.computerdatabase.persistence;

import java.util.Optional;

import org.hibernate.Session;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

import com.excilys.rmomprive.computerdatabase.core.QUser;
import com.excilys.rmomprive.computerdatabase.core.User;

@Component
public class UserDao extends Dao<User> implements IUserDao {
  private LocalSessionFactoryBean localSessionFactoryBean;

  public UserDao(LocalSessionFactoryBean localSessionFactoryBean) {
    this.localSessionFactoryBean = localSessionFactoryBean;
  }

  @Override
  public Optional<User> getByUsername(String username) {
    Session session = getSession(localSessionFactoryBean);
    User user = getQuery(session).from(QUser.user).where(QUser.user.username.eq(username)).fetchOne();
    session.close();

    return Optional.ofNullable(user);
  }

  @Override
  public Optional<User> add(User user) {
    Session session = getSession(localSessionFactoryBean);

    session.save(user);
    session.close();
    System.out.println(user.getId());

    return Optional.of(user);
  }

}
