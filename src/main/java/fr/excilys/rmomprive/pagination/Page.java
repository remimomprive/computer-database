package fr.excilys.rmomprive.pagination;

import java.util.List;

public class Page<T> {
	private List<T> content;
	private int pageId;
	private boolean previous;
	private boolean next;
	
	public Page(List<T> content, int pageId, boolean previous, boolean next) {
		this.content = content;
		this.pageId = pageId;
		this.previous = previous;
		this.next = next;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public boolean isPrevious() {
		return previous;
	}

	public void setPrevious(boolean previous) {
		this.previous = previous;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return "Page [content=" + content + ", pageId=" + pageId + ", previous=" + previous + ", next=" + next + "]";
	}
}
