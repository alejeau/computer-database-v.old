package com.excilys.formation.computerdb.pagination;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.persistence.Fields;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ComputerPager extends Pager {

	protected List<Computer> list = null;
	protected ComputerDatabaseServiceImpl services = null;

	public ComputerPager(int objectsPerPages) {
		super(objectsPerPages);
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
		this.list = new ArrayList<Computer>(this.objectsPerPages);
		this.nbEntries = services.getNbComputers();
		this.maxPageNumber = (int) Math.ceil(nbEntries / this.objectsPerPages);
		this.list = services.getComputersFromToSortedBy(currentPageNumber * objectsPerPages, objectsPerPages, this.field, this.ascending);
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
	
	public void setOrder(boolean ascending) {
		this.ascending = ascending;
		update();
	}
	
	public void setField(Fields field) {
		this.field = field;
		update();
	}

	public boolean goToPageNumber(int page) {
		if (page > this.maxPageNumber) {
			return false;
		}

		this.currentPageNumber = page;
		this.update();
		return true;
	}

	@Override
	public void setObjectsPerPages(int nb) {
		this.objectsPerPages = nb;
		this.list = new ArrayList<Computer>(this.objectsPerPages);
		this.nbEntries = services.getNbComputers();
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

	@Override
	public void update() {
		this.nbEntries = services.getNbComputers();
		this.maxPageNumber = (int) Math.ceil(nbEntries / this.objectsPerPages);
		revalidatePageNumber();
		this.list = services.getComputersFromToSortedBy(currentPageNumber * objectsPerPages, objectsPerPages, this.field, this.ascending);
	}
}
