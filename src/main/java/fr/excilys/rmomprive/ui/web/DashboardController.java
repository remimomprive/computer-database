package fr.excilys.rmomprive.ui.web;

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
import fr.excilys.rmomprive.dto.IDto;
import fr.excilys.rmomprive.exception.InvalidPageIdException;
import fr.excilys.rmomprive.exception.InvalidPageSizeException;
import fr.excilys.rmomprive.mapper.ComputerMapper;
import fr.excilys.rmomprive.model.Computer;
import fr.excilys.rmomprive.pagination.Page;
import fr.excilys.rmomprive.service.IComputerService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
  private Logger LOGGER;
  private IComputerService computerService;
  private ComputerMapper computerMapper;
  
  public DashboardController(IComputerService computerService, ComputerMapper computerMapper) {
    this.LOGGER = LoggerFactory.getLogger(DashboardController.class);
    this.computerService = computerService;
    this.computerMapper = computerMapper;
  }

  @GetMapping
  public String get(@RequestParam(name = "page_size", defaultValue = "10") int pageSize,
      @RequestParam(name = "page_id", defaultValue = "1") int pageId,
      @RequestParam(name = "order_by", defaultValue = "name") String orderBy,
      @RequestParam(name = "order_direction", defaultValue = "asc") String orderDirection,
      @RequestParam(name = "search", defaultValue = "") String search, Model model) {
    Page<IDto<Computer>> page = null;
    int rowCount = 0;
    int pageCount = 0;

    try {
      page = computerService
          .getByNameOrCompanyName(pageId, pageSize, search, orderBy, orderDirection)
          .createDtoPage(this.computerMapper);
      rowCount = computerService.getRowCount(search);
      pageCount = getPageCount(pageSize, search);
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

    return "dashboard";
  }

  @PostMapping
  public void post(@RequestParam(name = "selection", defaultValue = "") String selection,
      Model model) {
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
  }

  public int getPageCount(int pageSize, String search) throws DataAccessException {
    return computerService.getPageCount(pageSize, search);
  }
}
