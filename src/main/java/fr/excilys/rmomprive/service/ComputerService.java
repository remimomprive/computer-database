package fr.excilys.rmomprive.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.model.ComputerDetails;
import fr.excilys.rmomprive.pagination.Page;
import fr.excilys.rmomprive.persistence.CompanyDao;
import fr.excilys.rmomprive.persistence.ComputerDao;
import fr.excilys.rmomprive.validation.ComputerValidator;

@Service
public class ComputerService implements IComputerService {

  @Autowired
  private ComputerDao computerDao;
  
  @Autowired
  private CompanyDao companyDao;  

  /**
   * Public constructor for mockito.
   */
  public ComputerService() {

  }

  @Override
  public Optional<Computer> getById(long id) throws DataAccessException {
    return computerDao.getById(id);
  }

  @Override
  public List<Computer> getByName(String name) throws DataAccessException {
    return computerDao.getByName(name);
  }

  @Override
  public Optional<ComputerDetails> getDetailsByComputerId(final int id) throws DataAccessException {
    Optional<Computer> computer = computerDao.getById(id);

    if (!computer.isPresent()) {
      return Optional.empty();
    } else {
      Optional<Company> company = companyDao.getById(computer.get().getCompany().getId());

      if (company.isPresent()) {
        return Optional.of(new ComputerDetails(computer.get(), company.get()));
      } else {
        return Optional.of(new ComputerDetails(computer.get(), null));
      }
    }
  }

  @Override
  public Collection<Computer> getAll() throws DataAccessException {
    return computerDao.getAll();
  }

  @Override
  public Optional<Computer> add(Computer computer) throws DataAccessException {
    ComputerValidator.validate(computer);
    return computerDao.add(computer);
  }

  @Override
  public Collection<Computer> addAll(Collection<Computer> computers) {
    return computerDao.addAll(computers);
  }

  @Override
  public Computer update(Computer computer) throws DataAccessException {
    return computerDao.update(computer);
  }

  @Override
  public boolean delete(Computer computer) throws DataAccessException {
    return computerDao.delete(computer);
  }

  @Override
  public boolean deleteById(long id) throws DataAccessException {
    return computerDao.deleteById(id);
  }

  @Override
  public boolean deleteByIds(List<Long> ids) throws DataAccessException {
    return computerDao.deleteByIds(ids);
  }

  @Override
  public boolean checkExistenceById(long id) throws DataAccessException {
    return computerDao.checkExistenceById(id);
  }

  @Override
  public int getRowCount() throws DataAccessException {
    return computerDao.getRowCount();
  }

  @Override
  public int getRowCount(String search) throws DataAccessException {
    return computerDao.getRowCount(search);
  }

  @Override
  public int getPageCount(int pageSize) throws DataAccessException {
    return computerDao.getPageCount(pageSize);
  }
  
  @Override
  public int getPageCount(int pageSize, String search) throws DataAccessException {
    return computerDao.getPageCount(pageSize, search);
  }

  @Override
  public Page<Computer> getPage(int pageId, int pageSize)
      throws InvalidPageIdException, InvalidPageSizeException, DataAccessException {
    return computerDao.getPage(new Page<Computer>(pageId, pageSize));
  }
  
  @Override
  public Page<Computer> getByNameOrCompanyName(int pageId, int pageSize, String name,
      String orderBy, String orderDirection) throws DataAccessException, InvalidPageSizeException, InvalidPageIdException {
    return computerDao.getByNameOrCompanyName(new Page<Computer>(pageId, pageSize), name, orderBy, orderDirection);
  }
}
