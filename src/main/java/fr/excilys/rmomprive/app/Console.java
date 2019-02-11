package fr.excilys.rmomprive.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.excilys.rmomprive.persistence.Database;

/**
 * Hello world!
 *
 */
public class Console 
{
    public static void main( String[] args )
    {
        try {
        	Connection connection = Database.getConnection();
        	Statement statement = connection.createStatement();
        	
        	ResultSet rs = statement.executeQuery("SELECT * FROM company");
            while (rs.next()) {
            	int id = rs.getInt("id");
                String name = rs.getString("name");
                
            	System.out.println(id + " " + name);
            }
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
    }
}
