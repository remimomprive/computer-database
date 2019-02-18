package fr.excilys.rmomprive.persistence;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.pagination.Page;

public interface IDao<T> {
	public Optional<T> getById(long id) throws SQLException;
	public Collection<T> getAll() throws SQLException;
	public Optional<T> add(T object) throws SQLException;
	public Collection<T> addAll(Collection<T> objects);
	public T update(T object) throws SQLException;
	public boolean delete(T object) throws SQLException;
	public boolean deleteById(long id) throws SQLException;
	public boolean checkExistenceById(long id) throws SQLException;
	public int getRowCount() throws SQLException;
	public int getPageCount(int pageSize) throws SQLException;
	public Page<T> getPage(int pageId, int pageSize) throws InvalidPageIdException, InvalidPageSizeException, SQLException;
}
