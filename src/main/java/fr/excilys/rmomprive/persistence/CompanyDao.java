package fr.excilys.rmomprive.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.rmomprive.exception.ImpossibleActionException;
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

  private JdbcTemplate jdbcTemplate;

  public CompanyDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Company> getById(long id) {
    Company company = (Company) jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, new Object[] { id },
        new CompanyDao.CompanyMapper());
    return Optional.ofNullable(company);
  }

  @Override
  public List<Company> getByName(String name) throws DataAccessException {
    return jdbcTemplate.query(SELECT_BY_NAME_QUERY, new CompanyDao.CompanyMapper(),
        "%" + name + "%");
  }

  @Override
  public Collection<Company> getAll() throws DataAccessException {
    return jdbcTemplate.query(SELECT_ALL_QUERY, new CompanyDao.CompanyMapper());
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
  public boolean delete(Company company) throws DataAccessException {
    return deleteById(company.getId());
  }

  @Transactional
  @Override
  public boolean deleteById(long id) throws DataAccessException {
    jdbcTemplate.update(DELETE_COMPUTERS_BY_COMPANY_ID_QUERY, id);
    jdbcTemplate.update(DELETE_COMPANY_BY_ID_QUERY, id);
    return true;
  }

  @Override
  public boolean deleteByIds(List<Long> ids) throws DataAccessException {
    throw new ImpossibleActionException();
  }

  @Override
  public boolean checkExistenceById(long id) {
    return (jdbcTemplate.queryForObject(CHECK_EXISTENCE_QUERY, Integer.class, id) > 0);
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
  public Page<Company> getPage(Page<Company> page) throws DataAccessException {
    throw new ImpossibleActionException();
  }

  private static class CompanyMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Company(rs.getLong(FIELD_ID), rs.getString(FIELD_NAME));
    }

  }
}
