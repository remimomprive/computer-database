package fr.excilys.rmomprive.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;

import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.pagination.Page;

public interface IService<T> {
  /**
   * @param id The entity id
   * @return The entity we asked for
   * @throws DataAccessException if an error accessing the database happened
   */
  Optional<T> getById(long id) throws DataAccessException;

  /**
   * @param name id The entity name.
   * @return The companies if some match the name, an empty list if not
   * @throws DataAccessException if an error accessing the database happened
   */
  List<T> getByName(String name) throws DataAccessException;

  /**
   * @return A collection of all the entities in the table
   * @throws DataAccessException if an error accessing the database happened
   */
  Collection<T> getAll() throws DataAccessException;

  /**
   * @param object The entity we want to persist
   * @return The persisted entity if it was successfully added, no data if an error persisting the
   *         entity happened
   * @throws DataAccessException if an error accessing the database happened
   */
  Optional<T> add(T object) throws DataAccessException;

  /**
   * @param objects A Collection of entities to persist
   * @return The inserted entities
   */
  Collection<T> addAll(Collection<T> objects);

  /**
   * @param object The entity we want to update (thanks to its id and fields)
   * @return The updated entity
   * @throws DataAccessException if an error accessing the database happened
   */
  T update(T object) throws DataAccessException;

  /**
   * @param object The entity we want to remove
   * @return true if the entity was successfully deleted, false if not
   * @throws DataAccessException if an error accessing the database happened
   */
  boolean delete(T object) throws DataAccessException;

  /**
   * @param id The id of the entity we want to remove
   * @return true if the entity was successfully deleted, false if not
   * @throws DataAccessException if an error accessing the database happened
   */
  boolean deleteById(long id) throws DataAccessException;

  /**
   * @param ids The ids of the entities we want to remove
   * @return true if the entities were successfully deleted, false if not
   * @throws DataAccessException if an error accessing the database happened
   */
  boolean deleteByIds(List<Long> ids) throws DataAccessException;

  /**
   * @param id The entity id
   * @return true if the entity is present in the database, false if not
   * @throws DataAccessException if an error accessing the database happened
   */
  boolean checkExistenceById(long id) throws DataAccessException;

  /**
   * @return The number of entities in the database (for a specific type)
   * @throws DataAccessException if an error accessing the database happened
   */
  int getRowCount() throws DataAccessException;

  /**
   * @param pageSize The page size
   * @return The number of pages for a specific page size
   * @throws DataAccessException if an error accessing the database happened
   */
  int getPageCount(int pageSize) throws DataAccessException;

  /**
   * @param pageId   The page id
   * @param pageSize The page size
   * @return The page we asked for (containing a list of entities)
   * @throws InvalidPageIdException   if the page id is not valid (<1 or too large)
   * @throws InvalidPageSizeException (if the page size is not valid (<1)
   * @throws DataAccessException             if an error accessing the database happened
   */
  Page<T> getPage(int pageId, int pageSize)
      throws InvalidPageIdException, InvalidPageSizeException, DataAccessException;
}
