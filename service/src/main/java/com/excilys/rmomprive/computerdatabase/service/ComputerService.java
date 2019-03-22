package com.excilys.rmomprive.computerdatabase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.rmomprive.computerdatabase.core.ComputerDetails;
import com.excilys.rmomprive.computerdatabase.core.Computer;
import com.excilys.rmomprive.computerdatabase.core.Company;
import com.excilys.rmomprive.computerdatabase.persistence.Page;
import com.excilys.rmomprive.computerdatabase.persistence.ICompanyDao;
import com.excilys.rmomprive.computerdatabase.persistence.IComputerDao;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageIdException;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageSizeException;

import com.excilys.rmomprive.computerdatabase.validation.ComputerValidator;

@Service
public class ComputerService implements IComputerService {

  @Autowired
  private IComputerDao computerDao;

  @Autowired
  private ICompanyDao companyDao;

  /**
   * Public constructor for mockito.
   */
  public ComputerService() {

  }

  @Override
  public Optional<Computer> getById(long id) {
    return computerDao.getById(id);
  }

  @Override
  public Optional<ComputerDetails> getDetailsByComputerId(final int id) {
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
  public List<Computer> getAll() {
    return computerDao.getAll();
  }

  @Override
  public Optional<Computer> add(Computer computer) {
    ComputerValidator.validate(computer);
    return computerDao.add(computer);
  }

  @Override
  public Computer update(Computer computer) {
    return computerDao.update(computer);
  }

  @Override
  public boolean delete(Computer computer) {
    return computerDao.delete(computer);
  }

  @Override
  public boolean deleteById(long id) {
    return computerDao.deleteById(id);
  }

  @Override
  public boolean deleteByIds(List<Long> ids) {
    return computerDao.deleteByIds(ids);
  }

  @Override
  public boolean checkExistenceById(long id) {
    return computerDao.checkExistenceById(id);
  }

  @Override
  public int getRowCount() {
    return computerDao.getRowCount("");
  }

  @Override
  public int getRowCount(String search) {
    return computerDao.getRowCount(search);
  }

  @Override
  public int getPageCount(int pageSize) {
    return computerDao.getPageCount(pageSize, "");
  }

  @Override
  public int getPageCount(int pageSize, String search) {
    return computerDao.getPageCount(pageSize, search);
  }

  @Override
  public Page<Computer> getPage(int pageId, int pageSize)
      throws InvalidPageIdException, InvalidPageSizeException {
    return computerDao.getPage(new Page<Computer>(pageId, pageSize));
  }

  @Override
  public Page<Computer> getByNameOrCompanyName(int pageId, int pageSize, String name,
      String orderBy, String orderDirection) throws InvalidPageSizeException, InvalidPageIdException {
    return computerDao.getByNameOrCompanyName(new Page<Computer>(pageId, pageSize), name, orderBy, orderDirection);
  }
}
