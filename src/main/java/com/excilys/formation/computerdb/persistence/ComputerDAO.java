package com.excilys.formation.computerdb.persistence;

import java.util.List;

import com.excilys.formation.computerdb.model.Computer;

public interface ComputerDAO {
	List<Computer> getAll();
	List<Computer> getFromTo(int from, int to);
	int getNbEntries();
	int createComputer(Computer computer);
	void updateComputer(Computer computer);
	Computer getComputerById(long id);
	Computer getComputerByName(String name);
	void deleteComputer(long id);
	void deleteComputer(String name);
}
