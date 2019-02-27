package fr.excilys.rmomprive.persistence;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.pagination.Page;

public interface IDao<T> {
  /**
   * @param id The entity id
   * @return The entity we asked for
   * @throws SQLException if an error accessing the database happened
   */
  Optional<T> getById(long id) throws SQLException;

  /**
   * @return A collection of all the entities in the table
   * @throws SQLException if an error accessing the database happened
   */
  Collection<T> getAll() throws SQLException;

  /**
   * @param object The entity we want to persist
   * @return The persisted entity if it was successfully added,
   * no data if an error persisting the entity happened
   * @throws SQLException if an error accessing the database happened
   */
  Optional<T> add(T object) throws SQLException;

  /**
   * @param objects A Collection of entities to persist
   * @return The inserted entities
   */
  Collection<T> addAll(Collection<T> objects);

  /**
   * @param object The entity we want to update (thanks to its id and fields)
   * @return The updated entity
   * @throws SQLException if an error accessing the database happened
   */
  T update(T object) throws SQLException;

  /**
   * @param object The entity we want to remove
   * @return true if the entity was successfully deleted, false if not
   * @throws SQLException if an error accessing the database happened
   */
  boolean delete(T object) throws SQLException;

  /**
   * @param id The id of the entity we want to remove
   * @return true if the entity was successfully deleted, false if not
   * @throws SQLException if an error accessing the database happened
   */
  boolean deleteById(long id) throws SQLException;
  
  /**
   * @param ids The ids of the entities we want to remove
   * @return true if the entities were successfully deleted, false if not
   * @throws SQLException if an error accessing the database happened
   */
  boolean deleteByIds(List<Long> ids) throws SQLException;

  /**
   * @param id The entity id
   * @return true if the entity is present in the database, false if not
   * @throws SQLException if an error accessing the database happened
   */
  boolean checkExistenceById(long id) throws SQLException;

  /**
   * @return The number of entities in the database (for a specific type)
   * @throws SQLException if an error accessing the database happened
   */
  int getRowCount() throws SQLException;

  /**
   * @param pageSize The page size
   * @return The number of pages for a specific page size
   * @throws SQLException if an error accessing the database happened
   */
  int getPageCount(int pageSize) throws SQLException;

  /**
   * @param pageId The page id
   * @param pageSize The page size
   * @return The page we asked for (containing a list of entities)
   * @throws InvalidPageIdException if the page id is not valid (<1 or too large)
   * @throws InvalidPageSizeException (if the page size is not valid (<1)
   * @throws SQLException if an error accessing the database happened
   */
  Page<T> getPage(int pageId, int pageSize)
      throws InvalidPageIdException, InvalidPageSizeException, SQLException;
}
