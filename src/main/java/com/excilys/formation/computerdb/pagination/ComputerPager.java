package com.excilys.formation.computerdb.pagination;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdb.exceptions.ComputerCreationException;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ComputerPager extends Pager {

	protected List<Computer> list = null;
	protected ComputerDatabaseServiceImpl services = null;

	public ComputerPager(int objectsPerPages) throws ComputerCreationException {
		super(objectsPerPages);
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
		this.list = new ArrayList<Computer>(this.objectsPerPages);
		this.nbEntries = services.getNbComputers();
		this.maxPageNumber = (int) Math.ceil(nbEntries / this.objectsPerPages);
		this.updateList();
	}

	public List<Computer> getPreviousPage() throws ComputerCreationException {
		if (prevPage()) {
			this.updateList();
		}
		return this.list;
	}

	public List<Computer> getCurrentPage() {
		return this.list;
	}

	public List<Computer> getNextPage() throws ComputerCreationException {
		if (nextPage()) {
			this.updateList();
		}
		return this.list;
	}

	public boolean goToPageNumber(int page) throws Exception {
		if (page > this.maxPageNumber) {
			return false;
		}

		this.currentPageNumber = page;
		this.updateList();
		return true;
	}

	@Override
	public void setObjectsPerPages(int nb) throws Exception {
		this.objectsPerPages = nb;
		this.list = new ArrayList<Computer>(this.objectsPerPages);
		this.nbEntries = services.getNbComputers();
		this.maxPageNumber = (int) Math.ceil(this.nbEntries / this.objectsPerPages);
		revalidatePageNumber();
		this.updateList();
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
	 * @throws ComputerCreationException 
	 */
	protected void updateList() throws ComputerCreationException {
		this.list = services.getComputersFromTo(currentPageNumber * objectsPerPages, objectsPerPages);
	}
}
