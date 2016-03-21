package com.excilys.formation.computerdb.model;

public class Company implements Comparable<Company> {
	protected String name = null;
	protected long id = -1;
	
	public Company() {}

	/**
	 * Creates a Company with a given name and id
	 * @param id a long representing the company id in the database
	 * @param name the name of the company
	 */
	public Company(long id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		Company other = (Company) obj;

		if (id != other.id) {
			return false;
		}

		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}

		return true;
	}

	@Override
	public int compareTo(Company c) {
		return this.name.compareTo(c.getName());
	}

	@Override
	public String toString() {
		return name;
	}
	
	public static class CompanyBuilder {
		private long nestedId = -1;
		private String nestedName = null;
		
		public CompanyBuilder(final long cId, final String cName) {
			this.nestedId = cId;
			this.nestedName = cName;
		}
		
		public CompanyBuilder() {
		}

		public CompanyBuilder id(final long cId) {
			this.nestedId = cId;
			return this;
		}
		
		public CompanyBuilder name(String cName) {
			this.nestedName = cName;
			return this;
		}
		
		public Company build() {
			return new Company(nestedId, nestedName);
		}
		
	}
}
