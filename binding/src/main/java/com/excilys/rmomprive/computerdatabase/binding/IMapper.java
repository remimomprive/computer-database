package com.excilys.rmomprive.computerdatabase.binding;

public interface IMapper<T> {
  /**
   * Create a dto from its entity.
   * @param entity The entity object
   * @return The dto object
   */
  IDto<T> mapFromEntity(T entity);
}
