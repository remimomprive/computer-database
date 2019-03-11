package fr.excilys.rmomprive.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Database {
  private DataSource dataSource;
  
  @Autowired
  public Database(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public Connection getConnection() throws SQLException {
   return dataSource.getConnection();
  }
}