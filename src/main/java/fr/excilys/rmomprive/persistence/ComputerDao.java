package fr.excilys.rmomprive.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.excilys.rmomprive.exception.ImpossibleActionException;
import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.pagination.Page;

public class ComputerDao implements IDao<Computer> {
  private static final String SELECT_BY_ID_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = company_id  WHERE computer.id = ?";
  private static final String SELECT_BY_NAME_QUERY = "SELECT id, name FROM computer WHERE name LIKE ?";
  private static final String SELECT_BY_NAME_OR_COMPANY_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = company_id WHERE computer.name LIKE ? OR company.name LIKE ? "
      + "ORDER BY :order_by: :order_direction: LIMIT ? OFFSET ?";
  private static final String SELECT_ALL_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = company_id";
  private static final String DELETE_QUERY = "DELETE FROM computer where id = ?";
  private static final String DELETE_LIST_QUERY = "DELETE FROM computer where id IN (?)";
  private static final String INSERT_QUERY = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
  private static final String INSERT_QUERY_WITHOUT_COMPANY = "INSERT INTO computer(name, introduced, discontinued) VALUES(?, ?, ?)";
  private static final String CHECK_EXISTENCE_QUERY = "SELECT COUNT(id) AS count FROM computer WHERE id = ?";
  private static final String UPDATE_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
  private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM computer";
  private static final String COUNT_QUERY_SEARCH = "SELECT COUNT(computer.id) AS count FROM computer LEFT JOIN company ON company.id = company_id WHERE computer.name LIKE ? OR company.name LIKE ?";
  private static final String SELECT_PAGE_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = company_id LIMIT ? OFFSET ?";

  private static final String FIELD_ID = "computer.id";
  private static final String FIELD_NAME = "computer.name";
  private static final String FIELD_INTRODUCED = "computer.introduced";
  private static final String FIELD_DISCONTINUED = "computer.discontinued";
  private static final String FIELD_COMPANY_ID = "computer.company_id";
  private static final String FIELD_COMPANY_NAME = "company.name";
  private static final String FIELD_COUNT = "count";
  
  public static Map<String, String> orderColumns;
  public static Map<String, String> orderDirections;
  private static String DEFAULT_ORDER_BY = "computer.name";
  private static String DEFAULT_ORDER_DIRECTION = "ASC";
  
  private static ComputerDao instance;
  
  static {
    orderColumns = new HashMap();
    orderDirections = new HashMap();
    
    orderColumns.put("name", FIELD_NAME);
    orderColumns.put("introduced", FIELD_INTRODUCED);
    orderColumns.put("discontinued", FIELD_DISCONTINUED);
    orderColumns.put("company_name", FIELD_COMPANY_NAME);
    
    orderDirections.put("asc", "ASC");
    orderDirections.put("desc", "DESC");
  }

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
    Timestamp introducedTimestamp = resultSet.getTimestamp(FIELD_INTRODUCED);
    Timestamp discontinuedTimestamp = resultSet.getTimestamp(FIELD_DISCONTINUED);
    int companyId = resultSet.getInt(FIELD_COMPANY_ID);
    String companyName = resultSet.getString(FIELD_COMPANY_NAME);
    
    LocalDate introduced = (introducedTimestamp != null) ? introducedTimestamp.toLocalDateTime().toLocalDate(): null;
    LocalDate discontinued = (discontinuedTimestamp != null) ? discontinuedTimestamp.toLocalDateTime().toLocalDate(): null;

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
  public List<Computer> getByName(String name) throws SQLException {
    List<Computer> computers = new ArrayList<>();

    try (Connection connection = Database.getConnection()) {
      name = "%" + name + "%";

      PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_QUERY);
      statement.setString(1, name);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        computers.add(createFromResultSet(resultSet));
      }
    }

    return computers;
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
  public boolean deleteByIds(List<Long> ids) throws SQLException {
    try (Connection connection = Database.getConnection()) {
      // Get the amount of ?s
      StringBuilder idStringBuilder = new StringBuilder();
      for (int i = 0; i < ids.size() - 1; i++) {
        idStringBuilder.append("?, ");
      }
      idStringBuilder.append("?");

      // Create the query string
      String deleteListQueryWithParams = DELETE_LIST_QUERY.replace("?", idStringBuilder.toString());

      // Prepare the query
      PreparedStatement statement = connection.prepareStatement(deleteListQueryWithParams);
      for (int i = 0; i < ids.size(); i++) {
        statement.setLong(i + 1, ids.get(i));
      }

      // Execute the query
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
  
  public int getRowCount(String search) throws SQLException {
    int count = -1;

    try (Connection connection = Database.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(COUNT_QUERY_SEARCH);
      search = "%" + search + "%";
      statement.setString(1, search);
      statement.setString(2, search);
      ResultSet resultSet = statement.executeQuery();

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
  
  public int getPageCount(int pageSize, String search) throws SQLException {
    return (int) Math.ceil((1.0 * this.getRowCount(search)) / pageSize);
  }

  private Page<Computer> getPage(Page page, String query, String[] parameters)
      throws SQLException, InvalidPageSizeException, InvalidPageIdException {
    if (page != null) {
      // Compute the page count
      int pageCount = getPageCount(page.getPageSize());

      // The page size should not be < 1
      if (page.getPageSize() <= 0) {
        throw new InvalidPageSizeException();
      } else if (page.getPageId() <= 0 || page.getPageId() > pageCount) { // The page id is between
                                                                          // 1 and pageCount (a
        // computed value)
        throw new InvalidPageIdException();
      } else {
        int offset = (page.getPageId() - 1) * page.getPageSize();

        List<Computer> computers = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
          // Retrieve the page content
          PreparedStatement statement = connection.prepareStatement(query);
          for (int i = 1; i <= parameters.length; i++) {
            statement.setString(i, parameters[i - 1]);
          }
          statement.setInt(parameters.length + 1, page.getPageSize());
          statement.setInt(parameters.length + 2, offset);
          ResultSet resultSet = statement.executeQuery();

          while (resultSet.next()) {
            computers.add(createFromResultSet(resultSet));
          }
        }

        // Return the page
        return new Page<Computer>(computers, page.getPageId(), page.getPageSize(),
            page.getPageId() > 1, page.getPageId() < pageCount);
      }
    }

    return new Page<Computer>();
  }

  @Override
  public Page<Computer> getPage(Page page)
      throws InvalidPageIdException, InvalidPageSizeException, SQLException {
    return this.getPage(page, SELECT_PAGE_QUERY, new String[] {});
  }

  public Page<Computer> getByNameOrCompanyName(Page page, String name, String orderBy, String orderDirection)
      throws SQLException, InvalidPageSizeException, InvalidPageIdException {
    name = "%" + name + "%";
    
    String orderByMap = ComputerDao.orderColumns.get(orderBy);
    String orderDirectionMap = ComputerDao.orderDirections.get(orderDirection);
    
    String selectByNameOrCompany = SELECT_BY_NAME_OR_COMPANY_QUERY
        .replace(":order_by:", orderByMap != null ? orderByMap : DEFAULT_ORDER_BY)
        .replace(":order_direction:", orderDirectionMap != null ? orderDirectionMap : DEFAULT_ORDER_DIRECTION);
    
    return this.getPage(page, selectByNameOrCompany, new String[] {name, name});
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