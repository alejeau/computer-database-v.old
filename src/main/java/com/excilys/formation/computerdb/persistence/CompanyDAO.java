package com.excilys.formation.computerdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.computerdb.model.Company;

public interface CompanyDAO {
	public List<Company> getAll();
	public List<Company> getFromTo(int offset, int limit);
	public int getNbEntries();
	public void deleteCompany(long id, Connection connection) throws SQLException;
}
