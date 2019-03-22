package com.excilys.rmomprive.computerdatabase.service;

import java.util.Optional;

import com.excilys.rmomprive.computerdatabase.core.Computer;
import com.excilys.rmomprive.computerdatabase.core.ComputerDetails;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageIdException;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageSizeException;
import com.excilys.rmomprive.computerdatabase.persistence.Page;

public interface IComputerService extends IService<Computer> {
  /**
   * Get the details for the specified computer.
   *
   * @param id The computer id
   * @return The computer details of a specific computer
   */
  Optional<ComputerDetails> getDetailsByComputerId(final int id);

  /**
   * Get the number of rows for the specificied search query.
   *
   * @param search The search query
   * @return The number of rows
   */
  int getRowCount(String search);

  /**
   * Get the number of pages for the specified search query and page size.
   *
   * @param pageSize The page size
   * @param search   The search query
   * @return The page count
   */
  int getPageCount(int pageSize, String search);

  /**
   * Query a specific page with its parameters.
   *
   * @param pageId         The page id
   * @param pageSize       The page size
   * @param name           The search query
   * @param orderBy        The column to order by
   * @param orderDirection The order direction
   * @return The page content
   * @throws InvalidPageSizeException The the page size is not valid
   * @throws InvalidPageIdException   If the page id is not valid
   */
  Page<Computer> getByNameOrCompanyName(int pageId, int pageSize, String name, String orderBy,
      String orderDirection) throws InvalidPageSizeException, InvalidPageIdException;
}
