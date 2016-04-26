package com.excilys.formation.computerdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Company implements Comparable<Company> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected long id = -1;
	
	@Column(name = "name")
	protected String name = null;

	public Company() {
	}

	/**
	 * Creates a Company with a given name and id
	 * 
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
	
//	@Id
//	@GeneratedValue
//	@Column(name = "id")
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

	public static class Builder {
		private long nestedId = -1;
		private String nestedName = null;

		public Builder(final long id, final String name) {
			this.nestedId = id;
			this.nestedName = name;
		}

		public Builder() {
		}

		public Builder id(final long id) {
			this.nestedId = id;
			return this;
		}

		public Builder name(String name) {
			this.nestedName = name;
			return this;
		}

		public Company build() {
			return new Company(nestedId, nestedName);
		}

	}
}
