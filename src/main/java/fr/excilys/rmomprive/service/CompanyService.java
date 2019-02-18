package fr.excilys.rmomprive.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.pagination.Page;
import fr.excilys.rmomprive.persistence.CompanyDao;

public class CompanyService implements IService<Company> {

	private static CompanyService instance;
	
	@Override
	public Optional<Company> getById(long id) throws SQLException {
		return this.getCompanyDao().getById(id);
	}

	@Override
	public Collection<Company> getAll() throws SQLException {
		return this.getCompanyDao().getAll();
	}

	@Override
	public Optional<Company> add(Company object) {
		return this.getCompanyDao().add(object);
	}

	@Override
	public Collection<Company> addAll(Collection<Company> objects) {
		return this.getCompanyDao().addAll(objects);
	}

	@Override
	public Company update(Company object) {
		return this.getCompanyDao().update(object);
	}

	@Override
	public boolean delete(Company object) {
		return this.getCompanyDao().delete(object);
	}
	
	@Override
	public boolean deleteById(long id) throws SQLException {
		return this.getCompanyDao().deleteById(id);
	}
	
	@Override
	public boolean checkExistenceById(long id) {
		return this.getCompanyDao().checkExistenceById(id);
	}

	@Override
	public int getRowCount() {
		return this.getCompanyDao().getRowCount();
	}

	@Override
	public int getPageCount(int pageSize) {
		return this.getCompanyDao().getPageCount(pageSize);
	}

	@Override
	public Page<Company> getPage(int pageId, int pageSize) throws InvalidPageIdException {
		return this.getCompanyDao().getPage(pageId, pageSize);
	}
	
	public static CompanyService getInstance() {
		if (instance == null)
			instance = new CompanyService();
		
		return instance;
	}

	public CompanyDao getCompanyDao() {
		return CompanyDao.getInstance();
	}
}
