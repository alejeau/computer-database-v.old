package com.excilys.formation.computerdb.persistence;

import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.computerdb.constants.Fields;
import com.excilys.formation.computerdb.model.Computer;

public interface ComputerDao {
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
	
	/**
	 * Deletes a list of Computer.<br>
	 * This function needs a connection from a ThreadLocalConnection,
	 *  so be sure to store one before calling this function.
	 * @param list a list of Computer to delete from the database
	 * @throws SQLException if the deletion goes wrong
	 */
	void deleteComputers(long[] list) throws SQLException;
	
	/**
	 * Deletes a Company and the list of Computer associated.<br>
	 * This function needs a connection from a ThreadLocalConnection,
	 *  so be sure to store one before calling this function.
	 * @param id the id of the Company to delete
	 * @throws SQLException if the deletion goes wrong
	 */
	void deleteComputersWhereCompanyIdEquals(long id) throws SQLException;
}
