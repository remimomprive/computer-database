package fr.excilys.rmomprive.service;

import java.util.ArrayList;
import java.util.Arrays;
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
		return "Page [content=" + content + ", previous=" + previous + ", next=" + next + "]";
	}

	public static List<Page> getPages(List<Entity> entities, int pageSize) {
		List<Page> pages = new ArrayList<>();
		
		// Create pages
		/// TODO : fix infinite loop
		while (entities.iterator().hasNext()) {
			Page page = new Page();
	
			for (int i = 0; i < pageSize; i++) {
				Entity entity = entities.iterator().next();
				if (entity != null) {
					page.getContent().add(entity);
				}
			}
			
			pages.add(page);
		}
		
		// Set next values to pages
		for (int i = 0; i < pages.size() - 1; i++) {
			pages.get(i).setNext(pages.get(i + 1));
		}
		
		// Set previous values to pages
		for (int i = 1; i < pages.size(); i++) {
			pages.get(i).setNext(pages.get(i - 1));
		}

		return pages;
	}
	
	public static void main(String[] args) {
		List<Entity> companies = Arrays.asList(
			new Company(1, "a"),
			new Company(2, "b"),
			new Company(1, "a"),
			new Company(2, "b"),
			new Company(1, "a"),
			new Company(2, "b"),
			new Company(1, "a"),
			new Company(2, "b"),
			new Company(1, "a"),
			new Company(2, "b"),
			new Company(1, "a"),
			new Company(2, "b"),
			new Company(1, "a"),
			new Company(2, "b"),
			new Company(1, "a"),
			new Company(2, "b"),
			new Company(1, "a"),
			new Company(2, "b"),
			new Company(1, "a"),
			new Company(2, "b"),
			new Company(1, "a"),
			new Company(2, "b"),
			new Company(1, "a"),
			new Company(2, "b")
		);
		
		System.out.println(companies.size());
		
		List<Page> pages = Page.getPages(companies, 5);
		System.out.println(pages);
	}
}
