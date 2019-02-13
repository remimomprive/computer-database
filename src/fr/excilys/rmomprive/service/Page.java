package fr.excilys.rmomprive.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fr.excilys.rmomprive.model.Company;
import fr.excilys.rmomprive.model.Entity;

public class Page {
	private List<Entity> content;
	private Page previous;
	private Page next;
	
	public Page() {
		this.content = new ArrayList<>();
	}

	public List<Entity> getContent() {
		return content;
	}

	public void setContent(List<Entity> content) {
		this.content = content;
	}

	public Page getPrevious() {
		return previous;
	}

	public void setPrevious(Page previous) {
		this.previous = previous;
	}

	public Page getNext() {
		return next;
	}

	public void setNext(Page next) {
		this.next = next;
	}
	
	@Override
	public String toString() {
		return "Page [content=" + content + ", previous=" + (previous != null) + ", next=" + (next != null) + "]";
	}

	public static List<Page> getPages(List<Entity> entities, int pageSize) {
		List<Page> pages = new ArrayList<>();
		
		
		Iterator<Entity> iterator = entities.listIterator();
		// Create pages
		while (iterator.hasNext()) {
			Page page = new Page();
	
			int i = 0;
			while (iterator.hasNext() && i < pageSize) {
				Entity entity = iterator.next();
				page.getContent().add(entity);
				i++;
			}
			
			pages.add(page);
		}
		
		// Set next values to pages
		for (int i = 0; i < pages.size() - 1; i++) {
			pages.get(i).setNext(pages.get(i + 1));
		}
		
		// Set previous values to pages
		for (int i = 1; i < pages.size(); i++) {
			pages.get(i).setPrevious(pages.get(i - 1));
		}

		return pages;
	}
	
	/**
	 * This method tests the pagination class
	 * @param args
	 */
	public static void main(String[] args) {
		List<Entity> companies = Arrays.asList(
			new Company(1, "a"),
			new Company(2, "b"),
			new Company(3, "a"),
			new Company(4, "b"),
			new Company(5, "a"),
			new Company(6, "b"),
			new Company(7, "a"),
			new Company(8, "b"),
			new Company(9, "a"),
			new Company(10, "b"),
			new Company(11, "a"),
			new Company(12, "b")
		);
		
		System.out.printf("%d companies\n", companies.size());
		
		List<Page> pages = Page.getPages(companies, 5);
		System.out.println(pages);
		
		System.out.printf("%d pages\n", pages.size());
	}
}
