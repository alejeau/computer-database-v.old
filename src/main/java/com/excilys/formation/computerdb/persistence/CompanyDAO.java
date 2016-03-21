package com.excilys.formation.computerdb.persistence;

import java.util.List;

import com.excilys.formation.computerdb.model.Company;

public interface CompanyDAO {
	public List<Company> getAll();
	public List<Company> getFromTo(int offset, int limit);
	public int getNbEntries();
}
