package com.excilys.formation.computerdb.persistence.impl;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.computerdb.persistence.ConnectionFactory;

@Component
public class ConnectionFactoryImpl implements ConnectionFactory {

	@Autowired
	private DataSource dataSource;
	
	// Logger
	private final Logger LOGGER = Logger.getLogger(ConnectionFactoryImpl.class);

	public ConnectionFactoryImpl() {
	}

	/**
	 * Returns the connection initialized by the constructor
	 * 
	 * @return the connection initialized by the constructor
	 */
	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			LOGGER.fatal("Couldn't get connection!");
		}
		return null;
	}

	public Connection getTransaction() {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			LOGGER.fatal("Couldn't get connection!");
		}
		return connection;
	}

	public void commitTransaction(Connection connection) throws SQLException {
		connection.commit();
	}

	public void rollbackTransaction(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			LOGGER.fatal("Couldn't rollback the modifications!");
		}
	}

	/**
	 * 
	 * @return
	 */
	public Connection endTransaction(Connection connection) {
		try {
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			LOGGER.fatal(e.getMessage());
		}
		return connection;
	}
}