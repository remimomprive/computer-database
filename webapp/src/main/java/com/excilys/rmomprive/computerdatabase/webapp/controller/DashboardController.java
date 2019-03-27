package com.excilys.rmomprive.computerdatabase.webapp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.rmomprive.computerdatabase.binding.ComputerDto;
import com.excilys.rmomprive.computerdatabase.binding.ComputerMapper;
import com.excilys.rmomprive.computerdatabase.validation.ComputerValidator;
import com.excilys.rmomprive.computerdatabase.validation.ValidationException;
import com.excilys.rmomprive.computerdatabase.core.Company;
import com.excilys.rmomprive.computerdatabase.core.Computer;
import com.excilys.rmomprive.computerdatabase.service.ICompanyService;
import com.excilys.rmomprive.computerdatabase.service.IComputerService;
import com.excilys.rmomprive.computerdatabase.persistence.Page;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageSizeException;
import com.excilys.rmomprive.computerdatabase.persistence.InvalidPageIdException;

@Controller
@RequestMapping({"/", "/dashboard"})
public class DashboardController {
  private Logger LOGGER;
  private IComputerService computerService;

  public DashboardController(IComputerService computerService, ICompanyService companyService) {
    this.LOGGER = LoggerFactory.getLogger(DashboardController.class);
    this.computerService = computerService;
  }

  @GetMapping
  public String get(@RequestParam(name = "page_size", defaultValue = "10") int pageSize,
      @RequestParam(name = "page_id", defaultValue = "1") int pageId,
      @RequestParam(name = "order_by", defaultValue = "name") String orderBy,
      @RequestParam(name = "order_direction", defaultValue = "asc") String orderDirection,
      @RequestParam(name = "search", defaultValue = "") String search, Principal principal, Model model) {
    Page<ComputerDto> page = null;
    int rowCount = 0;
    int pageCount = 0;

    try {
      rowCount = computerService.getRowCount(search);
      pageCount = getPageCount(pageSize, search);
      page = computerService.getByNameOrCompanyName(pageId, pageSize, search, orderBy, orderDirection);
    } catch (InvalidPageIdException | InvalidPageSizeException | DataAccessException e) {
      LOGGER.error(e.getClass().toString());
    }

    model.addAttribute("computers", page);
    model.addAttribute("computerCount", rowCount);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("pageId", pageId);
    model.addAttribute("pageCount", pageCount);
    model.addAttribute("search", search);
    model.addAttribute("orderBy", orderBy);
    model.addAttribute("orderDirection", orderDirection);
    model.addAttribute("user", principal.getName());

    return "dashboard";
  }

  @PostMapping
  public String post(@RequestParam(name = "selection", defaultValue = "") String selection, Model model) {
    String[] idsString = selection.split(",");
    List<Long> ids = new ArrayList<>();
    for (String id : idsString) {
      ids.add(Long.valueOf(id));
    }
    LOGGER.info("Created id list : " + ids.toString());

    try {
      computerService.deleteByIds(ids);
      LOGGER.info("Successfully deleted computers {}", ids);
    } catch (DataAccessException e) {
      LOGGER.error("An error happened while trying to delete computers {} : {}", ids, e.getMessage());
    }
    
    return "redirect:/";
  }

  public int getPageCount(int pageSize, String search) throws DataAccessException {
    return computerService.getPageCount(pageSize, search);
  }
}
