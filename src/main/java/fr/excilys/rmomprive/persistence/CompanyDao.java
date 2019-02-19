package fr.excilys.rmomprive.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import fr.excilys.rmomprive.exception.ImpossibleActionException;
import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.pagination.Page;

public class CompanyDao implements IDao<Company> {
	private static final String SELECT_BY_ID_QUERY = "SELECT id, name FROM company WHERE ID = ?";
	private static final String SELECT_ALL_QUERY = "SELECT id, name FROM company";
	private static final String CHECK_EXISTENCE_QUERY = "SELECT COUNT(id) AS count FROM company WHERE id = ?";
	
	private static final String FIELD_ID = "id";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_COUNT = "count";
	
	private static CompanyDao instance;
	
	private CompanyDao() {
		
	}
	
	private Company createFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt(FIELD_ID);
        String name = resultSet.getString(FIELD_NAME);
        
        return new Company(id, name);
	}
	
	@Override
	public Optional<Company> getById(long objectId) throws SQLException {
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
	public Collection<Company> getAll() throws SQLException {
		List<Company> result = new ArrayList<>();
		
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
	public Optional<Company> add(Company object) {
		throw new ImpossibleActionException();
	}

	@Override
	public Collection<Company> addAll(Collection<Company> objects) {
		throw new ImpossibleActionException();
	}

	@Override
	public Company update(Company object) {
		throw new ImpossibleActionException();
	}

	@Override
	public boolean delete(Company object) {
		throw new ImpossibleActionException();
	}
	
	@Override
	public boolean deleteById(long id) {
		throw new ImpossibleActionException();
	}

	@Override
	public boolean checkExistenceById(long id) {
		int count = 0;
		
		try (Connection connection = Database.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(CHECK_EXISTENCE_QUERY);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
                count = resultSet.getInt(FIELD_COUNT);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (count != 0);
	}

	@Override
	public int getRowCount() {
		throw new ImpossibleActionException();
	}

	@Override
	public int getPageCount(int pageSize) {
		throw new ImpossibleActionException();
	}

	@Override
	public Page<Company> getPage(int pageId, int pageSize) throws InvalidPageIdException {
		throw new ImpossibleActionException();
	}
	
	public static CompanyDao getInstance() {
		if (instance == null)
			instance = new CompanyDao();
		return instance;
	}
}
