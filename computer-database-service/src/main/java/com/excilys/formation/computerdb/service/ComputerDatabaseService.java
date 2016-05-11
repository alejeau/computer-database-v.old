package com.excilys.formation.computerdb.service;

import java.util.List;

import com.excilys.formation.computerdb.constants.Fields;
import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.model.pagination.Page;
import com.excilys.formation.computerdb.model.pagination.SearchPage;
import com.excilys.formation.computerdb.model.pagination.SortedPage;

public interface ComputerDatabaseService {
	
	boolean existsCompany(String name);
	
	boolean existsCompany(Company c);
	
	boolean existsComputer(String name);
	
	boolean existsComputer(Computer c);

	int getNbCompanies();

	int getNbComputers();

	int getNbComputersNamed(String search);

	Company getCompanyById(Long id);

	Company getCompanyByName(String name);

	Computer getComputerById(Long id);

	Computer getComputerByName(String name);

	Page<Company> getAllCompanies();

	Page<Company> getCompanyPage(int pageNumber, Page<Company> p);

	Page<Computer> getAllComputers();

	/**
	 * Returns a List of Computer.
	 * 
	 * @param offset the start of the list
	 * @param limit the end of the list
	 * @return a List<Computer> which size is limit-offset
	 */
	List<Computer> getComputerList(int offset, int limit);

	/**
	 * Returns a List of Computer sorted by filed and ascending.
	 * 
	 * @param offset the start of the list
	 * @param limit the end of the list
	 * @param field the sorting field
	 * @param ascending whether the sort is ascending or not
	 * @return a List<Computer> which size is limit-offset sorted
	 */
	List<Computer> getComputerSortedList(int offset, int limit, Fields field, boolean ascending);

	/**
	 * Returns a SortedPage of Computer.
	 * 
	 * @param pageNumber the wanted page number
	 * @param sp the sorted page to fill
	 * @return a SortedPage<Computer> correctly filled
	 */
	SortedPage<Computer> getComputerSortedPage(int pageNumber, SortedPage<Computer> sp);

	/**
	 * Returns a SearchPage of Computer.
	 * 
	 * @param pageNumber the wanted page number
	 * @param sp the sorted page to fill
	 * @return a SearchPage<Computer> correctly filled
	 */
	SearchPage<Computer> getComputerSearchPage(int pageNumber, SearchPage<Computer> sp);

	/**
	 * Creates a computer
	 * 
	 * @param c the computer to create
	 * @return a List<Problem> of the potential problems encountered
	 */
	List<Problem> createComputer(Computer c);

	/**
	 * Creates a computer
	 * 
	 * @param name the computer's name
	 * @param intro the date of introduction
	 * @param outro the date of end
	 * @param comp the computer's manufacturer
	 * @return a List<Problem> of the potential problems encountered
	 */
	List<Problem> createComputer(String name, String intro, String outro, Company comp);

	/**
	 * Creates a computer
	 * 
	 * @param id the computer's id
	 * @param name the computer's name
	 * @param intro the date of introduction
	 * @param outro the date of end
	 * @param comp the computer's manufacturer
	 * @return a List<Problem> of the potential problems encountered
	 */
	List<Problem> createComputer(long id, String name, String intro, String outro, Company comp);

	void updateComputer(Computer c);
	
	/**
	 * Update a computer. The name of the previous computer is required in order to check whether
	 * the computer's new name is already taken or not.
	 * 
	 * @param c the computer to update
	 * @param oldName the name of the previous computer
	 * @return a List<Problem> of the potential problems encountered
	 */
	List<Problem> updateComputer(Computer c, String oldName);

	void deleteComputer(Computer c);
	
	void deleteComputer(Long id);

	void deleteComputer(String name);

	void deleteComputers(long[] listId);

	/**
	 * Deletes the Company c and all the related computers from the database.
	 * 
	 * @param c the company to remove from the database
	 */
	void deleteCompany(Company c);

	List<Company> listAllCompanies();

	List<Computer> listAllComputers();

	List<Company> getCompanyList(int offset, int limit);

	List<Computer> getComputerSearchList(String keyword, int pageNumber, int objectPerPages);

	List<Computer> getComputerSearchList(String keyword, int offset, int limit, Fields field, boolean ascending);

	void deleteCompany(long id);
}
