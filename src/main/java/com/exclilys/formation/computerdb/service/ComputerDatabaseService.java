package com.exclilys.formation.computerdb.service;

import java.util.List;

import com.exclilys.formation.computerdb.model.Company;
import com.exclilys.formation.computerdb.model.Computer;

public interface ComputerDatabaseService {
	/**
	 * @param from
	 * @param nb
	 * @return
	 */
	List<Computer> getComputersFromTo(int from, int nb);
	int getNbComputers();
	int getNbCompanies();
	List<Computer> getAllComputers();
	/**
	 * @param id
	 * @return
	 */
	Computer getComputerById(Long id);
	List<Company> getAllCompanies();
	/**
	 * @param from
	 * @param nb
	 * @return
	 */
	List<Company> getCompaniesFromTo(int from, int nb);
	void createComputer(Computer c);
	void updateComputer(Computer c);
	void deleteComputer(Long id);
}
