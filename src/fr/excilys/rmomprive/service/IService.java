package fr.excilys.rmomprive.service;

import java.util.Collection;

import fr.excilys.rmomprive.exception.InvalidPageNumberException;

public interface IService<T> {
	public T getById(int id);
	public Collection<T> getAll();
	public T add(T object);
	public Collection<T> addAll(Collection<T> objects);
	public T update(T object);
	public boolean delete(T object);
	public boolean deleteById(int id);
	public boolean checkExistenceById(int id);
	public int getRowCount();
	public int getPageCount(int pageSize);
	public Page getPage(int pageId, int pageSize) throws InvalidPageNumberException;
}
