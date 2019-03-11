package fr.excilys.rmomprive.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.pagination.Page;
import fr.excilys.rmomprive.persistence.CompanyDao;

@Service
public class CompanyService implements ICompanyService {

  @Autowired
  private CompanyDao companyDao;

  /**
   * Private constructor for singleton.
   */
  private CompanyService() {

  }

  @Override
  public Optional<Company> getById(long id) throws SQLException {
    return companyDao.getById(id);
  }

  @Override
  public List<Company> getByName(String name) throws SQLException {
    return companyDao.getByName(name);
  }

  @Override
  public Collection<Company> getAll() throws SQLException {
    return companyDao.getAll();
  }

  @Override
  public Optional<Company> add(Company object) {
    return companyDao.add(object);
  }

  @Override
  public Collection<Company> addAll(Collection<Company> objects) {
    return companyDao.addAll(objects);
  }

  @Override
  public Company update(Company object) {
    return companyDao.update(object);
  }

  @Override
  public boolean delete(Company object) throws SQLException {
    return companyDao.delete(object);
  }

  @Override
  public boolean deleteById(long id) throws SQLException {
    return companyDao.deleteById(id);
  }

  @Override
  public boolean deleteByIds(List<Long> ids) throws SQLException {
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
  public Page<Company> getPage(int pageId, int pageSize) throws InvalidPageIdException {
    return companyDao.getPage(new Page<Company>(pageId, pageSize));
  }
}
