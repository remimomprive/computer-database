package com.excilys.rmomprive.computerdatabase.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.rmomprive.computerdatabase.core.Company;
import com.excilys.rmomprive.computerdatabase.persistence.Page;
import com.excilys.rmomprive.computerdatabase.persistence.ICompanyDao;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageIdException;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageSizeException;

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
  public Optional<Company> getById(long id) {
    return companyDao.getById(id);
  }

  @Override
  public List<Company> getByName(String name) {
    return companyDao.getByName(name);
  }

  @Override
  public List<Company> getAll() {
    return companyDao.getAll();
  }

  @Override
  public Optional<Company> add(Company company) {
    return companyDao.add(company);
  }

  @Override
  public Company update(Company company) {
    return companyDao.update(company);
  }

  @Override
  public boolean delete(Company company) {
    return companyDao.delete(company);
  }

  @Override
  public boolean deleteById(long id) {
    return companyDao.deleteById(id);
  }

  @Override
  public boolean deleteByIds(List<Long> ids) {
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
  public Page<Company> getPage(int pageId, int pageSize) throws InvalidPageIdException, InvalidPageSizeException {
    return companyDao.getPage(new Page<Company>(pageId, pageSize));
  }
}
