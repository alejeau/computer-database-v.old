package com.exclilys.formation.computerdb.persistence;

import java.util.List;

import com.exclilys.formation.computerdb.model.Company;

public interface CompanyDAO {
	List<Company> getAll();
	List<Company> getFromTo(int from, int nb);
	int getNbEntries();
}
