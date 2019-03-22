package com.excilys.rmomprive.computerdatabase.persistence;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
  private List<T> content;
  private int pageId;
  private int pageSize;
  private boolean previous;
  private boolean next;

  public Page() {
    this.content = new ArrayList<>();
  }

  public Page(final int pageId, final int pageSize) {
    this.content = new ArrayList<>();
    this.pageId = pageId;
    this.pageSize = pageSize;
  }

  /**
   * Constructs a Page object with the parameters.
   *
   * @param content  The page content (generally, a list of entities)
   * @param pageId   The current page id
   * @param pageSize The page size
   * @param previous true if the page is not the first in the page list, false if not
   * @param next     true if the page is not the last in the page list, false if not
   */
  public Page(final List<T> content, final int pageId, final int pageSize, final boolean previous, final boolean next) {
    this.content = content;
    this.pageId = pageId;
    this.pageSize = pageSize;
    this.previous = previous;
    this.next = next;
  }

  public List<T> getContent() {
    return content;
  }

  public final void setContent(final List<T> content) {
    this.content = content;
  }

  public final int getPageId() {
    return pageId;
  }

  public final void setPageId(final int pageId) {
    this.pageId = pageId;
  }

  public final int getPageSize() {
    return pageSize;
  }

  public final void setPageSize(final int pageSize) {
    this.pageSize = pageSize;
  }
  public final boolean isPrevious() {
    return previous;
  }

  public final void setPrevious(final boolean previous) {
    this.previous = previous;
  }

  public final boolean isNext() {
    return next;
  }

  public final void setNext(final boolean next) {
    this.next = next;
  }

  @Override
  public String toString() {
    return "Page [content=" + content + ", pageId=" + pageId + ", pageSize=" + pageSize + ", previous=" + previous + ", next="
        + next + "]";
  }
}
