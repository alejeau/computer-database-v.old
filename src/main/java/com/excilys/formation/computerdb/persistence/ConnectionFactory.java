package com.excilys.formation.computerdb.persistence;

import java.sql.Connection;

public interface ConnectionFactory {
	/**
	 * Returns the connection initialized by the constructor
	 * @return the connection initialized by the constructor
	 */
	public Connection getConnection();
}