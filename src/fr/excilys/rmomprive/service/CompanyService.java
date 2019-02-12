package fr.excilys.rmomprive.service;

import java.util.Collection;

import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.persistence.CompanyDao;

public class CompanyService implements IService<Company> {

	private static CompanyService instance;
	private CompanyDao companyDao;
	
	public CompanyService() {
		this.setCompanyDao(new CompanyDao());
	}
	
	@Override
	public Company getById(int id) {
		return this.companyDao.getById(id);
	}

	@Override
	public Collection<Company> getAll() {
		return this.companyDao.getAll();
	}

	@Override
	public Company add(Company object) {
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