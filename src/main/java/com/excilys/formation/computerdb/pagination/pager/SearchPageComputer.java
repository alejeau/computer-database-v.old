package com.excilys.formation.computerdb.pagination.pager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.SearchPage;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

@Component
public class SearchPageComputer {
	@Autowired
	private ComputerDatabaseServiceImpl services;

	public SearchPageComputer() {
	}

	public SearchPage<Computer> firstPage(SearchPage<Computer> sp) {
		return services.getComputerSearchPage(0, sp);
	}

	public SearchPage<Computer> nextPage(SearchPage<Computer> sp) {
		int next = sp.getCurrentPageNumber() + 1;
		if (next > sp.getMaxPageNumber()) {
			return sp;
		}

		return services.getComputerSearchPage(next, sp);
	}

	public SearchPage<Computer> prevPage(SearchPage<Computer> sp) {
		int prev = sp.getCurrentPageNumber() - 1;
		if (prev < 0) {
			return sp;
		}

		return services.getComputerSearchPage(prev, sp);
	}

	public SearchPage<Computer> goToPage(int pageNumber, SearchPage<Computer> sp) {
		if (pageNumber > sp.getMaxPageNumber()) {
			pageNumber = sp.getMaxPageNumber();
		} else if (pageNumber < 0) {
			pageNumber = 0;
		}

		return services.getComputerSearchPage(pageNumber, sp);
	}
}
