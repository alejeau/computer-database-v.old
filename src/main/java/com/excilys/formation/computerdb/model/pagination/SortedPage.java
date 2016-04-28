package com.excilys.formation.computerdb.model.pagination;

import java.util.LinkedList;
import java.util.List;

import com.excilys.formation.computerdb.constants.Fields;

public class SortedPage<T> extends Page<T> {
	protected Fields field;
	protected boolean ascending;

	public SortedPage() {
		super();
		this.field = Fields.NAME;
		this.ascending = true;
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
	 */
	public SortedPage(List<T> list, int currentPageNumber, int maxPageNumber, int objectsPerPages, int nbEntries,
			Fields field, boolean ascending) {
		super(list, currentPageNumber, maxPageNumber, objectsPerPages, nbEntries);
		this.field = field;
		this.ascending = ascending;
	}

	public Fields getField() {
		return field;
	}

	public void setField(Fields field) {
		this.field = field;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (ascending ? 1231 : 1237);
		result = prime * result + ((field == null) ? 0 : field.hashCode());
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
		SortedPage<?> other = (SortedPage<?>) obj;
		if (ascending != other.ascending)
			return false;
		if (field != other.field)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SortedPage [field=" + field + ", ascending=" + ascending + ", currentPageNumber=" + currentPageNumber
				+ ", maxPageNumber=" + maxPageNumber + ", objectsPerPages=" + objectsPerPages + ", nbEntries="
				+ nbEntries + "]";
	}

	public static class Builder<T> {
		private List<T> nestedPage = null;
		private int nestedNbEntries = -1;
		private int nestedMaxPageNumber = -1;
		private int nestedObjectsPerPages = -1;
		private int nestedCurrentPageNumber = -1;
		private Fields nestedField = Fields.NAME;
		private boolean nestedAscending = true;

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

		public SortedPage<T> build() {
			return new SortedPage<T>(nestedPage, nestedCurrentPageNumber, nestedMaxPageNumber, nestedObjectsPerPages,
					nestedNbEntries, nestedField, nestedAscending);
		}
	}

}
