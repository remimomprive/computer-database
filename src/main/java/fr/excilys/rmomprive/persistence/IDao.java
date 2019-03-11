package fr.excilys.rmomprive.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import fr.excilys.rmomprive.exception.DaoException;
import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.pagination.Page;

public interface IDao<T> {
  /**
   * @param id The entity id
   * @return The entity we asked for
   */
  Optional<T> getById(long id) throws DaoException;

  /**
   * @param name id The entity name.
   * @return The companies if some match the name, an empty list if not
   */
  public List<T> getByName(String name) throws DaoException;

  /**
   * @return A collection of all the entities in the table
   */
  Collection<T> getAll() throws DaoException;

  /**
   * @param object The entity we want to persist
   * @return The persisted entity if it was successfully added, no data if an error persisting the
   *         entity happened
   */
  Optional<T> add(T object) throws DaoException;

  /**
   * @param objects A Collection of entities to persist
   * @return The inserted entities
   */
  Collection<T> addAll(Collection<T> objects);

  /**
   * @param object The entity we want to update (thanks to its id and fields)
   * @return The updated entity
   */
  T update(T object) throws DaoException;

  /**
   * @param object The entity we want to remove
   * @return true if the entity was successfully deleted, false if not
   */
  boolean delete(T object) throws DaoException;

  /**
   * @param id The id of the entity we want to remove
   * @return true if the entity was successfully deleted, false if not
   */
  boolean deleteById(long id) throws DaoException;

  /**
   * @param ids The ids of the entities we want to remove
   * @return true if the entities were successfully deleted, false if not
   */
  boolean deleteByIds(List<Long> ids) throws DaoException;

  /**
   * @param id The entity id
   * @return true if the entity is present in the database, false if not
   */
  boolean checkExistenceById(long id) throws DaoException;

  /**
   * @return The number of entities in the database (for a specific type)
   */
  int getRowCount() throws DaoException;

  /**
   * @param pageSize The page size
   * @return The number of pages for a specific page size
   */
  int getPageCount(int pageSize) throws DaoException;

  /**
   * @param page The page containing the parameters (size and number)
   * @return The page we asked for (containing a list of entities)
   * @throws InvalidPageIdException   if the page id is not valid (<1 or too large)
   * @throws InvalidPageSizeException (if the page size is not valid (<1)
   */
  Page<T> getPage(Page page) throws InvalidPageIdException, InvalidPageSizeException, DaoException;
}
