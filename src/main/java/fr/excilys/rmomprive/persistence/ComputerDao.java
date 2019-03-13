package fr.excilys.rmomprive.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import fr.excilys.rmomprive.exception.ImpossibleActionException;
import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.pagination.Page;

@Component
public class ComputerDao implements IDao<Computer> {
  private static final String SELECT_BY_ID_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = company_id  WHERE computer.id = ?";
  private static final String SELECT_BY_NAME_QUERY = "SELECT id, name FROM computer WHERE name LIKE ?";
  private static final String SELECT_BY_NAME_OR_COMPANY_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = company_id WHERE UPPER(computer.name) LIKE ? OR UPPER(company.name) LIKE ? "
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

  public static Map<String, String> orderColumns;
  public static Map<String, String> orderDirections;
  private static String DEFAULT_ORDER_BY = "computer.name";
  private static String DEFAULT_ORDER_DIRECTION = "ASC";

  private JdbcTemplate jdbcTemplate;

  static {
    orderColumns = new HashMap<>();
    orderDirections = new HashMap<>();

    orderColumns.put("name", FIELD_NAME);
    orderColumns.put("introduced", FIELD_INTRODUCED);
    orderColumns.put("discontinued", FIELD_DISCONTINUED);
    orderColumns.put("company_name", FIELD_COMPANY_NAME);

    orderDirections.put("asc", "ASC");
    orderDirections.put("desc", "DESC");
  }

  private ComputerDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Computer> getById(long id) throws DataAccessException {
    Computer computer = (Computer) jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY,
        new Object[] { id }, new ComputerDao.ComputerMapper());
    return Optional.ofNullable(computer);
  }

  @Override
  public List<Computer> getByName(String name) throws DataAccessException {
    return jdbcTemplate.query(SELECT_BY_NAME_QUERY, new ComputerDao.ComputerMapper(),
        "%" + name + "%");
  }

  @Override
  public Collection<Computer> getAll() throws DataAccessException {
    return jdbcTemplate.query(SELECT_ALL_QUERY, new ComputerDao.ComputerMapper());
  }

  @Override
  public Optional<Computer> add(Computer computer) throws DataAccessException {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    if (jdbcTemplate.update(getInsertQueryPreparedStatementCreator(computer), keyHolder) > 0) {
      computer.setId(keyHolder.getKey().longValue());
    }

    return Optional.ofNullable(computer);
  }

  private PreparedStatementCreator getInsertQueryPreparedStatementCreator(Computer computer) {
    return new PreparedStatementCreator() {
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection
            .prepareStatement((computer.getCompany() != null) ? ComputerDao.INSERT_QUERY
                : ComputerDao.INSERT_QUERY_WITHOUT_COMPANY, new String[] { "id" });
        statement.setString(1, computer.getName());
        statement.setObject(2, computer.getIntroduced());
        statement.setObject(3, computer.getDiscontinued());

        if (computer.getCompany() != null) {
          statement.setLong(4, computer.getCompany().getId());
        }

        return statement;
      }
    };
  }

  @Override
  public Collection<Computer> addAll(Collection<Computer> objects) {
    throw new ImpossibleActionException();
  }

  @Override
  public Computer update(Computer computer) throws DataAccessException {
    jdbcTemplate.update(UPDATE_QUERY, computer.getName(), computer.getIntroduced(),
        computer.getDiscontinued(),
        (computer.getCompany() != null) ? computer.getCompany().getId() : null, computer.getId());

    return computer;
  }

  @Override
  public boolean delete(Computer computer) throws DataAccessException {
    return deleteById(computer.getId());
  }

  @Override
  public boolean deleteById(long id) throws DataAccessException {
    jdbcTemplate.update(DELETE_QUERY, id);
    return true;
  }

  @Override
  public boolean deleteByIds(List<Long> ids) throws DataAccessException {
    // Get the amount of ?s
    StringBuilder idStringBuilder = new StringBuilder();
    for (int i = 0; i < ids.size() - 1; i++) {
      idStringBuilder.append("?, ");
    }
    idStringBuilder.append("?");

    // Create the query string
    String deleteListQueryWithParams = DELETE_LIST_QUERY.replace("?", idStringBuilder.toString());

    // Execute the query
    jdbcTemplate.update(new PreparedStatementCreator() {
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(deleteListQueryWithParams);
        for (int i = 1; i <= ids.size() - 1; i++) {
          statement.setLong(i, ids.get(i - 1));
        }
        return statement;
      }
    });

    return true;
  }

  @Override
  public boolean checkExistenceById(long id) throws DataAccessException {
    return (jdbcTemplate.queryForObject(CHECK_EXISTENCE_QUERY, Integer.class, id) > 0);
  }

  @Override
  public int getRowCount() throws DataAccessException {
    return jdbcTemplate.queryForObject(COUNT_QUERY, Integer.class);
  }

  public int getRowCount(String search) throws DataAccessException {
    return jdbcTemplate.queryForObject(COUNT_QUERY_SEARCH, Integer.class, "%" + search + "%", "%" + search + "%");
  }

  @Override
  public int getPageCount(int pageSize) throws DataAccessException {
    return (int) Math.ceil((1.0 * this.getRowCount()) / pageSize);
  }

  public int getPageCount(int pageSize, String search) throws DataAccessException {
    return (int) Math.ceil((1.0 * this.getRowCount(search)) / pageSize);
  }

  private Page<Computer> getPage(Page<Computer> page, String query, String[] parameters)
      throws DataAccessException, InvalidPageSizeException, InvalidPageIdException {
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

        List<Object> args = new ArrayList<>();
        args.addAll(Arrays.asList(parameters));
        args.add(page.getPageSize());
        args.add(offset);
        
        List<Computer> computers = jdbcTemplate.query(query, args.toArray(), new ComputerDao.ComputerMapper());

        // Return the page
        return new Page<Computer>(computers, page.getPageId(), page.getPageSize(),
            page.getPageId() > 1, page.getPageId() < pageCount);
      }
    }

    return new Page<Computer>();
  }

  @Override
  public Page<Computer> getPage(Page<Computer> page)
      throws InvalidPageIdException, InvalidPageSizeException, DataAccessException {
    return this.getPage(page, SELECT_PAGE_QUERY, new String[] {});
  }

  public Page<Computer> getByNameOrCompanyName(Page<Computer> page, String name, String orderBy,
      String orderDirection)
      throws DataAccessException, InvalidPageSizeException, InvalidPageIdException {
    name = "%" + name.toUpperCase() + "%";

    String orderByMap = ComputerDao.orderColumns.get(orderBy);
    String orderDirectionMap = ComputerDao.orderDirections.get(orderDirection);

    String selectByNameOrCompany = SELECT_BY_NAME_OR_COMPANY_QUERY
        .replace(":order_by:", orderByMap != null ? orderByMap : DEFAULT_ORDER_BY)
        .replace(":order_direction:",
            orderDirectionMap != null ? orderDirectionMap : DEFAULT_ORDER_DIRECTION);

    return this.getPage(page, selectByNameOrCompany, new String[] { name, name });
  }

  private static class ComputerMapper implements RowMapper<Computer> {

    @Override
    public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
      int id = resultSet.getInt(FIELD_ID);
      String name = resultSet.getString(FIELD_NAME);
      Timestamp introducedTimestamp = resultSet.getTimestamp(FIELD_INTRODUCED);
      Timestamp discontinuedTimestamp;

      discontinuedTimestamp = resultSet.getTimestamp(FIELD_DISCONTINUED);

      int companyId = resultSet.getInt(FIELD_COMPANY_ID);
      String companyName = resultSet.getString(FIELD_COMPANY_NAME);

      LocalDate introduced = (introducedTimestamp != null)
          ? introducedTimestamp.toLocalDateTime().toLocalDate()
          : null;
      LocalDate discontinued = (discontinuedTimestamp != null)
          ? discontinuedTimestamp.toLocalDateTime().toLocalDate()
          : null;

      Company company = null;
      if (companyName != null) {
        company = new Company(companyId, companyName);
      }

      return new Computer(id, name, introduced, discontinued, company);
    }

  }
}