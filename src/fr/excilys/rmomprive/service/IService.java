package fr.excilys.rmomprive.service;

import java.util.Collection;

public interface IService<T> {
	public T getById(int id);
	public Collection<T> getAll();
	public T add(T object);
	public Collection<T> addAll(Collection<T> objects);
	public T update(T object);
	public boolean delete(T object);
	public boolean deleteById(int id);
	public boolean checkExistenceById(int id);
}
