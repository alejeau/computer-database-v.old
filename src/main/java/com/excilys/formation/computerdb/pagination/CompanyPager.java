package com.excilys.formation.computerdb.pagination;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class CompanyPager extends Pager {

	protected List<Company> list = null;
	protected ComputerDatabaseServiceImpl services = null;

	public CompanyPager(int objectsPerPages) {
		super(objectsPerPages);
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
		this.list = new ArrayList<Company>(this.objectsPerPages);
		this.nbEntries = services.getNbCompanies();
		this.maxPageNumber = (int) Math.ceil(nbEntries / this.objectsPerPages);
		this.updateList();
	}

	public List<Company> getPreviousPage() {
		if (prevPage()) {
			this.updateList();
		}
		return this.list;
	}

	public List<Company> getCurrentPage() {
		return this.list;
	}

	public List<Company> getNextPage() {
		if (nextPage()) {
			this.updateList();
		}
		return this.list;
	}

	public boolean goToPageNumber(int page) {
		if ((page < 0) && (page > this.maxPageNumber)) {
			return false;
		}

		this.currentPageNumber = page;
		this.updateList();
		return true;
	}

	@Override
	public void setObjectsPerPages(int nb) {
		this.objectsPerPages = nb;
		this.list = new ArrayList<Company>(this.objectsPerPages);
		this.nbEntries = services.getNbCompanies();
		this.maxPageNumber = (int) Math.ceil(this.nbEntries / this.objectsPerPages);
		this.updateList();
	}

	/**
	 * Reloads the list with the current page from the database
	 */
	protected void updateList() {
		this.list = services.getCompaniesFromTo(currentPageNumber * objectsPerPages, objectsPerPages);
	}
}
