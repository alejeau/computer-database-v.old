package com.excilys.formation.computerdb.persistence;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.excilys.formation.computerdb.exceptions.connections.ConnectionErrorException;
import java.sql.Connection;

public class ConnectionFactory {
	private static final String FILE_PROPERTIES		= "dao.properties";
	private static final String PROPERTY_URL		= "url";
	private static final String PROPERTY_DRIVER		= "driver";
	private static final String PROPERTY_USERNAME	= "utilisateur";
	private static final String PROPERTY_PASSWORD	= "password";

	protected static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class);

	private static final ConnectionFactory INSTANCE = new ConnectionFactory();

	private static Properties properties;

//	protected Connection connec = null;

	/**
	 * Returns a Singleton of ConnectionFactory
	 * @return a Singleton of ConnectionFactory
	 */
	public static ConnectionFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * Private computer to allow a Singleton
	 */
	private ConnectionFactory() {
		properties = new Properties();

		try {
			properties.load(ConnectionFactory.class.getClassLoader().getResourceAsStream(FILE_PROPERTIES));

		} catch (IOException e) {
			LOGGER.error("Failed to load properties file!");
		}

		// chargement du driver jdbc
		try {
			Class.forName(properties.getProperty(PROPERTY_DRIVER));
		} catch (ClassNotFoundException e) {
			LOGGER.error("Failed to load driver!");
		}

		
	}

	/**
	 * Returns the connection initialized by the constructor
	 * @return the connection initialized by the constructor
	 */
	public Connection getConnection() {
		Connection connec = null;
		try {
			connec = DriverManager.getConnection(properties.getProperty(PROPERTY_URL), properties.getProperty(PROPERTY_USERNAME), properties.getProperty(PROPERTY_PASSWORD));
		} catch (SQLException e) {
			LOGGER.fatal("Failed to retrieve Connection!");
			LOGGER.fatal("Connection infos:\turl: " + properties.getProperty(PROPERTY_URL) + "\tusername: " + properties.getProperty(PROPERTY_USERNAME));
			throw new ConnectionErrorException("Failed to retrieve Connection!");
		}
		return connec;
	}

	/**
	 * Closes the connection initialized by the constructor
	 */
//	public void close() {
//		try {
//			this.connec.close();
//		} catch (SQLException e) {
//			LOGGER.error("Couldn't close the connection!");
//		}
//	}
}