package com.excilys.formation.computerdb.servlets.request;

import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.Page;
import com.excilys.formation.computerdb.pagination.SearchPage;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.servlets.Paths;

public class ComputerSearchPageRequest<T> {
	String url = Paths.PATH_DASHBOARD;
	SearchPage<Computer> page = new SearchPage<>();
	Page<T> p;
	
	public ComputerSearchPageRequest(SearchPage<Computer> p, String url){
		this.page = p;
		this.page = ComputerDatabaseServiceImpl.INSTANCE.getComputerSearchPage(page.getCurrentPageNumber(), page);
		this.url = url;
	}
	
	public SearchPage<Computer> getComputerSearchPage(){
		return this.page;
	}
	
	public String getUrl() {
		return this.url;
	}
}
