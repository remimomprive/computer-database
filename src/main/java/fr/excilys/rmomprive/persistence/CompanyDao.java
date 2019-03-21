package fr.excilys.rmomprive.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import fr.excilys.rmomprive.exception.ImpossibleActionException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.model.QCompany;
import fr.excilys.rmomprive.model.QComputer;
import fr.excilys.rmomprive.pagination.Page;

@Component
public class CompanyDao extends Dao<Company> implements ICompanyDao {
private LocalSessionFactoryBean localSessionFactoryBean;

  public CompanyDao(LocalSessionFactoryBean localSessionFactoryBean) {
    this.localSessionFactoryBean = localSessionFactoryBean;
  }

  @Override
  public Optional<Company> getById(long id) {
    Session session = getSession(localSessionFactoryBean);
    Company company = getQuery(session).from(QCompany.company).where(QCompany.company.id.eq(id)).fetchOne();
    session.close();
    
    return Optional.ofNullable(company);
  }

  @Override
  public List<Company> getByName(String name) throws DataAccessException {
    Session session = getSession(localSessionFactoryBean);
    List<Company> result = getQuery(session).from(QCompany.company).where(QCompany.company.name.eq(name)).fetch();
    session.close();
    
    return result;
  }

  @Override
  public List<Company> getAll() throws DataAccessException {
    Session session = getSession(localSessionFactoryBean);
    List<Company> result = getQuery(session).from(QCompany.company).fetch();
    session.close();
    
    return result;
  }

  @Override
  public Optional<Company> add(Company company) {
    throw new ImpossibleActionException();
  }

  @Override
  public List<Company> addAll(List<Company> companies) {
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
    Session session = getSession(localSessionFactoryBean);
    getQueryFactory(session).delete(QComputer.computer).where(QComputer.computer.company.id.eq(id));
    getQueryFactory(session).delete(QCompany.company).where(QCompany.company.id.eq(id));
    session.close();
    
    return true;
  }

  @Override
  public boolean deleteByIds(List<Long> ids) throws DataAccessException {
    throw new ImpossibleActionException();
  }

  @Override
  public boolean checkExistenceById(long id) {
    Session session = getSession(localSessionFactoryBean);
    boolean result = (getQuery(session).from(QCompany.company).where(QCompany.company.id.eq(id)).fetchCount() > 0);
    session.close();
    
    return result;
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
}
