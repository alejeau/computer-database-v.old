package com.excilys.formation.computerdb.model.pagination;

import java.util.LinkedList;
import java.util.List;

public class Page<T> {
	protected List<T> page;
	protected int currentPageNumber;
	protected int maxPageNumber;
	protected int objectsPerPages;
	protected int nbEntries;

	public Page() {
		this.page = null;
		this.nbEntries = -1;
		this.maxPageNumber = -1;
		this.objectsPerPages = -1;
		this.currentPageNumber = -1;
	}

	/**
	 * Creates a page of T.<br>
	 * The number of objects per page is based on the size of the list given in argument, so if your
	 * list is empty, do not forget to set it manually!
	 * 
	 * @param list a list to put in the page
	 * @param currentPageNumber the current page number
	 * @param maxPageNumber the maximum page number
	 * @param nbEntries the number of entries
	 */
	public Page(List<T> list, int currentPageNumber, int maxPageNumber, int objectsPerPages, int nbEntries) {
		this.page = list;
		this.objectsPerPages = objectsPerPages;

		this.nbEntries = nbEntries;
		this.maxPageNumber = maxPageNumber;
		this.currentPageNumber = currentPageNumber;
		if (this.objectsPerPages > 0) {
			double nbe = (double) this.nbEntries;
			double opp = (double) this.objectsPerPages;
			this.maxPageNumber = (int) Math.ceil(nbe / opp);
		}
	}

	public List<T> getPage() {
		return page;
	}

	public void setPage(List<T> page) {
		this.page = page;
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	public int getMaxPageNumber() {
		return maxPageNumber;
	}

	public void setMaxPageNumber(int maxPageNumber) {
		this.maxPageNumber = maxPageNumber;
	}

	public int getObjectsPerPages() {
		return objectsPerPages;
	}

	public void setObjectsPerPages(int objectsPerPages) {
		this.objectsPerPages = objectsPerPages;
	}

	public int getNbEntries() {
		return nbEntries;
	}

	public void setNbEntries(int nbEntries) {
		this.nbEntries = nbEntries;
		if (this.objectsPerPages > 0) {
			double nbe = (double) this.nbEntries;
			double opp = (double) this.objectsPerPages;
			this.maxPageNumber = (int) Math.ceil(nbe / opp);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPageNumber;
		result = prime * result + maxPageNumber;
		result = prime * result + nbEntries;
		result = prime * result + objectsPerPages;
		result = prime * result + ((page == null) ? 0 : page.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page<?> other = (Page<?>) obj;
		if (currentPageNumber != other.currentPageNumber)
			return false;
		if (maxPageNumber != other.maxPageNumber)
			return false;
		if (nbEntries != other.nbEntries)
			return false;
		if (objectsPerPages != other.objectsPerPages)
			return false;
		if (page == null) {
			if (other.page != null)
				return false;
		} else if (!page.equals(other.page))
			return false;
		return true;
	}

	public static class Builder<T> {
		private List<T> nestedPage = null;
		private int nestedNbEntries = -1;
		private int nestedMaxPageNumber = -1;
		private int nestedObjectsPerPages = -1;
		private int nestedCurrentPageNumber = -1;

		public Builder() {
		}

		public Builder<T> page(List<T> list) {
			this.nestedPage = new LinkedList<T>(list);
			this.nestedPage.size();
			return this;
		}

		public Builder<T> nbEntries(int nbEntries) {
			this.nestedNbEntries = nbEntries;
			return this;
		}

		public Builder<T> maxPageNumber(int maxPageNumber) {
			this.nestedMaxPageNumber = maxPageNumber;
			return this;
		}

		public Builder<T> objectsPerPages(int objectsPerPages) {
			this.nestedObjectsPerPages = objectsPerPages;
			return this;
		}

		public Builder<T> currentPageNumber(int currentPageNumber) {
			this.nestedCurrentPageNumber = currentPageNumber;
			return this;
		}

		public Page<T> build() {
			return new Page<T>(nestedPage, nestedCurrentPageNumber, nestedMaxPageNumber, nestedObjectsPerPages,
					nestedNbEntries);
		}
	}
}
