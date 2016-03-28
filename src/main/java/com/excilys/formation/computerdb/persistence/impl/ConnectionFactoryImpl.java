package com.excilys.formation.computerdb.persistence.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.excilys.formation.computerdb.persistence.ConnectionFactory;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.sql.Connection;

public enum ConnectionFactoryImpl implements ConnectionFactory {
//	public static final ConnectionFactoryImpl INSTANCE = new ConnectionFactoryImpl();
	INSTANCE;
	
	// Config parameters
	private final String FILE_PROPERTIES = "dao.properties";
	private final String PROPERTY_URL = "url";
	private final String PROPERTY_DRIVER = "driver";
	private final String PROPERTY_USERNAME = "utilisateur";
	private final String PROPERTY_PASSWORD = "password";

	private String USERNAME;
	private String PASSWORD;
	private String URL;
	private String DRIVER;

	
	private Properties properties;
	
	// Logger
	private final Logger LOGGER = Logger.getLogger(ConnectionFactoryImpl.class);

	private BoneCP connectionPool = null;

	/**
	 * Returns a Singleton of ConnectionFactory
	 * 
	 * @return a Singleton of ConnectionFactory
	 */
	public static ConnectionFactoryImpl getInstance() {
		return INSTANCE;
	}

	/**
	 * Private computer to allow a Singleton
	 */
	private ConnectionFactoryImpl() {

		try {
			properties = new Properties();

			try {
				properties.load(ConnectionFactoryImpl.class.getClassLoader().getResourceAsStream(FILE_PROPERTIES));

			} catch (IOException e) {
				LOGGER.error("Failed to load properties file!");
			}

			URL = properties.getProperty(PROPERTY_URL);
			USERNAME = properties.getProperty(PROPERTY_USERNAME);
			PASSWORD = properties.getProperty(PROPERTY_PASSWORD);
			DRIVER = properties.getProperty(PROPERTY_DRIVER);
		} catch (Exception e) {
			LOGGER.error(e);
		}

		// loading JDBC driver
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Failed to load driver!");
		}

		try {
			BoneCPConfig config = new BoneCPConfig();
			// Setting up config
			config.setJdbcUrl(URL);
			config.setUsername(USERNAME);
			config.setPassword(PASSWORD);
			
			// Configuring pool size
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(5);

			// Creating pool from config via BoneCP object
			connectionPool = new BoneCP(config);
		} catch (SQLException e) {
			LOGGER.fatal("Pool connection config error!");
			throw new RuntimeException("Pool connection config error!", e);
		}

	}

	/**
	 * Returns the connection initialized by the constructor
	 * 
	 * @return the connection initialized by the constructor
	 */
	public Connection getConnection() {
		try {
			return connectionPool.getConnection();
		} catch (SQLException e) {
			LOGGER.fatal("Couldn't get connection!");
		}
		return null;
	}
	
	public Connection getTransaction() {
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
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