package com.excilys.formation.computerdb.pagination;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ComputerPager extends Pager {

	protected List<Computer> list = null;
	protected ComputerDatabaseServiceImpl services = null;

	public ComputerPager(int objectsPerPages) {
		super(objectsPerPages);
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
		this.list = new ArrayList<Computer>(this.objectsPerPages);
		this.maxPageNumber = (int) Math.ceil(services.getNbComputers() / this.objectsPerPages);
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

	/**
	 * Reloads the list with the current page from the database
	 */
	protected void updateList() {
		this.list = services.getComputersFromTo(currentPageNumber * objectsPerPages, objectsPerPages);
	}

	public void finalize() {
		this.list = null;
	}
}
