package fr.excilys.rmomprive.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Database {
  @Autowired
  private static DataSource dataSource;

  /**
   * @return The instance of Connection in memory.
   * @throws SQLException If an error accessing the database happened.
   */
  public static Connection getConnection() throws SQLException {
   return dataSource.getConnection();
  }
}