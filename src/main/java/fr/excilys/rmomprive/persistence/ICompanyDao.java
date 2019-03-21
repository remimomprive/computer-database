package fr.excilys.rmomprive.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;

import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.pagination.Page;

public interface ICompanyDao {
  /**
   * @param id The entity id
   * @return The entity we asked for
   */
  Optional<Company> getById(long id) throws DataAccessException;

  /**
   * @param name id The entity name.
   * @return The companies if some match the name, an empty list if not
   */
  public List<Company> getByName(String name) throws DataAccessException;

  /**
   * @return A collection of all the entities in the table
   */
  List<Company> getAll() throws DataAccessException;

  /**
   * @param company The entity we want to persist
   * @return The persisted entity if it was successfully added, no data if an error persisting the
   *         entity happened
   */
  Optional<Company> add(Company company) throws DataAccessException;

  /**
   * @param companies A Collection of entities to persist
   * @return The inserted entities
   */
  List<Company> addAll(List<Company> companies);

  /**
   * @param company The entity we want to update (thanks to its id and fields)
   * @return The updated entity
   */
  Company update(Company company) throws DataAccessException;

  /**
   * @param company The entity we want to remove
   * @return true if the entity was successfully deleted, false if not
   */
  boolean delete(Company company) throws DataAccessException;

  /**
   * @param id The id of the entity we want to remove
   * @return true if the entity was successfully deleted, false if not
   */
  boolean deleteById(long id) throws DataAccessException;

  /**
   * @param ids The ids of the entities we want to remove
   * @return true if the entities were successfully deleted, false if not
   */
  boolean deleteByIds(List<Long> ids) throws DataAccessException;

  /**
   * @param id The entity id
   * @return true if the entity is present in the database, false if not
   */
  boolean checkExistenceById(long id) throws DataAccessException;

  /**
   * @return The number of entities in the database (for a specific type)
   */
  int getRowCount() throws DataAccessException;

  /**
   * @param pageSize The page size
   * @return The number of pages for a specific page size
   */
  int getPageCount(int pageSize) throws DataAccessException;

  /**
   * @param page The page containing the parameters (size and number)
   * @return The page we asked for (containing a list of entities)
   * @throws InvalidPageIdException   if the page id is not valid (<1 or too large)
   * @throws InvalidPageSizeException (if the page size is not valid (<1)
   */
  Page<Company> getPage(Page<Company> page) throws InvalidPageIdException, InvalidPageSizeException, DataAccessException;
}
