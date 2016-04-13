package com.excilys.formation.computerdb.servlets.request;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.SortedPage;
//import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.servlets.Paths;

//@Component
public class ComputerSortedPageRequest {

//	@Autowired
//	ComputerDatabaseServiceImpl services;

	String url = Paths.PATH_DASHBOARD;
	SortedPage<Computer> page = new SortedPage<>();

	public ComputerSortedPageRequest() {
	}

	public ComputerSortedPageRequest(SortedPage<Computer> p, String url) {
		this.page = p;
//		this.page = this.services.getComputerSortedPage(page.getCurrentPageNumber(), page);
		this.url = url;
	}

	public SortedPage<Computer> getComputerSortedPage() {
		return this.page;
	}

	public String getUrl() {
		return this.url;
	}

	public void set(SortedPage<Computer> p) {
		this.page = p;
	}

	public void set(String url) {
		this.url = url;
	}

	public void set(SortedPage<Computer> p, String url) {
		this.page = p;
//		this.page = this.services.getComputerSortedPage(page.getCurrentPageNumber(), page);
		this.url = url;
	}

}
