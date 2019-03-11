package fr.excilys.rmomprive.test.unit;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import fr.excilys.rmomprive.exception.DaoException;
import fr.excilys.rmomprive.exception.ValidationException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.persistence.CompanyDao;
import fr.excilys.rmomprive.persistence.ComputerDao;
import fr.excilys.rmomprive.service.ComputerService;

public class ComputerServiceTest {
  /**
   * Get a mocked ComputerService instance, using reflection to set computerDao and companyDao fields
   * @param computerDao The value of computerDao to set
   * @param companyDao The value of companyDao to set
   * @return The mocked ComputerService
   */
  private ComputerService getService(ComputerDao computerDao, CompanyDao companyDao)
      throws NoSuchFieldException, SecurityException, IllegalArgumentException,
      IllegalAccessException {
    ComputerService computerService = Mockito.spy(ComputerService.class);

    // Try changing some fields with reflection api
    Field computerDaoField, companyDaoField;
    
    // Change the computerDao field of computerService
    computerDaoField = ComputerService.class.getDeclaredField("computerDao");
    computerDaoField.setAccessible(true);
    computerDaoField.set(computerService, computerDao);
    
    // Change the companyDao field of computerService
    companyDaoField = ComputerService.class.getDeclaredField("companyDao");
    companyDaoField.setAccessible(true);
    companyDaoField.set(computerService, companyDao);

    return computerService;
  }

  /**
   * Checks if we can create a valid computer
   */
  @Test
  public void shouldSuccessWhenAddingComputerWithValidData() throws DaoException, NoSuchFieldException, SecurityException,
      IllegalArgumentException, IllegalAccessException {
    // Create the expected data
    Company company = new Company(1, "company");
    Computer computer = new Computer(1, "ABC", LocalDate.now(), null, company);
    Optional<Computer> expectedData = Optional.of(computer);

    // Create the dao mocks
    ComputerDao computerDao = Mockito.mock(ComputerDao.class);
    Mockito.when(computerDao.add(computer)).thenReturn(expectedData);
    CompanyDao companyDao = Mockito.mock(CompanyDao.class);

    // Get the mocked service
    ComputerService computerService = getService(computerDao, companyDao);

    // Apply the method we want to test
    Optional<Computer> resultData = computerService.add(computer);

    // Check if we got what we expected
    assertEquals(resultData.get().getId(), expectedData.get().getId());
    assertEquals(resultData.get().getName(), expectedData.get().getName());
    assertEquals(resultData.get().getIntroduced(), expectedData.get().getIntroduced());
    assertEquals(resultData.get().getDiscontinued(), expectedData.get().getDiscontinued());
    assertEquals(resultData.get().getCompany().getId(), expectedData.get().getCompany().getId());
  }

  /**
   * Checks if ValidationException happens when computer.name is empty
   */
  @Test(expected = ValidationException.class)
  public void shouldThrowExceptionWhenAddingComputerWithoutName() throws DaoException, NoSuchFieldException, SecurityException,
      IllegalArgumentException, IllegalAccessException {
    // Create the expected data
    Company company = new Company(1, "company");
    Computer computer = new Computer(1, "", LocalDate.now(), null, company);
    Optional<Computer> expectedData = Optional.of(computer);

    // Create the dao mocks
    ComputerDao computerDao = Mockito.mock(ComputerDao.class);
    Mockito.when(computerDao.add(computer)).thenReturn(expectedData);
    CompanyDao companyDao = Mockito.mock(CompanyDao.class);

    // Get the mocked service
    ComputerService computerService = getService(computerDao, companyDao);

    // Apply the method we want to to test
    computerService.add(computer);
  }

  /**
   * Checks if ValidationException happens when introduction date is after discontinution date
   */
  @Test(expected = ValidationException.class)
  public void shouldThrowExceptionWhenAddingComputerWithInvalidDatesPrecedence() throws DaoException, NoSuchFieldException, SecurityException,
      IllegalArgumentException, IllegalAccessException {
    // Create the expected data
    Company company = new Company(1, "company");
    Computer computer = new Computer(1, "ABC", LocalDate.of(2010, 1, 10), LocalDate.of(2002, 1, 10),
        company);
    Optional<Computer> expectedData = Optional.of(computer);

    // Create the dao mocks
    ComputerDao computerDao = Mockito.mock(ComputerDao.class);
    Mockito.when(computerDao.add(computer)).thenReturn(expectedData);
    CompanyDao companyDao = Mockito.mock(CompanyDao.class);

    // Get the mocked service
    ComputerService computerService = getService(computerDao, companyDao);

    // Apply the method we want to to test
    computerService.add(computer);
  }

  /**
   * Check if an empty result is given then we try to add a computer with an invalid company
   */
  @Test
  public void shouldReturnEmptyComputerWhenAddingComputerWithInvalidCompanyId() throws DaoException, NoSuchFieldException, SecurityException,
      IllegalArgumentException, IllegalAccessException {
    // Create the expected data
    Company company = new Company(1, "company");
    Computer computer = new Computer(1, "ABC", LocalDate.of(2010, 1, 10), LocalDate.of(2012, 1, 10),
        company);

    // Create the dao mocks
    ComputerDao computerDao = Mockito.mock(ComputerDao.class);
    CompanyDao companyDao = Mockito.mock(CompanyDao.class);
    Mockito.doReturn(Optional.empty()).when(companyDao).getById(1);

    // Get the mocked service
    ComputerService computerService = getService(computerDao, companyDao);

    // Apply the method we want to to test
    Optional<Computer> resultData = computerService.add(computer);
    
    // Check if we got what we expected
    assertEquals(resultData, Optional.empty());
  }
}
