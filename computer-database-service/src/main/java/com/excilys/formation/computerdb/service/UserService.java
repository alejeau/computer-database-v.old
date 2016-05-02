package com.excilys.formation.computerdb.service;

import com.excilys.formation.computerdb.model.User;

public interface UserService {

	/**
	 * Returns a User from a corresponding name.
	 * 
	 * @param name the User name
	 * @return the User with the name name
	 */
	public User getUser(String login);

	/**
	 * Adds a user to the database.
	 * 
	 * @param u the User to add
	 */
	public void createUser(User u);
	
	/**
	 * Deletes a User from the database.
	 * 
	 * @param u the User to delete
	 */
	public void deleteUser(String login);

}
