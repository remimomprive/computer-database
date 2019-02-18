package fr.excilys.rmomprive;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import fr.excilys.rmomprive.exception.ValidationException;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.persistence.CompanyDao;
import fr.excilys.rmomprive.persistence.ComputerDao;
import fr.excilys.rmomprive.service.ComputerService;

public class ComputerTest {
	@Test
	public void validCreation() throws SQLException {
		Computer computer = new Computer(1, "ABC", new Date(100), null, 1);
		Optional<Computer> expectedData = Optional.of(computer);
		
		ComputerDao computerDao = Mockito.mock(ComputerDao.class);
		Mockito.when(computerDao.add(computer)).thenReturn(expectedData);
		CompanyDao companyDao = Mockito.mock(CompanyDao.class);
		
		ComputerService computerService = Mockito.spy(ComputerService.class);
		Mockito.when(computerService.getComputerDao()).thenReturn(computerDao);
		Mockito.when(computerService.getCompanyDao()).thenReturn(companyDao);
		
		Optional<Computer> resultData = computerService.add(computer);
		
		assertEquals(resultData.get().getId(), expectedData.get().getId());
		assertEquals(resultData.get().getName(), expectedData.get().getName());
		assertEquals(resultData.get().getIntroduced(), expectedData.get().getIntroduced());
		assertEquals(resultData.get().getDiscontinued(), expectedData.get().getDiscontinued());
		assertEquals(resultData.get().getCompanyId(), expectedData.get().getCompanyId());
	}
	
	// When the computer name is empty
	@Test(expected = ValidationException.class)
	public void invalidComputerName() throws SQLException {
		Computer computer = new Computer(1, "", new Date(100), null, 1);
		Optional<Computer> expectedData = Optional.of(computer);
		
		ComputerDao computerDao = Mockito.mock(ComputerDao.class);
		Mockito.when(computerDao.add(computer)).thenReturn(expectedData);
		CompanyDao companyDao = Mockito.mock(CompanyDao.class);
		
		ComputerService computerService = Mockito.spy(ComputerService.class);
		Mockito.when(computerService.getComputerDao()).thenReturn(computerDao);
		Mockito.when(computerService.getCompanyDao()).thenReturn(companyDao);
		
		computerService.add(computer);
	}
	
	// When introduction date is after discontinution date
	@Test(expected = ValidationException.class)
	public void invalidDates() throws SQLException {
		Computer computer = new Computer(1, "ABC", new Date(100), new Date(50), 1);
		Optional<Computer> expectedData = Optional.of(computer);
		
		ComputerDao computerDao = Mockito.mock(ComputerDao.class);
		Mockito.when(computerDao.add(computer)).thenReturn(expectedData);
		CompanyDao companyDao = Mockito.mock(CompanyDao.class);
		
		ComputerService computerService = Mockito.spy(ComputerService.class);
		Mockito.when(computerService.getComputerDao()).thenReturn(computerDao);
		Mockito.when(computerService.getCompanyDao()).thenReturn(companyDao);
		
		computerService.add(computer);
	}
	
	// When company does not exist
	@Test
	public void invalidCompanyId() throws SQLException {
		Computer computer = new Computer(1, "ABC", new Date(50), new Date(500), 1);
		
		ComputerDao computerDao = Mockito.mock(ComputerDao.class);
		CompanyDao companyDao = Mockito.mock(CompanyDao.class);
		Mockito.doReturn(Optional.empty()).when(companyDao).getById(1);
		
		ComputerService computerService = Mockito.spy(ComputerService.class);
		Mockito.when(computerService.getComputerDao()).thenReturn(computerDao);
		Mockito.when(computerService.getCompanyDao()).thenReturn(companyDao);
		
		Optional<Computer> resultData = computerService.add(computer);
		
		assertEquals(resultData, Optional.empty());
	}
}
