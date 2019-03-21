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
import fr.excilys.rmomprive.pagination.Page;
import fr.excilys.rmomprive.persistence.CompanyDao;
import fr.excilys.rmomprive.persistence.ICompanyDao;

@Service
public class CompanyService implements ICompanyService {

  @Autowired
  private ICompanyDao companyDao;

  /**
   * Private constructor for singleton.
   */
  private CompanyService() {

  }

  @Override
  public Optional<Company> getById(long id) throws DataAccessException {
    return companyDao.getById(id);
  }

  @Override
  public List<Company> getByName(String name) throws DataAccessException {
    return companyDao.getByName(name);
  }

  @Override
  public List<Company> getAll() throws DataAccessException {
    return companyDao.getAll();
  }

  @Override
  public Optional<Company> add(Company company) {
    return companyDao.add(company);
  }

  @Override
  public List<Company> addAll(List<Company> companies) {
    return companyDao.addAll(companies);
  }

  @Override
  public Company update(Company company) {
    return companyDao.update(company);
  }

  @Override
  public boolean delete(Company company) throws DataAccessException {
    return companyDao.delete(company);
  }

  @Override
  public boolean deleteById(long id) throws DataAccessException {
    return companyDao.deleteById(id);
  }

  @Override
  public boolean deleteByIds(List<Long> ids) throws DataAccessException {
    return companyDao.deleteByIds(ids);
  }

  @Override
  public boolean checkExistenceById(long id) {
    return companyDao.checkExistenceById(id);
  }

  @Override
  public int getRowCount() {
    return companyDao.getRowCount();
  }

  @Override
  public int getPageCount(int pageSize) {
    return companyDao.getPageCount(pageSize);
  }

  @Override
  public Page<Company> getPage(int pageId, int pageSize) throws InvalidPageIdException, DataAccessException, InvalidPageSizeException {
    return companyDao.getPage(new Page<Company>(pageId, pageSize));
  }
}
