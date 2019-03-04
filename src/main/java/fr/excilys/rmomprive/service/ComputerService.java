package fr.excilys.rmomprive.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import fr.excilys.rmomprive.dto.IDto;
import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.model.ComputerDetails;
import fr.excilys.rmomprive.pagination.Page;
import fr.excilys.rmomprive.persistence.CompanyDao;
import fr.excilys.rmomprive.persistence.ComputerDao;
import fr.excilys.rmomprive.validation.ComputerValidator;

public class ComputerService implements IService<Computer> {
  private static ComputerService instance;

  /**
   * Public constructor for mockito.
   */
  public ComputerService() {

  }

  @Override
  public Optional<Computer> getById(long id) throws SQLException {
    return this.getComputerDao().getById(id);
  }

  @Override
  public List<Computer> getByName(String name) throws SQLException {
    return this.getComputerDao().getByName(name);
  }

  /**
   * @param id The computer id
   * @return The computer details of a specific computer
   * @throws SQLException If an error accessing the database happened
   */
  public Optional<ComputerDetails> getDetailsByComputerId(final int id) throws SQLException {
    Optional<Computer> computer = this.getComputerDao().getById(id);

    if (!computer.isPresent()) {
      return Optional.empty();
    } else {
      Optional<Company> company = this.getCompanyDao().getById(computer.get().getCompany().getId());

      if (company.isPresent()) {
        return Optional.of(new ComputerDetails(computer.get(), company.get()));
      } else {
        return Optional.of(new ComputerDetails(computer.get(), null));
      }
    }
  }

  @Override
  public Collection<Computer> getAll() throws SQLException {
    return this.getComputerDao().getAll();
  }

  @Override
  public Optional<Computer> add(Computer object) throws SQLException {
    ComputerValidator.validate(object);
    return this.getComputerDao().add(object);
  }

  @Override
  public Collection<Computer> addAll(Collection<Computer> objects) {
    return this.getComputerDao().addAll(objects);
  }

  @Override
  public Computer update(Computer object) throws SQLException {
    return this.getComputerDao().update(object);
  }

  @Override
  public boolean delete(Computer object) throws SQLException {
    return this.getComputerDao().delete(object);
  }

  @Override
  public boolean deleteById(long id) throws SQLException {
    return this.getComputerDao().deleteById(id);
  }

  @Override
  public boolean deleteByIds(List<Long> ids) throws SQLException {
    return this.getComputerDao().deleteByIds(ids);
  }

  @Override
  public boolean checkExistenceById(long id) throws SQLException {
    return this.getComputerDao().checkExistenceById(id);
  }

  @Override
  public int getRowCount() throws SQLException {
    return this.getComputerDao().getRowCount();
  }

  public int getRowCount(String search) throws SQLException {
    return this.getComputerDao().getRowCount(search);
  }

  @Override
  public int getPageCount(int pageSize) throws SQLException {
    return this.getComputerDao().getPageCount(pageSize);
  }
  
  public int getPageCount(int pageSize, String search) throws SQLException {
    return this.getComputerDao().getPageCount(pageSize, search);
  }

  @Override
  public Page<Computer> getPage(int pageId, int pageSize)
      throws InvalidPageIdException, InvalidPageSizeException, SQLException {
    return this.getComputerDao().getPage(new Page<Computer>(pageId, pageSize));
  }
  
  public Page<Computer> getByNameOrCompanyName(int pageId, int pageSize, String name,
      String orderBy, String orderDirection) throws SQLException, InvalidPageSizeException, InvalidPageIdException {
    return this.getComputerDao().getByNameOrCompanyName(new Page<Computer>(pageId, pageSize), name, orderBy, orderDirection);
  }

  /**
   */
  public static ComputerService getInstance() {
    if (instance == null) {
      instance = new ComputerService();
    }

    return instance;
  }

  public CompanyDao getCompanyDao() {
    return CompanyDao.getInstance();
  }

  public ComputerDao getComputerDao() {
    return ComputerDao.getInstance();
  }
}
