package fr.excilys.rmomprive.controller;

import java.util.Collection;

import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.persistence.ComputerDao;

public class ComputerController implements IController<Computer> {

	private static ComputerController instance;
	private ComputerDao computerDao;
	
	public ComputerController() {
		this.setComputerDao(new ComputerDao());
	}
	
	@Override
	public Computer getById(int id) {
		return this.computerDao.getById(id);
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
	
	public static ComputerController getInstance() {
		if (instance == null)
			instance = new ComputerController();
		
		return instance;
	}

	public ComputerDao getComputerDao() {
		return computerDao;
	}

	public void setComputerDao(ComputerDao computerDao) {
		this.computerDao = computerDao;
	}
}
