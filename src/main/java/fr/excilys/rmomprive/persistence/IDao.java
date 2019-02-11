package fr.excilys.rmomprive.persistence;

import java.util.Collection;

public interface IDao<T> {
	public T getById(int id);
	public Collection<T> getAll();
}
