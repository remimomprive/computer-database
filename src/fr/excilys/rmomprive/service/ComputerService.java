package fr.excilys.rmomprive.service;

import java.util.Collection;

import fr.excilys.rmomprive.exception.InvalidPageNumberException;
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
	public Computer getById(int id) {
		return this.computerDao.getById(id);
	}
	
	public ComputerDetails getDetailsByComputerId(int id) {
		Computer computer = this.computerDao.getById(id);
		Company company = this.companyDao.getById(computer.getCompanyId());
		return new ComputerDetails(computer, company);
	}

	@Override
	public Collection<Computer> getAll() {
		return this.computerDao.getAll();
	}

	@Override
	public Computer add(Computer object) {
		return this.computerDao.add(object);
	}

	@Override
	public Collection<Computer> addAll(Collection<Computer> objects) {
		return this.computerDao.addAll(objects);
	}

	@Override
	public Computer update(Computer object) {
		return this.computerDao.update(object);
	}

	@Override
	public boolean delete(Computer object) {
		return this.computerDao.delete(object);
	}
	
	@Override
	public boolean deleteById(int id) {
		return this.computerDao.deleteById(id);
	}
	
	@Override
	public boolean checkExistenceById(int id) {
		return this.computerDao.checkExistenceById(id);
	}
	
	public Page<Computer> getPage(int pageId, int pageSize) throws InvalidPageNumberException {
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
