package com.excilys.formation.computerdb.mapper.request;

import com.excilys.formation.computerdb.constants.Paths;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.model.pagination.SearchPage;

/**
 * Page encapsulating a SearchPage and its associated URL
 */
public class ComputerSearchPageRequest {

	String url = Paths.PATH_DASHBOARD;
	SearchPage<Computer> page = new SearchPage<>();

	public ComputerSearchPageRequest() {
	}

	public ComputerSearchPageRequest(SearchPage<Computer> p, String url) {
		this.page = p;
		this.url = url;
	}

	public SearchPage<Computer> getComputerSearchPage() {
		return this.page;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SearchPage<Computer> getPage() {
		return page;
	}

	public void setPage(SearchPage<Computer> page) {
		this.page = page;
	}

}
