package com.excilys.rmomprive.computerdatabase.persistence;

import java.util.List;
import java.util.Optional;

import com.excilys.rmomprive.computerdatabase.core.Computer;

public interface IComputerDao {
  /**
   * @param id The entity id
   * @return The entity we asked for
   */
  Optional<Computer> getById(long id);

  /**
   * @param page           The page parameters (size, id)
   * @param name           The name used for the search (computer name and company name)
   * @param orderBy        The column defining who to order by
   * @param orderDirection The order direction for the result
   * @return A page containing the computers if some match the name, an empty page if not
   * @throws InvalidPageIdException   if the page id is not valid (<1 or too large)
   * @throws InvalidPageSizeException (if the page size is not valid (<1)
   */
  Page<Computer> getByNameOrCompanyName(Page<Computer> page, String name, String orderBy, String orderDirection) throws InvalidPageIdException, InvalidPageSizeException;

  /**
   * @return A collection of all the entities in the table
   */
  List<Computer> getAll();

  /**
   * @param computer The entity we want to persist
   * @return The persisted entity if it was successfully added, no data if an error persisting the
   *         entity happened
   */
  Optional<Computer> add(Computer computer);

  /**
   * @param computer The entity we want to update (thanks to its id and fields)
   * @return The updated entity
   */
  Computer update(Computer computer);

  /**
   * @param computer The entity we want to remove
   * @return true if the entity was successfully deleted, false if not
   */
  boolean delete(Computer computer);

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
   * @param search The search query
   * @return The number of entities in the database (for a specific type)
   */
  int getRowCount(String search);

  /**
   * @param pageSize The page size
   * @param search   The search query
   * @return The number of pages for a specific page size
   */
  int getPageCount(int pageSize, String search);

  /**
   * @param page The page containing the parameters (size and number)
   * @return The page we asked for (containing a list of entities)
   * @throws InvalidPageIdException   if the page id is not valid (<1 or too large)
   * @throws InvalidPageSizeException (if the page size is not valid (<1)
   */
  Page<Computer> getPage(Page<Computer> page) throws InvalidPageIdException, InvalidPageSizeException;
}
