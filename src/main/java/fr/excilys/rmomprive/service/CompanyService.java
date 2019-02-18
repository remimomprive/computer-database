package fr.excilys.rmomprive.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.persistence.CompanyDao;

public class CompanyService implements IService<Company> {

	private static CompanyService instance;
	private CompanyDao companyDao;
	
	public CompanyService() {
		this.setCompanyDao(new CompanyDao());
	}
	
	@Override
	public Optional<Company> getById(int id) throws SQLException {
		return this.companyDao.getById(id);
	}

	@Override
	public Collection<Company> getAll() throws SQLException {
		return this.companyDao.getAll();
	}

	@Override
	public Optional<Company> add(Company object) {
		return this.companyDao.add(object);
	}

	@Override
	public Collection<Company> addAll(Collection<Company> objects) {
		return this.companyDao.addAll(objects);
	}

	@Override
	public Company update(Company object) {
		return this.companyDao.update(object);
	}

	@Override
	public boolean delete(Company object) {
		return this.companyDao.delete(object);
	}
	
	@Override
	public boolean deleteById(int id) {
		return this.companyDao.deleteById(id);
	}
	
	@Override
	public boolean checkExistenceById(int id) {
		return this.companyDao.checkExistenceById(id);
	}

	@Override
	public int getRowCount() {
		return this.companyDao.getRowCount();
	}

	@Override
	public int getPageCount(int pageSize) {
		return this.companyDao.getPageCount(pageSize);
	}

	@Override
	public Page<Company> getPage(int pageId, int pageSize) throws InvalidPageIdException {
		return this.companyDao.getPage(pageId, pageSize);
	}
	
	public static CompanyService getInstance() {
		if (instance == null)
			instance = new CompanyService();
		
		return instance;
	}

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
}
