package com.excilys.rmomprive.computerdatabase.webapp.controller.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComputerQuery {
  @JsonProperty("search")
  private String search;
  
  @JsonProperty("order_by")
  private String orderBy;
  
  @JsonProperty("order_direction")
  private String orderDirection;
  
  @JsonProperty("page_id")
  private int pageId;
  
  @JsonProperty("page_size")
  private int pageSize;

  public String getSearch() {
    return search;
  }

  public void setSearch(String search) {
    this.search = search;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public String getOrderDirection() {
    return orderDirection;
  }
  
  public void setOrderDirection(String orderDirection) {
    this.orderDirection = orderDirection;
  }
  
  public int getPageId() {
    return pageId;
  }

  public void setPageId(int pageId) {
    this.pageId = pageId;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
}
