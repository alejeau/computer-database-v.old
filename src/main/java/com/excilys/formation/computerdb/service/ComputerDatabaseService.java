package com.excilys.formation.computerdb.service;

import java.util.List;

import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;

public interface ComputerDatabaseService {
	boolean alreadyExists(String name);
	int getNbCompanies();
	int getNbComputers();
	int getNbComputersNamed(String search);
	Company getCompanyById(Long id);
	Company getCompanyByName(String name);
	Computer getComputerById(Long id);
	Computer getComputerByName(String name);
	List<Company> getAllCompanies();
	List<Company> getCompaniesFromTo(int from, int nb);
	List<Computer> getAllComputers();
	List<Computer> getComputersFromTo(int from, int nb);
	List<Computer> getComputersNamedFromTo(String search, int from, int to);
	List<Problem> createComputer(Computer c);

	/**
	 * Creates a computer
	 * @param name the computer's name
	 * @param intro the date of introduction
	 * @param outro the date of end
	 * @param comp the computer's manufacturer
	 */
	List<Problem> createComputer(String name, String intro, String outro, Company comp);
	
	/**
	 * Creates a computer
	 * @param id the computer's id
	 * @param name the computer's name
	 * @param intro the date of introduction
	 * @param outro the date of end
	 * @param comp the computer's manufacturer
	 */
	List<Problem> createComputer(long id, String name, String intro, String outro, Company comp);
	List<Problem> updateComputer(Computer c, String oldName);
	void deleteComputer(Long id);
	void deleteComputer(String name);
}
