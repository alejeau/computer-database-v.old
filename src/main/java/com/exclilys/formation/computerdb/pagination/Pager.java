package com.exclilys.formation.computerdb.pagination;

public abstract class Pager {
	protected int currentPageNumber;
	protected int maxPageNumber;
	protected int objectsPerPages;
	
	Pager(int objectsPerPages){
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
	
	protected abstract boolean goToPageNumber(int page);
	
	/**
	 * 
	 * @return true if there is a previous page available, false else
	 */
	protected boolean prevPage(){
		if (this.currentPageNumber > 0){
			this.currentPageNumber--;
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return true if there is a next page available, false else
	 */
	protected boolean nextPage(){
		if (this.currentPageNumber < this.maxPageNumber){
			this.currentPageNumber++;
			return true;
		}
		return false;
	}
}
