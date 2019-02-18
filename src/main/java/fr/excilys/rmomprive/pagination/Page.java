package fr.excilys.rmomprive.pagination;

import java.util.List;

public class Page<T> {
	private List<T> content;
	private int pageId;
	private boolean previous;
	private boolean next;
	
	public Page(final List<T> content, final int pageId, final boolean previous, final boolean next) {
		this.content = content;
		this.pageId = pageId;
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
		return "Page [content=" + content + ", pageId=" + pageId + ", previous=" + previous + ", next=" + next + "]";
	}
}
