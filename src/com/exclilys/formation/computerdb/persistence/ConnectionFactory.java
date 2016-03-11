package com.exclilys.formation.computerdb.persistence;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Properties;

import org.apache.log4j.Logger;


import java.sql.Connection;

public class ConnectionFactory { // A renommer en ConnectionFactory
	private static final String FILE_PROPERTIES				= "dao.properties";
	private static final String PROPERTY_URL				= "url";
	private static final String PROPERTY_DRIVER				= "driver";
	private static final String PROPERTY_NOM_UTILISATEUR	= "utilisateur";
	private static final String PROPERTY_MOT_DE_PASSE		= "password";

	protected static final Logger logger = Logger.getLogger(ConnectionFactory.class);
	
	private final static ConnectionFactory _instance = new ConnectionFactory();

	private static Properties properties;
	
	protected Connection connec = null;

	public static ConnectionFactory getInstance(){
		return _instance;
	}

	// méthode déclarée statique car sinon nous devons l'appeler depuis une instance alors que ce n'sst pas le but
	// permet d'éviter la création d'un nouvel objet à chaque appel
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

	public Connection getConnection(){
		return connec;
	}

	public void close(){
		try {
			this.connec.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/*
	public void close(Connection conn, ResultSet rs, Statement stmt, PreparedStatement pstmt ){ 
		if (rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("Failed to close ResultSet");
			}
		}
		if (stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("Failed to close Statement");
			}
		}
		if (pstmt != null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				logger.error("Failed to close PreparedStatement");
			}
		}
		if (conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Failed to close connection");
			}
		}
	}
	//*/

}