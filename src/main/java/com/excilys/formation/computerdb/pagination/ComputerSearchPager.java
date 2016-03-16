package com.excilys.formation.computerdb.pagination;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdb.exceptions.PagerSearchException;
import com.excilys.formation.computerdb.model.Computer;
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
		this.updateList();
	}

	public List<Computer> getPreviousPage() {
		if (prevPage()) {
			this.updateList();
		}
		return this.list;
	}

	public List<Computer> getCurrentPage() {
		return this.list;
	}

	public List<Computer> getNextPage() {
		if (nextPage()) {
			this.updateList();
		}
		return this.list;
	}

	public boolean goToPageNumber(int page) {
		if (page > this.maxPageNumber) {
			return false;
		}

		this.currentPageNumber = page;
		this.updateList();
		return true;
	}

	@Override
	public int getNbEntries() {
		return this.nbEntries;
	}
	
	public void setSearch(String search) throws PagerSearchException {
		if (search != null) {
			this.search = search;
		} else {
			throw new PagerSearchException("Null search field!");
		}
		this.nbEntries = services.getNbComputersNamed(this.search);
		this.maxPageNumber = (int) Math.ceil(this.nbEntries / this.objectsPerPages);
		this.updateList();
	}
	
	public void setObjectsPerPages(int obj) {
		this.objectsPerPages = obj;
		this.list = new ArrayList<Computer>(this.objectsPerPages);
		this.nbEntries = services.getNbComputersNamed(this.search);
		this.maxPageNumber = (int) Math.ceil(this.nbEntries / this.objectsPerPages);
		this.updateList();
	}

	/**
	 * Reloads the list with the current page from the database
	 */
	protected void updateList() {
		this.list = services.getComputersNamedFromTo(search, currentPageNumber * objectsPerPages, objectsPerPages);
	}
}