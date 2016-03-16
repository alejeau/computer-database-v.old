package com.excilys.formation.computerdb.persistence;

import java.util.List;

import com.excilys.formation.computerdb.exceptions.ComputerCreationException;
import com.excilys.formation.computerdb.model.Computer;

public interface ComputerDAO {
	List<Computer> getAll() throws ComputerCreationException;
	List<Computer> getNamedFromTo(String name, int from, int to) throws ComputerCreationException;
	List<Computer> getFromTo(int from, int to) throws ComputerCreationException;
	int getNbEntries();
	int getNbEntriesNamed(String name);
	int createComputer(Computer computer);
	void updateComputer(Computer computer);
	Computer getComputerById(long id) throws ComputerCreationException;
	Computer getComputerByName(String name) throws ComputerCreationException;
	void deleteComputer(long id);
	void deleteComputer(String name);
}
