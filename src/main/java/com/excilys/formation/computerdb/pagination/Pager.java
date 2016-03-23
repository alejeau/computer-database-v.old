package com.excilys.formation.computerdb.pagination;

public abstract class Pager {
	protected int currentPageNumber;
	protected int maxPageNumber;
	protected int objectsPerPages;
	protected int nbEntries;

	Pager(int objectsPerPages) {
		this.currentPageNumber = 0;
		this.objectsPerPages = objectsPerPages;
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public int getObjectsPerPages() {
		return objectsPerPages;
	}

	public int getMaxPageNumber() {
		return maxPageNumber;
	}

	public int getNbEntries() {
		return this.nbEntries;
	}

	public abstract void setObjectsPerPages(int nb) throws Exception;

	public abstract boolean goToPageNumber(int page) throws Exception;

	/**
	 * Returns true if there is a previous page available, false else
	 * @return true if there is a previous page available, false else
	 */
	protected boolean prevPage() {
		if (this.currentPageNumber > 0) {
			this.currentPageNumber--;
			return true;
		}
		return false;
	}

	/**
	 * Returns true if there is a next page available, false else
	 * @return true if there is a next page available, false else
	 */
	protected boolean nextPage() {
		if (this.currentPageNumber < this.maxPageNumber) {
			this.currentPageNumber++;
			return true;
		}
		return false;
	}
	
	/**
	 * Reloads the list with the current page from the database
	 * and updates the number of computers and its dependencies
	 * in order to keep the informations displayed accurate.
	 *//**
	 * Reloads the list with the current page from the database
	 */
	public abstract void update();
}
