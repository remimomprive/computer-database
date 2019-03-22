package com.excilys.rmomprive.computerdatabase.persistence;

import java.util.List;
import java.util.Optional;

import com.excilys.rmomprive.computerdatabase.core.Company;

public interface ICompanyDao {
  /**
   * @param id The entity id
   * @return The entity we asked for
   */
  Optional<Company> getById(long id);

  /**
   * @param name id The entity name.
   * @return The companies if some match the name, an empty list if not
   */
  public List<Company> getByName(String name);

  /**
   * @return A collection of all the entities in the table
   */
  List<Company> getAll();

  /**
   * @param company The entity we want to persist
   * @return The persisted entity if it was successfully added, no data if an error persisting the
   *         entity happened
   */
  Optional<Company> add(Company company);

  /**
   * @param company The entity we want to update (thanks to its id and fields)
   * @return The updated entity
   */
  Company update(Company company);

  /**
   * @param company The entity we want to remove
   * @return true if the entity was successfully deleted, false if not
   */
  boolean delete(Company company);

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
   * @param page The page containing the parameters (size and number)
   * @return The page we asked for (containing a list of entities)
   * @throws InvalidPageIdException   if the page id is not valid (<1 or too large)
   * @throws InvalidPageSizeException (if the page size is not valid (<1)
   */
  Page<Company> getPage(Page<Company> page) throws InvalidPageIdException, InvalidPageSizeException;
}
