package com.excilys.formation.computerdb.model.pagination;

import java.util.LinkedList;
import java.util.List;

import com.excilys.formation.computerdb.constants.Fields;

public class SearchPage<T> extends SortedPage<T> {
	String search;

	public SearchPage() {
		super();
		this.search = null;
	}

	/**
	 * Creates a page of T.
	 * 
	 * @param list a list to put in the page
	 * @param currentPageNumber the current page number
	 * @param maxPageNumber the maximum page number
	 * @param nbEntries the number of entries
	 * @param field the sort field (from enum Fields)
	 * @param ascending true if the sort is ascending, false else
	 * @param search the String to look for in the database
	 */
	public SearchPage(List<T> list, int currentPageNumber, int maxPageNumber, int objectsPerPages, int nbEntries,
			Fields field, boolean ascending, String search) {
		super(list, currentPageNumber, maxPageNumber, objectsPerPages, nbEntries, field, ascending);
		this.search = search;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((search == null) ? 0 : search.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchPage<?> other = (SearchPage<?>) obj;
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search))
			return false;
		return true;
	}

	public static class Builder<T> {
		private List<T> nestedPage = null;
		private int nestedNbEntries = -1;
		private int nestedMaxPageNumber = -1;
		private int nestedObjectsPerPages = -1;
		private int nestedCurrentPageNumber = -1;
		private Fields nestedField = null;
		private boolean nestedAscending = true;
		private String nestedSearch = null;

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

		public Builder<T> field(Fields field) {
			this.nestedField = field;
			return this;
		}

		public Builder<T> ascending(boolean ascending) {
			this.nestedAscending = ascending;
			return this;
		}

		public Builder<T> search(String search) {
			this.nestedSearch = search;
			return this;
		}

		public Page<T> build() {
			return new SearchPage<T>(nestedPage, nestedCurrentPageNumber, nestedMaxPageNumber, nestedObjectsPerPages,
					nestedNbEntries, nestedField, nestedAscending, nestedSearch);
		}
	}

}
