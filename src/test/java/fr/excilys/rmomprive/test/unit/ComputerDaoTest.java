package fr.excilys.rmomprive.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.rmomprive.configuration.AppConfigurationTest;
import fr.excilys.rmomprive.exception.DaoException;
import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.pagination.Page;
import fr.excilys.rmomprive.persistence.ComputerDao;
import fr.excilys.rmomprive.persistence.Database;

public class ComputerDaoTest {
  private static ComputerDao computerDao;
  private static Database database;

  @BeforeClass
  public static void start() {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfigurationTest.class);
    computerDao = context.getBean(ComputerDao.class);
    database = context.getBean(Database.class);
  }

  @Before
  public void beforeTest() throws SQLException {
    Connection connection = database.getConnection();
    Statement statement = connection.createStatement();
    statement.executeUpdate("TRUNCATE TABLE computer;");
    statement.executeUpdate(
        "INSERT INTO computer(name, introduced) VALUES('Inserted 1', '2018-01-10');");
    statement.executeUpdate(
        "INSERT INTO computer(name, introduced) VALUES('Inserted 2', '2019-01-20');");
    statement.executeUpdate("INSERT INTO computer(name) VALUES('Inserted 3');");
  }

  @After
  public void afterTest() throws SQLException {
    Connection connection = database.getConnection();
    Statement statement = connection.createStatement();
    statement.executeUpdate("TRUNCATE TABLE computer;");
  }

  @Test
  public void shouldInsertComputer() throws DaoException {
    Computer computer = new Computer.ComputerBuilder().setName("Computer 1")
        .setIntroduced(LocalDate.now()).build();
    Optional<Computer> insertedComputer = computerDao.add(computer);

    assertTrue(insertedComputer.isPresent());
    assertTrue(insertedComputer.get().getId() != 0);
  }

  @Test
  public void shouldUpdateComputerWithAReference() throws DaoException {
    Computer computer = new Computer.ComputerBuilder().setId(2).setName("Updated").build();
    Computer updatedComputer = computerDao.update(computer);

    assertTrue(updatedComputer.getName().equals("Updated"));
  }

  @Test
  public void shouldFindComputer() throws DaoException {
    Optional<Computer> retrievedComputer = computerDao.getById(1);
    Computer computerToTest = new Computer.ComputerBuilder().setId(1).setName("Inserted 1")
        .setIntroduced(LocalDate.of(2018, 01, 10)).setDiscontinued(null).setCompany(null).build();

    assertEquals(retrievedComputer.get(), computerToTest);
  }

  @Test
  public void shouldDeleteComputer() throws DaoException, SQLException {
    Computer computer = new Computer.ComputerBuilder().setId(1).setName("Updated").build();
    computerDao.delete(computer);

    Statement statement = database.getConnection().createStatement();
    statement.execute("SELECT COUNT(id) AS count FROM computer WHERE computer.id = 1");
    ResultSet resultSet = statement.getResultSet();
    resultSet.next();

    assertEquals(resultSet.getInt("count"), 0);
  }

  @Test
  public void shouldRetrieveComputerCount() throws DaoException {
    assertEquals(computerDao.getRowCount(), 3);
  }

  @Test
  public void shouldRetrieveComputerPage()
      throws DaoException, InvalidPageIdException, InvalidPageSizeException {
    Page<Computer> page = new Page<>(2, 2);
    page = computerDao.getPage(page);

    Computer computerToTest = new Computer.ComputerBuilder().setId(3).setName("Inserted 3")
        .setIntroduced(null).setDiscontinued(null).setCompany(null).build();
    assertEquals(page.getContent().get(0), computerToTest);
  }

  @Test
  public void shouldRetrieveComputerPageCount() throws DaoException {
    assertEquals(computerDao.getPageCount(5), 1);
  }

  @Test(expected = InvalidPageIdException.class)
  public void shouldThrowExceptionWhenRetirevingInvalidPageId()
      throws InvalidPageIdException, InvalidPageSizeException, DaoException {
    Page<Computer> page = new Page<>(-1, 10);
    computerDao.getPage(page);
  }

  @Test(expected = InvalidPageSizeException.class)
  public void shouldThrowExceptionWhenRetirevingInvalidPageSize()
      throws InvalidPageIdException, InvalidPageSizeException, DaoException {
    Page<Computer> page = new Page<>(1, -10);
    computerDao.getPage(page);
  }
}
