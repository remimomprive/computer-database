package com.excilys.rmomprive.computerdatabase.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageIdException;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageSizeException;
import com.excilys.rmomprive.computerdatabase.persistence.Page;

public interface IService<T> {
  /**
   * @param id The entity id
   * @return The entity we asked for
   */
  Optional<T> getById(long id);

  /**
   * @return A collection of all the entities in the table
   */
  List<T> getAll();

  /**
   * @param object The entity we want to persist
   * @return The persisted entity if it was successfully added, no data if an error persisting the
   *         entity happened
   */
  Optional<T> add(T object);

  /**
   * @param object The entity we want to update (thanks to its id and fields)
   * @return The updated entity
   */
  T update(T object);

  /**
   * @param object The entity we want to remove
   * @return true if the entity was successfully deleted, false if not
   */
  boolean delete(T object);

  /**
   * @param id The id of the entity we want to remove
   * @return true if the entity was successfully deleted, false if not
   */
  boolean deleteById(long id);

  /**
   * @param ids The ids of the entities we want to remove
   * @return true if the entities were successfully deleted, false if not
   */
  boolean deleteByIds(List<Long> ids);

  /**
   * @param id The entity id
   * @return true if the entity is present in the database, false if not
   */
  boolean checkExistenceById(long id);

  /**
   * @return The number of entities in the database (for a specific type)
   */
  int getRowCount();

  /**
   * @param pageSize The page size
   * @return The number of pages for a specific page size
   */
  int getPageCount(int pageSize);

  /**
   * @param pageId   The page id
   * @param pageSize The page size
   * @return The page we asked for (containing a list of entities)
   * @throws InvalidPageIdException   if the page id is not valid (<1 or too large)
   * @throws InvalidPageSizeException (if the page size is not valid (<1)
   */
  Page<T> getPage(int pageId, int pageSize)
      throws InvalidPageIdException, InvalidPageSizeException;
}
