package com.excilys.rmomprive.computerdatabase.service;

import java.util.List;
import com.excilys.rmomprive.computerdatabase.core.Company;

public interface ICompanyService extends IService<Company> {
  /**
   * @param name id The entity name.
   * @return The companies if some match the name, an empty list if not
   */
  List<Company> getByName(String name);
}
