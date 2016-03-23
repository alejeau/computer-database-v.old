package com.excilys.formation.computerdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.computerdb.model.Computer;

public interface ComputerDAO {
	boolean exists(String name);
	boolean exists(Computer computer);
	List<Computer> getAll() ;
	List<Computer> getFromTo(int offset, int limit) ;
	List<Computer> getNamedFromTo(String name, int offset, int limit) ;
	int getNbEntries();
	int getNbEntriesNamed(String name);
	int createComputer(Computer computer);
	void updateComputer(Computer computer);
	Computer getComputerById(long id) ;
	Computer getComputerByName(String name) ;
	void deleteComputer(long id);
	void deleteComputer(String name);
	void deleteComputers(long[] list, Connection connection) throws SQLException;
	void deleteComputersWhereCompanyIdEquals(long id, Connection connection) throws SQLException;
}
