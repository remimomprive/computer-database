package fr.excilys.rmomprive.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private static Connection connection;
	
	private static final String DB_IP = "localhost";
	private static final String DB_NAME = "computer-database-db";
	private static final String DB_USER = "admincdb";
	private static final String DB_PASSWORD = "qwerty1234";
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		if(connection == null) {       
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + DB_IP + "/" + DB_NAME, DB_USER, DB_PASSWORD);
		}
		return connection;
	}
}