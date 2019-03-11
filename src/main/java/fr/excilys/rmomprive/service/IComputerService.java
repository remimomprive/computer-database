package fr.excilys.rmomprive.service;

import java.util.Optional;

import fr.excilys.rmomprive.exception.DaoException;
import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.model.ComputerDetails;
import fr.excilys.rmomprive.pagination.Page;

public interface IComputerService extends IService<Computer> {
  /**
   * @param id The computer id
   * @return The computer details of a specific computer
   * @throws DaoException If an error accessing the database happened
   */
  Optional<ComputerDetails> getDetailsByComputerId(final int id) throws DaoException;

  /**
   * Get the number of rows for the specificied search query
   * @param search The search query
   * @return The number of rows
   * @throws DaoException If an error accessing the database happened
   */
  int getRowCount(String search) throws DaoException;
  
  /**
   * Get the number of pages for the specified search query and page size
   * @param pageSize The page size
   * @param search The search query
   * @return The page count
   * @throws DaoException If an error accessing the database happened
   */
  int getPageCount(int pageSize, String search) throws DaoException;
  
  /**
   * Query a specific page with its parameters 
   * @param pageId The page id
   * @param pageSize The page size
   * @param name The search query
   * @param orderBy The column to order by
   * @param orderDirection The order direction
   * @return The page content
   * @throws DaoException If an error accessing the database happened
   * @throws InvalidPageSizeException The the page size is not valid
   * @throws InvalidPageIdException If the page id is not valid
   */
  Page<Computer> getByNameOrCompanyName(int pageId, int pageSize, String name, String orderBy,
      String orderDirection) throws DaoException, InvalidPageSizeException, InvalidPageIdException;
}
