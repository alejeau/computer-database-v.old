package com.excilys.formation.computerdb.service;

import java.util.List;

import com.excilys.formation.computerdb.dto.ComputerDTO;
import com.excilys.formation.computerdb.exceptions.ComputerCreationException;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;

public interface ComputerDatabaseService {
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
	void createComputer(Computer c);
	
	/**
	 * 
	 * @param name the computer's name 
	 * @param intro the date of introduction
	 * @param outro the date of end
	 * @param comp the computer's manufacturer
	 * @throws ComputerCreationException id the computer object could not be created
	 */
	void createComputer(String name, String intro, String outro, Company comp) throws ComputerCreationException;
	void createComputer(long id, String name, String intro, String outro, Company comp) throws ComputerCreationException;
	void updateComputer(Computer c);
	void deleteComputer(Long id);
	void deleteComputer(String name);

	ComputerDTO computerToComputerDTO(Computer computer);
	List<ComputerDTO> computersToComputersDTO(List<Computer> computers);
}
