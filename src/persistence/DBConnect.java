package persistence;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class DBConnect {

	protected static final String DB_USER = "admincdb";
	protected static final String DB_PASS = "qwerty1234";
	protected static final String DB_URL  = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	// ?zeroDateTimeBehavior=convertToNull converts zero date to null
	
	protected static Connection connec;
	private final static DBConnect _instance = new DBConnect();

	public static DBConnect getInstance(){
		return _instance;
	}
	
	/**
	 * Init the MySQL driver and starts the connection
	 */
	private DBConnect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Can't load class!");
			e.printStackTrace();
		}
		try {
			connec = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (SQLException e) {
			System.out.println("Can't get connection!");
			e.printStackTrace();
		}
	}

	/*
	public Connection getConnection(){
		return connec;
	}
	//*/

	/**
	 * Executes a MySQL query of type "SELECT FROM WHERE"
	 * @param query the query to execute
	 * @return the ResultSet from the request
	 */
	public ResultSet executeQuery(String query){
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = connec.createStatement();
		} catch (SQLException e) {
			System.out.println("Can't create statement!");
			e.printStackTrace();
		}
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("Query error!");
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Executes a MySQL query of type INSERT/UPDATE/DELETE
	 * @param update the query to execute
	 * @return the result from the request
	 */
	public int executeUpdate(String update){
		int rs = -1;
		Statement stmt = null;
		try {
			stmt = connec.createStatement();
		} catch (SQLException e) {
			System.out.println("Can't create statement!");
			e.printStackTrace();
		}
		try {
			rs = stmt.executeUpdate(update);
		} catch (SQLException e) {
			System.out.println("Query error!");
			e.printStackTrace();
		}
		return rs;
	}


	/**
	 * Closes the connection to the database
	 */
	public static void close(){
		try {
			connec.close();
		} catch (SQLException e) {
			System.out.println("Couln't close the connection!");
			e.printStackTrace();
		}
	}

	/**
	 * Closes the connection to the database when the object dies
	 */
	protected void finalize(){
		try {
			if (!connec.isClosed()){
				try {
					connec.close();
				} catch (SQLException e) {
					System.out.println("Couln't close the connection!");
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
