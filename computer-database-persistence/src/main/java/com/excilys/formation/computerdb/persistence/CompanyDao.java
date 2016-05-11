package com.excilys.formation.computerdb.persistence;

import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.computerdb.model.Company;

public interface CompanyDao {

	public boolean exists(String name);

	public boolean exists(Company c);

	/**
	 * Returns a list of all the Companies in the database
	 * 
	 * @return a list of all the Companies in the database
	 */
	public List<Company> getAll();

	/**
	 * Returns a List of Company from offset to offset+limit.
	 * 
	 * @param offset the start point.
	 * @param limit the end point.
	 * @return a List of Company from offset to offset+limit.
	 */
	public List<Company> getFromTo(int offset, int limit);

	/**
	 * Returns the number of entries in the database (i.e. the number of Company).
	 * 
	 * @return the number of Company
	 */
	public int getNbEntries();

	/**
	 * Returns a company from a corresponding ID.
	 * 
	 * @param id the company id
	 * @return the Company with the ID id.
	 */
	public Company getCompanyById(long id);

	/**
	 * Returns a company from a corresponding name.
	 * 
	 * @param name the company name
	 * @return the Company with the name name.
	 */
	public Company getCompanyByName(String name);

	/**
	 * Deletes a Company from the database
	 * 
	 * @param id the Company id
	 * @throws SQLException
	 */
	public void deleteCompany(long id) throws SQLException;
}
