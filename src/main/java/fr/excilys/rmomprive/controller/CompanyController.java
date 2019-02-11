package fr.excilys.rmomprive.controller;

import java.util.Collection;

import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.persistence.CompanyDao;

public class CompanyController implements IController<Company> {

	private static CompanyController instance;
	private CompanyDao companyDao;
	
	public CompanyController() {
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
	
	public static CompanyController getInstance() {
		if (instance == null)
			instance = new CompanyController();
		
		return instance;
	}

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

}
