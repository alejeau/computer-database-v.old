package com.excilys.formation.computerdb.pagination;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdb.exceptions.PagerSearchException;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.persistence.Fields;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ComputerSearchPager extends Pager {

	protected List<Computer> list = null;
	protected ComputerDatabaseServiceImpl services = null;
	protected String search = null;

	public ComputerSearchPager(int objectsPerPages) {
		super(objectsPerPages);
		this.list = new ArrayList<Computer>(this.objectsPerPages);
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
	}

	public ComputerSearchPager(String search, int objectsPerPages) throws PagerSearchException {
		super(objectsPerPages);
		if (search != null) {
			this.search = search;
		} else {
			throw new PagerSearchException("Null search field!");
		}

		this.services = ComputerDatabaseServiceImpl.INSTANCE;
		this.list = new ArrayList<Computer>(this.objectsPerPages);
		this.nbEntries = services.getNbComputersNamed(this.search);
		this.maxPageNumber = (int) Math.ceil(this.nbEntries / this.objectsPerPages);
		this.list = services.getComputersNamedFromToSortedBy(search, currentPageNumber * objectsPerPages, objectsPerPages, this.field, this.ascending);
	}

	public List<Computer> getPreviousPage() {
		if (prevPage()) {
			this.update();
		}
		return this.list;
	}

	public List<Computer> getCurrentPage() {
		return this.list;
	}

	public List<Computer> getNextPage() {
		if (nextPage()) {
			this.update();
		}
		return this.list;
	}

	public boolean goToPageNumber(int page) {
		this.currentPageNumber = page;
		revalidatePageNumber();
		
		if (this.currentPageNumber != page) {
			return false;
		}

		this.update();
		return true;
	}

	@Override
	public int getNbEntries() {
		return this.nbEntries;
	}

	/**
	 * Sets the new keyword for search, 
	 * and updates the pager accordingly.
	 */
	public void setSearch(String search) throws PagerSearchException {
		if (search != null) {
			this.search = search;
		} else {
			throw new PagerSearchException("Null search field!");
		}
		this.nbEntries = services.getNbComputersNamed(this.search);
		this.maxPageNumber = (int) Math.ceil(this.nbEntries / this.objectsPerPages);
		this.update();
	}

	/**
	 * Sets a new number of objects per pages, 
	 * and updates the pager accordingly.
	 */
	public void setObjectsPerPages(int obj) {
		this.objectsPerPages = obj;
		this.list = new ArrayList<Computer>(this.objectsPerPages);
		this.nbEntries = services.getNbComputersNamed(this.search);
		this.maxPageNumber = (int) Math.ceil(this.nbEntries / this.objectsPerPages);
		this.update();
	}
	
	public void setOrder(boolean ascending) {
		this.ascending = ascending;
		update();
	}
	
	public void setField(Fields field) {
		this.field = field;
		update();
	}
	
	/**
	 * Checks whether the current page number is valid, 
	 * and validates it otherwise.  
	 */
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
	 * and updates the number of computers and its dependencies
	 * in order to keep the informations displayed accurate.
	 *//**
	 * Reloads the list with the current page from the database
	 */
	@Override
	public void update() {
		this.nbEntries = this.services.getNbComputersNamed(this.search);
		this.maxPageNumber = (int) Math.ceil(this.nbEntries / this.objectsPerPages);
		revalidatePageNumber();
		this.list = services.getComputersNamedFromToSortedBy(search, currentPageNumber * objectsPerPages, objectsPerPages, this.field, this.ascending);
	}
}
