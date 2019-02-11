package fr.excilys.rmomprive.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.excilys.rmomprive.model.Company;

public class CompanyDao implements IDao<Company> {
	private static final String SELECT_ALL_QUERY = "SELECT * FROM company";
	
	@Override
	public Company getById(int id) {
		return null;
	}

	@Override
	public Collection<Company> getAll() {
		List<Company> result = new ArrayList<>();
		
		try {
			Connection connection = Database.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			
			while (resultSet.next()) {
            	int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                result.add(new Company(id, name));
            }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
