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
import java.util.Optional;

import fr.excilys.rmomprive.exception.ImpossibleActionException;
import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.pagination.Page;

public class ComputerDao implements IDao<Computer> {
	private static final String SELECT_BY_ID_QUERY = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE ID = ?";
	private static final String SELECT_ALL_QUERY = "SELECT id, name, introduced, discontinued, company_id FROM computer";
	private static final String DELETE_QUERY = "DELETE FROM computer where id = ?";
	private static final String INSERT_QUERY = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
	private static final String CHECK_EXISTENCE_QUERY = "SELECT COUNT(id) AS count FROM computer WHERE id = ?";
	private static final String UPDATE_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM computer";
	private static final String SELECT_PAGE_QUERY = "SELECT id, name, introduced, discontinued, company_id FROM computer LIMIT ? OFFSET ?";
	
	private static final String FIELD_ID = "id";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_INTRODUCED = "introduced";
	private static final String FIELD_DISCONTINUED = "discontinued";
	private static final String FIELD_COMPANY_ID = "company_id";
	private static final String FIELD_COUNT = "count";
	
	private static ComputerDao instance;
	
	private Computer createFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt(FIELD_ID);
        String name = resultSet.getString(FIELD_NAME);
        Timestamp introduced = resultSet.getTimestamp(FIELD_INTRODUCED);
        Timestamp discontinued =  resultSet.getTimestamp(FIELD_DISCONTINUED);
        int companyId = resultSet.getInt(FIELD_COMPANY_ID);
        
        return new Computer(id, name, introduced, discontinued, companyId);
	}
	
	@Override
	public Optional<Computer> getById(long objectId) throws SQLException {
		try (Connection connection = Database.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
			statement.setLong(1, objectId);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
            	return Optional.of(createFromResultSet(resultSet));
            }
		}
		
		return Optional.empty();
	}

	@Override
	public Collection<Computer> getAll() throws SQLException {
		List<Computer> result = new ArrayList<>();
		
		try (Connection connection = Database.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			
			while (resultSet.next()) {
                result.add(createFromResultSet(resultSet));
            }
		}
		
		return result;
	}

	@Override
	public Optional<Computer> add(Computer object) throws SQLException {
		try (Connection connection = Database.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, object.getName());
			statement.setObject(2, object.getIntroduced());
			statement.setObject(3, object.getDiscontinued());
			statement.setLong(4, object.getCompanyId());
			
			statement.executeUpdate();
			
			 ResultSet rs = statement.getGeneratedKeys();
             if(rs.next()) {
                 object.setId(rs.getInt(1));
                 return Optional.of(object);
             }
		}
		
		return Optional.empty();
	}

	@Override
	public Collection<Computer> addAll(Collection<Computer> objects) {
		throw new ImpossibleActionException();
	}

	@Override
	public Computer update(Computer object) throws SQLException {
		try (Connection connection = Database.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
			statement.setString(1, object.getName());
			statement.setObject(2, object.getIntroduced());
			statement.setObject(3, object.getDiscontinued());
			statement.setLong(4, object.getCompanyId());
			statement.setLong(5, object.getId());
			statement.executeUpdate();
			
			return object;
		}
	}

	@Override
	public boolean delete(Computer object) throws SQLException {
		return deleteById(object.getId());
	}
	
	@Override
	public boolean deleteById(long id) throws SQLException {
		try (Connection connection = Database.getConnection()){
			PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
			statement.setLong(1, id);
			
			return (statement.executeUpdate() != 0);
		}
	}

	@Override
	public boolean checkExistenceById(long id) throws SQLException {
		int count = 0;
		
		try (Connection connection = Database.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(CHECK_EXISTENCE_QUERY);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
                count = resultSet.getInt(FIELD_COUNT);
            }
		}
		
		return (count != 0);
	}
	
	/**
	 * Returns the number of computers inside the database
	 * @throws SQLException 
	 */
	@Override
	public int getRowCount() throws SQLException {
		int count = -1;
		
		try (Connection connection = Database.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(COUNT_QUERY);
			
			while (resultSet.next()) {
                count = resultSet.getInt(FIELD_COUNT);
            }
		}
		
		return count;
	}
	
	/**
	 * Returns the number of pages with the specified size
	 * @throws SQLException 
	 */
	public int getPageCount(int pageSize) throws SQLException {
		return (int) Math.round((1.0 * this.getRowCount()) / pageSize);
	}
	
	/**
	 * Returns the specific page
	 * @param pageId the page id we want to retrieve
	 * @param pageSize
	 * @return The specified spage
	 * @throws SQLException 
	 */
	public Page<Computer> getPage(int pageId, int pageSize) throws InvalidPageIdException, InvalidPageSizeException, SQLException {
		// Compute the page count
		int pageCount = getPageCount(pageSize);
		
		// The page size should not be < 1
		if (pageSize <= 0)
			throw new InvalidPageSizeException();
		// The page id is between 1 and pageCount (a computed value)
		else if (pageId <= 0 || pageId > pageCount)
			throw new InvalidPageIdException();
		else {
			int offset = (pageId - 1) * pageSize;
			
			List<Computer> computers = new ArrayList<>();
			
			try (Connection connection = Database.getConnection()) {
				// Retrieve the page content
				PreparedStatement statement = connection.prepareStatement(SELECT_PAGE_QUERY);
				statement.setInt(1, pageSize);
				statement.setInt(2, offset);
				ResultSet resultSet = statement.executeQuery();
				
				while (resultSet.next()) {
					computers.add(createFromResultSet(resultSet));
	            }
			}
			
			// Return the page
			return new Page<Computer>(computers, pageId, pageId > 1, pageId < pageCount);
		}
	}
	
	public static ComputerDao getInstance() {
		if (instance == null)
			instance = new ComputerDao();
		return instance;
	}
}