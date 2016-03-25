package com.excilys.formation.computerdb.servlets.request;

import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.Page;
import com.excilys.formation.computerdb.pagination.SortedPage;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.servlets.Paths;

public class PageRequest<T> {
	String url = Paths.PATH_DASHBOARD;
	SortedPage<Computer> page = new SortedPage<>();
	Page<T> p;
	
	public PageRequest(SortedPage<Computer> p, String url){
		this.page = p;
		this.page = ComputerDatabaseServiceImpl.INSTANCE.getComputerSortedPage(page.getCurrentPageNumber(), page);
		this.url = url;
	}
	
	public SortedPage<Computer> getComputerSortedPage(){
		return this.page;
	}
	
	public String getUrl() {
		return this.url;
	}
	
}
