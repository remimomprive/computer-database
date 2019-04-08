package com.excilys.rmomprive.computerdatabase.webapp.controller.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.rmomprive.computerdatabase.binding.ComputerDto;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageIdException;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageSizeException;
import com.excilys.rmomprive.computerdatabase.service.ComputerService;

@RestController
@RequestMapping(path = "/api/computers", produces = "application/json")
public class ComputerController {
  private ComputerService computerService;

  public ComputerController(ComputerService computerService) {
    this.computerService = computerService;
  }

  @GetMapping
  public ResponseEntity<List<ComputerDto>> findAll(@RequestBody ComputerQuery computerQuery) throws InvalidPageSizeException, InvalidPageIdException {
    List<ComputerDto> computers = computerService
        .getByNameOrCompanyName(computerQuery.getPageId(), computerQuery.getPageSize(), "", computerQuery.getOrderBy(), computerQuery.getOrderDirection()).getContent();
    return new ResponseEntity<List<ComputerDto>>(computers, HttpStatus.OK);
  }

  @GetMapping(path = "/{id}")
  public ComputerDto find(@PathVariable("id") long id) {
    return computerService.getById(id).get();
  }

  @PostMapping
  public ResponseEntity<ComputerDto> create(@RequestBody ComputerDto computer) {
    Optional<ComputerDto> optionalComputerDto = computerService.add(computer);
    return optionalComputerDto.isPresent() ? new ResponseEntity<ComputerDto>(optionalComputerDto.get(), HttpStatus.CREATED)
        : new ResponseEntity<ComputerDto>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<ComputerDto> update(@RequestBody ComputerDto computer, @PathVariable("id") long id) {
    boolean exists = computerService.checkExistenceById(id);

    if (exists) {
      computer.setId(id);
      computerService.update(computer);
      return new ResponseEntity<ComputerDto>(computer, HttpStatus.OK);
    }

    return new ResponseEntity<ComputerDto>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
    boolean exists = computerService.checkExistenceById(id);

    if (exists) {
      return new ResponseEntity<Boolean>(computerService.deleteById(id), HttpStatus.OK);
    }

    return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
  }
}
