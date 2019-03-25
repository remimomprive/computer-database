package com.excilys.rmomprive.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.rmomprive.computerdatabase.core.ComputerDetails;
import com.excilys.rmomprive.computerdatabase.core.Computer;
import com.excilys.rmomprive.computerdatabase.binding.ComputerDto;
import com.excilys.rmomprive.computerdatabase.binding.ComputerMapper;
import com.excilys.rmomprive.computerdatabase.core.Company;
import com.excilys.rmomprive.computerdatabase.persistence.Page;
import com.excilys.rmomprive.computerdatabase.persistence.ICompanyDao;
import com.excilys.rmomprive.computerdatabase.persistence.IComputerDao;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageIdException;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageSizeException;

import com.excilys.rmomprive.computerdatabase.validation.ComputerValidator;

@Service
public class ComputerService implements IComputerService {

  private IComputerDao computerDao;
  private ComputerMapper computerMapper;

  public ComputerService(IComputerDao computerDao, ComputerMapper computerMapper) {
    this.computerDao = computerDao;
    this.computerMapper = computerMapper;
  }

  @Override
  public Optional<ComputerDto> getById(long id) {
    Optional<Computer> computer = computerDao.getById(id);
    if (computer.isPresent()) {
      return Optional.of(computerMapper.mapFromEntity(computer.get()));
    } else {
      return Optional.empty();
    }
  }

  @Override
  public List<ComputerDto> getAll() {
    List<ComputerDto> output = new ArrayList<>();
    computerDao.getAll().forEach(computer -> output.add(computerMapper.mapFromEntity(computer)));
    return output;
  }

  @Override
  public Optional<ComputerDto> add(ComputerDto computerDto) {
    Computer computer = computerMapper.mapFromDto(computerDto);
    ComputerValidator.validate(computer);
    Optional<Computer> addedComputer = computerDao.add(computer);
    
    if (addedComputer.isPresent()) {
      return Optional.of(computerMapper.mapFromEntity(addedComputer.get()));
    } else {
      return Optional.empty();
    }
  }

  @Override
  public ComputerDto update(ComputerDto computerDto) {
    Computer computer = computerMapper.mapFromDto(computerDto);
    ComputerValidator.validate(computer);
    
    return computerMapper.mapFromEntity(computerDao.update(computer));
  }

  @Override
  public boolean delete(ComputerDto computerDto) {
    Computer computer = computerMapper.mapFromDto(computerDto);
    ComputerValidator.validate(computer);
    
    return computerDao.delete(computer);
  }

  @Override
  public boolean deleteById(long id) {
    return computerDao.deleteById(id);
  }

  @Override
  public boolean deleteByIds(List<Long> ids) {
    return computerDao.deleteByIds(ids);
  }

  @Override
  public boolean checkExistenceById(long id) {
    return computerDao.checkExistenceById(id);
  }

  @Override
  public int getRowCount() {
    return computerDao.getRowCount("");
  }

  @Override
  public int getRowCount(String search) {
    return computerDao.getRowCount(search);
  }

  @Override
  public int getPageCount(int pageSize) {
    return computerDao.getPageCount(pageSize, "");
  }

  @Override
  public int getPageCount(int pageSize, String search) {
    return computerDao.getPageCount(pageSize, search);
  }

  @Override
  public Page<ComputerDto> getPage(int pageId, int pageSize) throws InvalidPageIdException, InvalidPageSizeException {
    return computerMapper.createDtoPage(computerDao.getPage(new Page<Computer>(pageId, pageSize)));
  }

  @Override
  public Page<ComputerDto> getByNameOrCompanyName(int pageId, int pageSize, String name, String orderBy, String orderDirection)
      throws InvalidPageSizeException, InvalidPageIdException {
    return computerMapper.createDtoPage(computerDao.getByNameOrCompanyName(new Page<Computer>(pageId, pageSize), name, orderBy, orderDirection));
  }
}
