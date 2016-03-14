package com.excilys.formation.computerdb.pagination;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.persistence.impl.ComputerDAOImpl;

public class ComputerPager extends Pager {

	protected List<Computer> list = null;
	protected ComputerDAOImpl dao = null;
	
	public ComputerPager(int objectsPerPages, ComputerDAOImpl dao) {
		super(objectsPerPages);
		this.dao = dao;
		this.list = new ArrayList<Computer>(this.objectsPerPages);
		this.maxPageNumber = (int) Math.ceil(dao.getNbEntries()/this.objectsPerPages);
		this.updateList();
	}

	public List<Computer> getPreviousPage() {
		if (prevPage()){
			this.updateList();
		}
		return this.list;
	}

	public List<Computer> getCurrentPage() {
		return this.list;
	}

	public List<Computer> getNextPage() {
		if (nextPage()){
			this.updateList();
		}
		return this.list;
	}
	
	public boolean goToPageNumber(int page){
		if (page > this.maxPageNumber)
			return false;
		
		this.currentPageNumber = page;
		this.updateList();
		return true;
	}
	
	/**
	 * Reloads the list with the current page from the database
	 */
	protected void updateList(){
		this.list = dao.getFromTo(currentPageNumber*objectsPerPages, objectsPerPages);
	}
	
	public void finalize(){
		this.list = null;
	}
}
