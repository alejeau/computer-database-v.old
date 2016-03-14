package com.excilys.formation.computerdb.persistence;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;


import java.sql.Connection;

public class ConnectionFactory {
	private static final String FILE_PROPERTIES				= "dao.properties";
	private static final String PROPERTY_URL				= "url";
	private static final String PROPERTY_DRIVER				= "driver";
	private static final String PROPERTY_NOM_UTILISATEUR	= "utilisateur";
	private static final String PROPERTY_MOT_DE_PASSE		= "password";

	protected static final Logger logger = Logger.getLogger(ConnectionFactory.class);
	
	private final static ConnectionFactory _instance = new ConnectionFactory();

	private static Properties properties;
	
	protected Connection connec = null;

	/**
	 * Returns a Singleton of ConnectionFactory
	 * @return a Singleton of ConnectionFactory
	 */
	public static ConnectionFactory getInstance(){
		return _instance;
	}

	/**
	 * Private computer to allow a Singleton
	 */
	private ConnectionFactory() {
		properties = new Properties();

		try {
			properties.load(ConnectionFactory.class.getClassLoader().getResourceAsStream(FILE_PROPERTIES));

		} catch (IOException e) {
			logger.error("Failed to load properties file!");
		}

		// chargement du driver jdbc
		try {
			Class.forName(properties.getProperty(PROPERTY_DRIVER));
		} catch (ClassNotFoundException e) {
			logger.error("Failed to load driver!");
		}
		
		try {
			connec = DriverManager.getConnection(properties.getProperty(PROPERTY_URL), properties.getProperty(PROPERTY_NOM_UTILISATEUR), properties.getProperty(PROPERTY_MOT_DE_PASSE));
		} catch (SQLException e) {
			logger.error("Failed to retrieve Connection!");
		}
	}
	
	/**
	 * Returns the connection initialized by the constructor
	 * @return the connection initialized by the constructor
	 */
	public Connection getConnection(){
		return connec;
	}

	/**
	 * Closes the connection initialized by the constructor
	 */
	public void close(){
		try {
			this.connec.close();
		} catch (SQLException e) {
			logger.error("Couldn't close the connection!");
		}
	}
}