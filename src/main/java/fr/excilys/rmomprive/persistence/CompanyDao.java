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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.excilys.rmomprive.exception.DaoException;
import fr.excilys.rmomprive.exception.ImpossibleActionException;
import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.pagination.Page;

@Component
public class CompanyDao implements IDao<Company> {
  private static final String SELECT_BY_ID_QUERY = "SELECT id, name FROM company WHERE ID = ?";
  private static final String SELECT_BY_NAME_QUERY = "SELECT id, name FROM company WHERE name LIKE ?";
  private static final String SELECT_ALL_QUERY = "SELECT id, name FROM company";
  private static final String CHECK_EXISTENCE_QUERY = "SELECT COUNT(id) AS count FROM company WHERE id = ?";
  private static final String DELETE_COMPANY_BY_ID_QUERY = "DELETE FROM company WHERE company.id = ?";
  private static final String DELETE_COMPUTERS_BY_COMPANY_ID_QUERY = "DELETE FROM computer WHERE computer.company_id = ?";

  private static final String FIELD_ID = "id";
  private static final String FIELD_NAME = "name";
  private static final String FIELD_COUNT = "count";

  private Database database;

  @Autowired
  public CompanyDao(Database database) {
    this.database = database;
  }

  /**
   * Create a Company object from a ResultSet given by a database result.
   * 
   * @param resultSet The ResultSet value
   * @return The Company object
   * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  private Company createFromResultSet(ResultSet resultSet) throws SQLException {
    int id = resultSet.getInt(FIELD_ID);
    String name = resultSet.getString(FIELD_NAME);

    return new Company(id, name);
  }

  @Override
  public Optional<Company> getById(long id) throws DaoException {
    try (Connection connection = database.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        return Optional.of(createFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      throw new DaoException();
    }

    return Optional.empty();
  }

  @Override
  public List<Company> getByName(String name) throws DaoException {
    List<Company> companies = new ArrayList<>();

    try (Connection connection = database.getConnection()) {
      name = "%" + name + "%";

      PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_QUERY);
      statement.setString(1, name);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        companies.add(createFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      throw new DaoException();
    }

    return companies;
  }

  @Override
  public Collection<Company> getAll() throws DaoException {
    List<Company> result = new ArrayList<>();

    try (Connection connection = database.getConnection()) {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);

      while (resultSet.next()) {
        result.add(createFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      throw new DaoException();
    }

    return result;
  }

  @Override
  public Optional<Company> add(Company company) {
    throw new ImpossibleActionException();
  }

  @Override
  public Collection<Company> addAll(Collection<Company> companies) {
    throw new ImpossibleActionException();
  }

  @Override
  public Company update(Company company) {
    throw new ImpossibleActionException();
  }

  @Override
  public boolean delete(Company company) throws DaoException {
    return deleteById(company.getId());
  }

  @Override
  public boolean deleteById(long id) throws DaoException {
    Connection connection = null;

    try {
      connection = database.getConnection();
      connection.setAutoCommit(false);

      PreparedStatement deleteComputers = connection
          .prepareStatement(DELETE_COMPUTERS_BY_COMPANY_ID_QUERY);
      deleteComputers.setLong(1, id);
      deleteComputers.executeUpdate();

      PreparedStatement deleteCompany = connection.prepareStatement(DELETE_COMPANY_BY_ID_QUERY);
      deleteCompany.setLong(1, id);
      deleteCompany.executeUpdate();

      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      if (connection != null) {
        try {
          connection.rollback();
        } catch (SQLException e1) {
          throw new DaoException();
        }
      }

      throw new DaoException();
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          throw new DaoException();
        }
      }
    }

    return true;
  }

  @Override
  public boolean deleteByIds(List<Long> ids) throws DaoException {
    throw new ImpossibleActionException();
  }

  @Override
  public boolean checkExistenceById(long id) {
    int count = 0;

    try (Connection connection = database.getConnection()) {
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
  public Page<Company> getPage(Page page) throws InvalidPageIdException {
    throw new ImpossibleActionException();
  }
}
