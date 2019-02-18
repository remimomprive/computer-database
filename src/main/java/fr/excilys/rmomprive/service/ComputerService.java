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
import fr.excilys.rmomprive.persistence.CompanyDao;
import fr.excilys.rmomprive.persistence.ComputerDao;

public class ComputerService implements IService<Computer> {
	private static ComputerService instance;
	private ComputerDao computerDao;
	private CompanyDao companyDao;
	
	public ComputerService() {
		this.setComputerDao(new ComputerDao());
		this.setCompanyDao(new CompanyDao());
	}
	
	@Override
	public Optional<Computer> getById(int id) throws SQLException {
		return this.computerDao.getById(id);
	}
	
	public Optional<ComputerDetails> getDetailsByComputerId(int id) throws SQLException {
		Optional<Computer> computer = this.computerDao.getById(id);
		
		if (!computer.isPresent()) {
			return Optional.empty();
		}
		else {
			Optional<Company> company = this.companyDao.getById(computer.get().getCompanyId());
			
			if (company.isPresent()) {
				return Optional.of(new ComputerDetails(computer.get(), company.get()));
			}
			else {
				return Optional.empty();
			}				
		}
	}

	@Override
	public Collection<Computer> getAll() throws SQLException {
		return this.computerDao.getAll();
	}

	@Override
	public Optional<Computer> add(Computer object) throws SQLException {
		return this.computerDao.add(object);
	}

	@Override
	public Collection<Computer> addAll(Collection<Computer> objects) {
		return this.computerDao.addAll(objects);
	}

	@Override
	public Computer update(Computer object) throws SQLException {
		return this.computerDao.update(object);
	}

	@Override
	public boolean delete(Computer object) throws SQLException {
		return this.computerDao.delete(object);
	}
	
	@Override
	public boolean deleteById(int id) throws SQLException {
		return this.computerDao.deleteById(id);
	}
	
	@Override
	public boolean checkExistenceById(int id) throws SQLException {
		return this.computerDao.checkExistenceById(id);
	}

	@Override
	public int getRowCount() throws SQLException {
		return this.computerDao.getRowCount();
	}

	@Override
	public int getPageCount(int pageSize) throws SQLException {
		return this.computerDao.getPageCount(pageSize);
	}
	
	@Override
	public Page<Computer> getPage(int pageId, int pageSize) throws InvalidPageIdException, InvalidPageSizeException, SQLException {
		return this.computerDao.getPage(pageId, pageSize);
	}
	
	public static ComputerService getInstance() {
		if (instance == null)
			instance = new ComputerService();
		
		return instance;
	}

	public ComputerDao getComputerDao() {
		return computerDao;
	}

	public void setComputerDao(ComputerDao computerDao) {
		this.computerDao = computerDao;
	}

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
}
