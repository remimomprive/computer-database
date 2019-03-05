package fr.excilys.rmomprive.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Database {
  private static HikariConfig hikariConfig = new HikariConfig("hikari.properties");
  private static DataSource dataSource = new HikariDataSource(hikariConfig);
  private static Connection connection;

  /**
   * This class only provides static method.
   */
  private Database() {
  }

  /**
   * @return The instance of Connection in memory.
   * @throws SQLException If an error accessing the database happened.
   */
  public static Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
      connection = dataSource.getConnection();
    }
    return connection;
  }
}