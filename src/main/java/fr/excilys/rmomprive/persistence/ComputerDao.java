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
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.pagination.Page;

public class ComputerDao implements IDao<Computer> {
  private static final String SELECT_BY_ID_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = company_id  WHERE computer.id = ?";
  private static final String SELECT_ALL_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = company_id";
  private static final String DELETE_QUERY = "DELETE FROM computer where id = ?";
  private static final String INSERT_QUERY = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
  private static final String INSERT_QUERY_WITHOUT_COMPANY = "INSERT INTO computer(name, introduced, discontinued) VALUES(?, ?, ?)";
  private static final String CHECK_EXISTENCE_QUERY = "SELECT COUNT(id) AS count FROM computer WHERE id = ?";
  private static final String UPDATE_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
  private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM computer";
  private static final String SELECT_PAGE_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = company_id LIMIT ? OFFSET ?";
  private static final String FIELD_ID = "computer.id";
  private static final String FIELD_NAME = "computer.name";
  private static final String FIELD_INTRODUCED = "computer.introduced";
  private static final String FIELD_DISCONTINUED = "computer.discontinued";
  private static final String FIELD_COMPANY_ID = "computer.company_id";
  private static final String FIELD_COMPANY_NAME = "company.name";
  private static final String FIELD_COUNT = "count";

  private static ComputerDao instance;

  /**
   * Private contructor for singleton.
   */
  private ComputerDao() {

  }

  /// TODO : find computer by name

  /**
   * Create a Computer object from a ResultSet given by a database result.
   *
   * @param resultSet The ResultSet value
   * @return The Computer object
   * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  private Computer createFromResultSet(ResultSet resultSet) throws SQLException {
    int id = resultSet.getInt(FIELD_ID);
    String name = resultSet.getString(FIELD_NAME);
    Timestamp introduced = resultSet.getTimestamp(FIELD_INTRODUCED);
    Timestamp discontinued = resultSet.getTimestamp(FIELD_DISCONTINUED);
    int companyId = resultSet.getInt(FIELD_COMPANY_ID);
    String companyName = resultSet.getString(FIELD_COMPANY_NAME);

    Company company = null;
    if (companyName != null) {
      company = new Company(companyId, companyName);
    }

    return new Computer(id, name, introduced, discontinued, company);
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
      PreparedStatement statement = (object.getCompany() != null)
          ? connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)
          : connection.prepareStatement(INSERT_QUERY_WITHOUT_COMPANY,
              Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, object.getName());
      statement.setObject(2, object.getIntroduced());
      statement.setObject(3, object.getDiscontinued());

      if (object.getCompany() != null) {
        statement.setLong(4, object.getCompany().getId());
      }

      statement.executeUpdate();

      ResultSet rs = statement.getGeneratedKeys();
      if (rs.next()) {
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
      statement.setObject(4, (object.getCompany() != null) ? object.getCompany().getId() : null);
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
    try (Connection connection = Database.getConnection()) {
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

  @Override
  public int getPageCount(int pageSize) throws SQLException {
    return (int) Math.ceil((1.0 * this.getRowCount()) / pageSize);
  }

  @Override
  public Page<Computer> getPage(int pageId, int pageSize)
      throws InvalidPageIdException, InvalidPageSizeException, SQLException {
    // Compute the page count
    int pageCount = getPageCount(pageSize);

    // The page size should not be < 1
    if (pageSize <= 0) {
      throw new InvalidPageSizeException();
    } else if (pageId <= 0 || pageId > pageCount) { // The page id is between 1 and pageCount (a
                                                    // computed value)
      throw new InvalidPageIdException();
    } else {
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

  /**
   * @return The instance of ComputerDao in memory.
   */
  public static ComputerDao getInstance() {
    if (instance == null) {
      instance = new ComputerDao();
    }
    return instance;
  }
}
