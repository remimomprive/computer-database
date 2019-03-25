package com.excilys.rmomprive.computerdatabase.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

import com.excilys.rmomprive.computerdatabase.core.Computer;
import com.excilys.rmomprive.computerdatabase.core.QComputer;

@Component
public class ComputerDao extends Dao<Computer> implements IComputerDao {
  private static final String FIELD_NAME = "computer.name";
  private static final String FIELD_INTRODUCED = "computer.introduced";
  private static final String FIELD_DISCONTINUED = "computer.discontinued";
  private static final String FIELD_COMPANY_NAME = "company.name";

  public static Map<String, String> orderColumns;
  public static Map<String, String> orderDirections;

  private LocalSessionFactoryBean localSessionFactoryBean;

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

  private ComputerDao(LocalSessionFactoryBean localSessionFactoryBean) {
    this.localSessionFactoryBean = localSessionFactoryBean;
  }

  @Override
  public Optional<Computer> getById(long id) {
    Session session = getSession(localSessionFactoryBean);
    Computer computer = getQuery(session).from(QComputer.computer).where(QComputer.computer.id.eq(id)).fetchOne();
    session.close();

    return Optional.ofNullable(computer);
  }

  @Override
  public List<Computer> getAll() {
    Session session = getSession(localSessionFactoryBean);
    List<Computer> computers = getQuery(session).from(QComputer.computer).fetch();
    session.close();

    return computers;
  }

  @Override
  public Optional<Computer> add(Computer computer) {
    Session session = getSession(localSessionFactoryBean);

    session.save(computer);
    session.close();

    return Optional.of(computer);
  }

  @Override
  public Computer update(Computer computer) {
    Session session = getSession(localSessionFactoryBean);
    getQueryFactory(session).update(QComputer.computer).where(QComputer.computer.id.eq(computer.getId())).set(QComputer.computer.name, computer.getName())
        .set(QComputer.computer.introduced, computer.getIntroduced()).set(QComputer.computer.discontinued, computer.getDiscontinued())
        .set(QComputer.computer.company, computer.getCompany()).execute();
    session.close();

    return computer;
  }

  @Override
  public boolean delete(Computer computer) {
    return deleteById(computer.getId());
  }

  @Override
  public boolean deleteById(long id) {
    Session session = getSession(localSessionFactoryBean);
    getDeleteClause(session, QComputer.computer).where(QComputer.computer.id.eq(id)).execute();
    session.close();

    return true;
  }

  @Override
  public boolean deleteByIds(List<Long> ids) {
    Session session = getSession(localSessionFactoryBean);
    getQueryFactory(session).delete(QComputer.computer).where(QComputer.computer.id.in(ids)).execute();
    session.close();

    return true;
  }

  @Override
  public boolean checkExistenceById(long id) {
    Session session = getSession(localSessionFactoryBean);
    boolean result = (getQuery(session).from(QComputer.computer).where(QComputer.computer.id.eq(id)).fetchCount() > 0);
    session.close();

    return result;
  }

  public int getRowCount() {
    Session session = getSession(localSessionFactoryBean);
    int result = (int) getQuery(session).from(QComputer.computer).fetchCount();
    session.close();

    return result;
  }

  public int getRowCount(String search) {
    Session session = getSession(localSessionFactoryBean);
    int result = (int) getQuery(session).from(QComputer.computer).where(QComputer.computer.name.like("%" + search + "%")).fetchCount();
    session.close();

    return result;
  }

  public int getPageCount(int pageSize) {
    return (int) Math.ceil((1.0 * this.getRowCount()) / pageSize);
  }

  public int getPageCount(int pageSize, String search) {
    return (int) Math.ceil((1.0 * this.getRowCount(search)) / pageSize);
  }

  private Page<Computer> getPage(Page<Computer> page, String[] parameters) throws InvalidPageSizeException, InvalidPageIdException {
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

        Session session = getSession(localSessionFactoryBean);

        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(parameters[2], parameters[3]);
        List<Computer> computers = getQuery(session).from(QComputer.computer).where(QComputer.computer.name.like("%" + parameters[0] + "%")).orderBy(orderSpecifier)
            .limit(page.getPageSize()).offset(offset).fetch();

        session.close();

        // Return the page
        return new Page<Computer>(computers, page.getPageId(), page.getPageSize(), page.getPageId() > 1, page.getPageId() < pageCount);
      }
    }

    return new Page<Computer>();
  }

  @Override
  public Page<Computer> getPage(Page<Computer> page) throws InvalidPageIdException, InvalidPageSizeException {
    return this.getPage(page, new String[] { "", "" });
  }

  @Override
  public Page<Computer> getByNameOrCompanyName(Page<Computer> page, String name, String orderBy, String orderDirection)
      throws InvalidPageSizeException, InvalidPageIdException {
    name = "%" + name.toUpperCase() + "%";

    String orderByMap = ComputerDao.orderColumns.get(orderBy);
    String orderDirectionMap = ComputerDao.orderDirections.get(orderDirection);

    return this.getPage(page, new String[] { name, name, orderByMap, orderDirectionMap });
  }

  private OrderSpecifier<?> getOrderSpecifier(String orderBy, String orderDirection) {
    ComparableExpressionBase<?> path;

    switch (orderBy) {
    case FIELD_INTRODUCED:
      path = QComputer.computer.introduced;
      break;
    case FIELD_DISCONTINUED:
      path = QComputer.computer.discontinued;
      break;
    case FIELD_COMPANY_NAME:
      path = QComputer.computer.company.name;
      break;
    case FIELD_NAME:
    default:
      path = QComputer.computer.name;
      break;
    }

    switch (orderDirection) {
    case "DESC":
      return path.desc();
    default:
    case "ASC":
      return path.asc();
    }
  }
}
