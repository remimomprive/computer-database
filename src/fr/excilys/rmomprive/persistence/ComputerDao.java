package fr.excilys.rmomprive.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.excilys.rmomprive.exception.ImpossibleActionException;
import fr.excilys.rmomprive.model.Computer;

public class ComputerDao implements IDao<Computer> {
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM computer WHERE ID = ?";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM computer";
	private static final String DELETE_QUERY = "DELETE FROM computer where id = ?";
	private static final String INSERT_QUERY = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
	private static final String CHECK_EXISTENCE_QUERY = "SELECT COUNT(id) AS count FROM company WHERE id = ?";
	
	private static final String FIELD_ID = "id";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_INTRODUCED = "introduced";
	private static final String FIELD_DISCONTINUED = "discontinued";
	private static final String FIELD_COMPANY_ID = "company_id";
	private static final String FIELD_COUNT = "count";
	
	private Computer createFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt(FIELD_ID);
        String name = resultSet.getString(FIELD_NAME);
        Timestamp introduced = (Timestamp) resultSet.getObject(FIELD_INTRODUCED);
        Timestamp discontinued = (Timestamp) resultSet.getObject(FIELD_DISCONTINUED);
        int companyId = resultSet.getInt(FIELD_COMPANY_ID);
        
        // Company company = CompanyController.getInstance().getById(companyId);
        
        return new Computer(id, name, introduced, discontinued, companyId);
	}
	
	@Override
	public Computer getById(int objectId) {
		Computer result = null;
		
		try {
			Connection connection = Database.getConnection();
			
			PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
			statement.setInt(1, objectId);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
            	result = createFromResultSet(resultSet);
            }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Collection<Computer> getAll() {
		List<Computer> result = new ArrayList<>();
		
		try {
			Connection connection = Database.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			
			while (resultSet.next()) {
                result.add(createFromResultSet(resultSet));
            }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Computer add(Computer object) {
		Connection connection;
		try {
			connection = Database.getConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, object.getName());
			statement.setObject(2, object.getIntroduced());
			statement.setObject(3, object.getDiscontinued());
			statement.setInt(4, object.getCompanyId());
			
			statement.executeUpdate();
			
			 ResultSet rs = statement.getGeneratedKeys();
             if(rs.next()) {
                 int insertedId = rs.getInt(1);
                 object.setId(insertedId);
                 return object;
             }
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Collection<Computer> addAll(Collection<Computer> objects) {
		throw new ImpossibleActionException();
	}

	@Override
	public Computer update(Computer object) {
		throw new ImpossibleActionException();
	}

	@Override
	public boolean delete(Computer object) {
		return deleteById(object.getId());
	}
	
	@Override
	public boolean deleteById(int id) {
		Connection connection;
		try {
			connection = Database.getConnection();
			PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
			statement.setInt(1, id);
			
			return (statement.executeUpdate() != 0);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkExistenceById(int id) {
		int count = 0;
		
		try {
			Connection connection = Database.getConnection();
			
			PreparedStatement statement = connection.prepareStatement(CHECK_EXISTENCE_QUERY);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
                count = resultSet.getInt(FIELD_COUNT);
            }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (count != 0);
	}
}
