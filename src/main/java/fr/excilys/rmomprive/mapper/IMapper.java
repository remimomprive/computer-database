package fr.excilys.rmomprive.mapper;

import fr.excilys.rmomprive.dto.IDto;

public interface IMapper<T> {
  /**
   * Create an entity from its dto.
   * @param dto The Dto object
   * @return The entity object
   */
  T mapFromDto(IDto<T> dto);

  /**
   * Create a dto from its entity.
   * @param entity The entity object
   * @return The dto object
   */
  IDto<T> mapFromEntity(T entity);
}
