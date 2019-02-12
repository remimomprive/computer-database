package fr.excilys.rmomprive.persistence;

import java.util.Collection;

public interface IDao<T> {
	public T getById(int id);
	public Collection<T> getAll();
	public T add(T object);
	public Collection<T> addAll(Collection<T> objects);
	public T update(T object);
	public boolean delete(T object);
	public boolean deleteById(int id);
}
