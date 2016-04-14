package com.excilys.formation.computerdb.servlets.request;

import org.springframework.stereotype.Component;

import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.Page;
import com.excilys.formation.computerdb.pagination.SearchPage;
import com.excilys.formation.computerdb.servlets.Paths;

@Component
public class ComputerSearchPageRequest {
//	@Autowired
//	ComputerDatabaseServiceImpl services;

	String url = Paths.PATH_DASHBOARD;
	SearchPage<Computer> page = new SearchPage<>();
	Page<Computer> p;

	public ComputerSearchPageRequest() {
	}

	public ComputerSearchPageRequest(SearchPage<Computer> p, String url) {
		this.page = p;
//		this.page = this.services.getComputerSearchPage(page.getCurrentPageNumber(), page);
		this.url = url;
	}

	public SearchPage<Computer> getComputerSearchPage() {
		return this.page;
	}

	public String getUrl() {
		return this.url;
	}
}
