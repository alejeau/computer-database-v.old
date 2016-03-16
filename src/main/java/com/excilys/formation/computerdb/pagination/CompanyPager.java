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
		this.list = services.getCompaniesFromTo(currentPageNumber * this.objectsPerPages, this.objectsPerPages);
	}

	public List<Company> getPreviousPage() {
		if (prevPage()) {
			this.update();
		}
		return this.list;
	}

	public List<Company> getCurrentPage() {
		return this.list;
	}

	public List<Company> getNextPage() {
		if (nextPage()) {
			this.update();
		}
		return this.list;
	}

	public boolean goToPageNumber(int page) {
		if ((page < 0) && (page > this.maxPageNumber)) {
			return false;
		}

		this.currentPageNumber = page;
		this.update();
		return true;
	}

	@Override
	public void setObjectsPerPages(int nb) {
		this.objectsPerPages = nb;
		this.list = new ArrayList<Company>(this.objectsPerPages);
		this.nbEntries = services.getNbCompanies();
		this.maxPageNumber = (int) Math.ceil(this.nbEntries / this.objectsPerPages);
		this.update();
	}


	public void revalidatePageNumber() {
		if (this.currentPageNumber > this.maxPageNumber) {
			this.currentPageNumber = this.maxPageNumber;
		}
		if (this.currentPageNumber < 0) {
			this.currentPageNumber = 0;
		}
	}
	
	/**
	 * Reloads the list with the current page from the database
	 */
	protected void update() {
		this.nbEntries = services.getNbCompanies();
		this.maxPageNumber = (int) Math.ceil(nbEntries / this.objectsPerPages);
		revalidatePageNumber();
		this.list = services.getCompaniesFromTo(currentPageNumber * objectsPerPages, objectsPerPages);
	}
}
