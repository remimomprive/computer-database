package fr.excilys.rmomprive.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

import fr.excilys.rmomprive.exception.EntityNotFoundException;
import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.model.ComputerDetails;
import fr.excilys.rmomprive.pagination.Page;
import fr.excilys.rmomprive.persistence.CompanyDao;
import fr.excilys.rmomprive.persistence.ComputerDao;
import fr.excilys.rmomprive.validation.ComputerValidator;

public class ComputerService implements IService<Computer> {
	private static ComputerService instance;
	
	@Override
	public Optional<Computer> getById(long id) throws SQLException {
		return this.getComputerDao().getById(id);
	}
	
	public Optional<ComputerDetails> getDetailsByComputerId(int id) throws SQLException {
		Optional<Computer> computer = this.getComputerDao().getById(id);
		
		if (!computer.isPresent()) {
			return Optional.empty();
		}
		else {
			Optional<Company> company = this.getCompanyDao().getById(computer.get().getCompany().getId());
			
			if (company.isPresent()) {
				return Optional.of(new ComputerDetails(computer.get(), company.get()));
			}
			else {
				return Optional.of(new ComputerDetails(computer.get(), null));
			}				
		}
	}

	@Override
	public Collection<Computer> getAll() throws SQLException {
		return this.getComputerDao().getAll();
	}

	@Override
	public Optional<Computer> add(Computer object) throws SQLException {
		ComputerValidator.validate(object);
		return this.getComputerDao().add(object);
	}

	@Override
	public Collection<Computer> addAll(Collection<Computer> objects) {
		return this.getComputerDao().addAll(objects);
	}

	@Override
	public Computer update(Computer object) throws SQLException {
		return this.getComputerDao().update(object);
	}

	@Override
	public boolean delete(Computer object) throws SQLException {
		return this.getComputerDao().delete(object);
	}
	
	@Override
	public boolean deleteById(long id) throws SQLException {
		return this.getComputerDao().deleteById(id);
	}
	
	@Override
	public boolean checkExistenceById(long id) throws SQLException {
		return this.getComputerDao().checkExistenceById(id);
	}

	@Override
	public int getRowCount() throws SQLException {
		return this.getComputerDao().getRowCount();
	}

	@Override
	public int getPageCount(int pageSize) throws SQLException {
		return this.getComputerDao().getPageCount(pageSize);
	}
	
	@Override
	public Page<Computer> getPage(int pageId, int pageSize) throws InvalidPageIdException, InvalidPageSizeException, SQLException {
		return this.getComputerDao().getPage(pageId, pageSize);
	}
	
	public static ComputerService getInstance() {
		if (instance == null)
			instance = new ComputerService();
		
		return instance;
	}

	public CompanyDao getCompanyDao() {
		return CompanyDao.getInstance();
	}

	public ComputerDao getComputerDao() {
		return ComputerDao.getInstance();
	}
}
