package fr.excilys.rmomprive.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Database {
  private static HikariConfig hikariConfig = new HikariConfig("src/main/resources/hikari.properties");
  private static DataSource dataSource = new HikariDataSource(hikariConfig);

  /**
   * @return The instance of Connection in memory.
   * @throws SQLException If an error accessing the database happened.
   */
  public static Connection getConnection() throws SQLException {
   return dataSource.getConnection();
  }
}