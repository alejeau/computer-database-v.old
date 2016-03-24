package com.excilys.formation.computerdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.computerdb.model.Computer;

public interface ComputerDAO {
	boolean exists(String name);
	boolean exists(Computer computer);
	
	List<Computer> getAll();
	List<Computer> getAllSortedBy(Fields field, boolean ascending);
	List<Computer> getFromTo(int offset, int limit);
	List<Computer> getFromToSortedBy(int offset, int limit, Fields field, boolean ascending);
	List<Computer> getNamedFromTo(String name, int offset, int limit);
	List<Computer> getNamedFromToSortedBy(String name, int offset, int limit, Fields field, boolean ascending);
	
	int getNbEntries();
	int getNbEntriesNamed(String name);
	int createComputer(Computer computer);
	
	Computer getComputerById(long id) ;
	Computer getComputerByName(String name) ;
	
	void updateComputer(Computer computer);
	void deleteComputer(long id);
	void deleteComputer(String name);
	void deleteComputers(long[] list, Connection connection) throws SQLException;
	void deleteComputersWhereCompanyIdEquals(long id, Connection connection) throws SQLException;
}
