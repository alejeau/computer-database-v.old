package com.excilys.formation.computerdb.pagination.pager;

import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.SearchPage;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class SearchPageComputer {
private static ComputerDatabaseServiceImpl services = ComputerDatabaseServiceImpl.INSTANCE;
	
	public static SearchPage<Computer> firstPage(SearchPage<Computer> sp){
		return services.getComputerSearchPage(0, sp);
	}
	
	public static SearchPage<Computer> nextPage(SearchPage<Computer> sp){
		int next = sp.getCurrentPageNumber() + 1; 
		if (next > sp.getMaxPageNumber()) {
			return sp;
		}
		
		return  services.getComputerSearchPage(next, sp);
	}
	
	public static SearchPage<Computer> prevPage(SearchPage<Computer> sp){
		int prev = sp.getCurrentPageNumber() - 1; 
		if (prev < 0) {
			return sp;
		}
		
		return  services.getComputerSearchPage(prev, sp);
	}
	
	public static SearchPage<Computer> goToPage(int pageNumber, SearchPage<Computer> sp){
		if (pageNumber > sp.getMaxPageNumber()) {
			pageNumber = sp.getMaxPageNumber();
		} else if (pageNumber < 0) {
			pageNumber = 0;
		}
		
		return  services.getComputerSearchPage(pageNumber, sp);
	}
}
