package com.excilys.formation.computerdb.persistence;

import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.computerdb.constants.Fields;
import com.excilys.formation.computerdb.model.Computer;

public interface ComputerDao {

	/**
	 * Checks whether a computer with the name name exists.
	 * 
	 * @param name the computer name.
	 * @return true if the computer exists, false else.
	 */
	boolean exists(String name);

	/**
	 * Checks whether a Computer exists. The computer MUST HAVE a valid ID.
	 * 
	 * @param computer the Computer to check
	 * @return true if the computer exists, false else.
	 */
	boolean exists(Computer computer);

	/**
	 * Returns a List of all the Computer in the database.
	 * 
	 * @return a List of all the Computer in the database.
	 */
	List<Computer> getAll();

	/**
	 * Returns all the Computer from the database sorted by Field, ascending or descending.
	 * 
	 * @param field the sort Field
	 * @param ascending whether the Field is ascending or not
	 * @return all Computer sorted by Field
	 */
	List<Computer> getAllSortedBy(Fields field, boolean ascending);

	/**
	 * Returns a List of Computer from offset to offset+limit.
	 * 
	 * @param offset the start point.
	 * @param limit the end point.
	 * @return a List of Company from offset to offset+limit.
	 */
	List<Computer> getFromTo(int offset, int limit);

	/**
	 * Returns a List of Computer sorted by Field, ascending or not, from offset to offset+limit.
	 * 
	 * @param offset the start point.
	 * @param limit the end point.
	 * @param field the sort Field
	 * @param ascending whether the Field is ascending or not
	 * @return a List of Company from offset to offset+limit
	 */
	List<Computer> getFromToSortedBy(int offset, int limit, Fields field, boolean ascending);

	/**
	 * Returns a List of Computer containing 'name' from offset to offset+limit.
	 * 
	 * @param name the researched name
	 * @param offset the start point
	 * @param limit the end point
	 * @return
	 */
	List<Computer> getNamedFromTo(String name, int offset, int limit);

	/**
	 * Returns a List of Computer containing 'name', sorted by Field, ascending or not, from offset
	 * to offset+limit.
	 * 
	 * @param name the researched name
	 * @param offset the start point
	 * @param limit the end point
	 * @param field the sort Field
	 * @param ascending whether the Field is ascending or not
	 * @return a List of Company from offset to offset+limit
	 */
	List<Computer> getNamedFromToSortedBy(String name, int offset, int limit, Fields field, boolean ascending);

	/**
	 * Returns the number of entries in the database
	 * 
	 * @return the number of entries in the database
	 */
	int getNbEntries();

	/**
	 * Returns the number of entries in the database containing 'name'
	 * 
	 * @param name the researched name
	 * @return the number of entries in the database containing 'name'
	 */
	int getNbEntriesNamed(String name);

	/**
	 * Adds a Computer to the database
	 * 
	 * @param computer the computer to add
	 * @return -1 if failure
	 */
	void createComputer(Computer computer);

	/**
	 * Returns the computer with the ID id, null else.
	 * 
	 * @param id the ID of the researched computer
	 * @return the computer with the ID id, null else
	 */
	Computer getComputerById(long id);

	/**
	 * 
	 * @param name id the name of the researched computer
	 * @return the computer with the name 'name', null else
	 */
	Computer getComputerByName(String name);

	/**
	 * Updates a computer.
	 * 
	 * @param computer the computer to update
	 */
	void updateComputer(Computer computer);

	/**
	 * Deletes a computer from the database.
	 * 
	 * @param c the Computer to delete
	 */
	void deleteComputer(Computer c);

	/**
	 * Deletes a computer from the database from a given ID.
	 * 
	 * @param id the computer's id
	 */
	void deleteComputer(long id);

	/**
	 * Deletes a computer from the database from a given name.
	 * 
	 * @param name the computer's name
	 */
	void deleteComputer(String name);

	/**
	 * Deletes a list of Computer.<br>
	 * This function needs a connection from a ThreadLocalConnection, so be sure to store one before
	 * calling this function.
	 * 
	 * @param list a list of Computer to delete from the database
	 * @throws SQLException if the deletion goes wrong
	 */
	void deleteComputers(long[] list) throws SQLException;

	/**
	 * Deletes a Company and the list of Computer associated.<br>
	 * This function needs a connection from a ThreadLocalConnection, so be sure to store one before
	 * calling this function.
	 * 
	 * @param id the id of the Company to delete
	 * @throws SQLException if the deletion goes wrong
	 */
	void deleteComputersWhereCompanyIdEquals(long id) throws SQLException;
}
