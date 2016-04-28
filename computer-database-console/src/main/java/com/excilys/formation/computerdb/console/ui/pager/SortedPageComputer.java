package com.excilys.formation.computerdb.ui.pager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.model.pagination.SortedPage;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

@Component
public class SortedPageComputer {

	@Autowired
	private ComputerDatabaseServiceImpl services;

	public SortedPageComputer() {
	}

	public SortedPage<Computer> firstPage(SortedPage<Computer> sp) {
		return services.getComputerSortedPage(0, sp);
	}

	public SortedPage<Computer> nextPage(SortedPage<Computer> sp) {
		int next = sp.getCurrentPageNumber() + 1;
		if (next > sp.getMaxPageNumber()) {
			return sp;
		}

		return services.getComputerSortedPage(next, sp);
	}

	public SortedPage<Computer> prevPage(SortedPage<Computer> sp) {
		int prev = sp.getCurrentPageNumber() - 1;
		if (prev < 0) {
			return sp;
		}

		return services.getComputerSortedPage(prev, sp);
	}

	public SortedPage<Computer> goToPage(int pageNumber, SortedPage<Computer> sp) {
		if (pageNumber > sp.getMaxPageNumber()) {
			pageNumber = sp.getMaxPageNumber();
		} else if (pageNumber < 0) {
			pageNumber = 0;
		}

		return services.getComputerSortedPage(pageNumber, sp);
	}
}
