package com.excilys.formation.computerdb.servlets.request;

import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.SortedPage;
import com.excilys.formation.computerdb.servlets.Paths;

public class ComputerSortedPageRequest {

	String url = Paths.PATH_DASHBOARD;
	SortedPage<Computer> page = new SortedPage<>();

	public ComputerSortedPageRequest() {
	}

	public ComputerSortedPageRequest(SortedPage<Computer> p, String url) {
		this.page = p;
		this.url = url;
	}

	public SortedPage<Computer> getComputerSortedPage() {
		return this.page;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SortedPage<Computer> getPage() {
		return page;
	}

	public void setPage(SortedPage<Computer> page) {
		this.page = page;
	}
}
