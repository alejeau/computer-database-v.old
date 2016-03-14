package com.excilys.formation.computerdb.service;

import java.util.List;

import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;

public interface ComputerDatabaseService {
	int getNbComputers();
	int getNbCompanies();
	Company getCompanyById(Long id);
	Company getCompanyByName(String name);
	Computer getComputerById(Long id);
	Computer getComputerByName(String name);
	List<Company> getAllCompanies();
	List<Company> getCompaniesFromTo(int from, int nb);
	List<Computer> getAllComputers();
	List<Computer> getComputersFromTo(int from, int nb);
	void createComputer(Computer c);
	void updateComputer(Computer c);
	void deleteComputer(Long id);
	void deleteComputer(String name);
}
