package com.exclilys.formation.computerdb.persistence;

import java.util.List;

import com.exclilys.formation.computerdb.model.Computer;

public interface ComputerDAO {
	List<Computer> getAll();
	List<Computer> getFromTo(int from, int to);
	int getNbEntries();
	int createComputer(Computer computer);
	void updateComputer(Computer computer);
	Computer getComputerById(long id);
	void deleteComputer(long id);
}
