package com.excilys.formation.computerdb.pagination.pager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.pagination.Page;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

@Component
public class CompanyPager {
	@Autowired
	private ComputerDatabaseServiceImpl services;

	public CompanyPager() {
	}

	public Page<Company> firstPage(Page<Company> sp) {
		return services.getCompanyPage(0, sp);
	}

	public Page<Company> nextPage(Page<Company> sp) {
		int next = sp.getCurrentPageNumber() + 1;
		if (next > sp.getMaxPageNumber()) {
			return sp;
		}

		return services.getCompanyPage(next, sp);
	}

	public Page<Company> prevPage(Page<Company> sp) {
		int prev = sp.getCurrentPageNumber() - 1;
		if (prev < 0) {
			return sp;
		}

		return services.getCompanyPage(prev, sp);
	}

	public Page<Company> goToPage(int pageNumber, Page<Company> sp) {
		if (pageNumber > sp.getMaxPageNumber()) {
			pageNumber = sp.getMaxPageNumber();
		}

		if (pageNumber < 0) {
			pageNumber = 0;
		}

		return services.getCompanyPage(pageNumber, sp);
	}

}
