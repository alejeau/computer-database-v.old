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
	protected static Connection connec;
	
	private final static DBConnect _instance = new DBConnect();
	
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
	
	public Connection getConnection(){
		return connec;
	}
	
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
	
	public static void close(){
		try {
			connec.close();
		} catch (SQLException e) {
			System.out.println("Couln't close the connection!");
			e.printStackTrace();
		}
	}
	
	public static DBConnect getInstance(){
		return _instance;
	}
	
	protected void finalize(){
		try {
			connec.close();
		} catch (SQLException e) {
			System.out.println("Couln't close the connection!");
			e.printStackTrace();
		}
	}
	
	/*
	public static void main(String[] args) throws SQLException{
		DBConnect dbc = getInstance();
		ResultSet rs;
		String query = "select distinct name from computer";
		rs = dbc.executeQuery(query);
		while (rs.next()){
			try {
				System.out.println(rs.getString("name"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	//*/

}
