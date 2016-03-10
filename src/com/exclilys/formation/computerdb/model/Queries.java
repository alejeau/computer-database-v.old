package com.exclilys.formation.computerdb.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.exclilys.formation.computerdb.persistence.DBConnect;

public class Queries {
	public static final String TIMESTAMP_ZERO 	= "0000-00-00";
	public static final String COMPUTER 		= "computer";
	public static final String COMPANY 			= "company";
	protected DBConnect dbc = null;

	public Queries(){
		dbc = DBConnect.getInstance();
	}
	
	/**
	 * Lists the different computers/companies available and prints them out on the console
	 * 
	 * @param type Queries.COMPUTER or Queries.COMPANY
	 * @throws SQLException if the ResultSet can't work properly
	 */
	public void listAll(String type) throws SQLException{
		if (!checkType(type)){
			System.out.println("Wrong table name!");
		}
		else {
			ResultSet rs;
			String query = "select distinct name from " + type + ";";
			rs = dbc.executeQuery(query);
			while (rs.next()){
				try {
					System.out.println(rs.getString("name"));
				} catch (SQLException e) {
					System.out.println("\n\n*** Can't get " + type + " name! *** \n\n");
					e.printStackTrace();
				}
			}
		}
	}
	
	protected boolean checkType(String type){
		if (type.equals(COMPUTER) || type.equals(COMPANY))
			return true;
		
		return false;
	}
	
	/**
	 * Lists the different computers/companies available and prints them out on the console by pages
	 * 
	 * @param type Queries.COMPUTER or Queries.COMPANY
	 * @param pageNumber the number of the page to display
	 * @throws SQLException if the ResultSet can't work properly
	 */
	public void listByPages(String type, int pageNumber) throws SQLException{
		if (!checkType(type)){
			System.out.println("Wrong table name!");
		}
		else {
			ResultSet rs;
			pageNumber *= 20;
			String query = "select distinct name from " + type + " limit " + pageNumber + ", 20";
			rs = dbc.executeQuery(query);
			while (rs.next()){
				try {
					System.out.println(rs.getString("name"));
				} catch (SQLException e) {
					System.out.println("\n\n*** Can't get " + type + " name! *** \n\n");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Displays on the console the details of the computer which name is in argument
	 * 
	 * @param name a String of the computer's name
	 * @throws SQLException if the ResultSet can't work properly
	 */
	public void showDetails(String name) throws SQLException{
		ResultSet rs;
		String query = "select * from computer where name = '" + name + "'";
		//		System.out.println("query = " + query);
		rs = dbc.executeQuery(query);
		String introduced = "", discontinued = "", compName = "";
		int compId = -1;
		while (rs.next()){
			try {
				name = rs.getString("name");
			} catch (SQLException e) {
				System.out.println("\n\n*** Can't get computer name! *** \n\n");
				e.printStackTrace();
			}

			try {
				introduced = rs.getString("introduced");
			} catch (SQLException e) {
				System.out.println("\n\n*** Can't get intro date! *** \n\n");
				e.printStackTrace();
			}

			try {
				discontinued = rs.getString("discontinued");
			} catch (SQLException e) {
				System.out.println("\n\n*** Can't get outro date! *** \n\n");
				e.printStackTrace();
			}

			try {
				compId = rs.getInt("company_id");
			} catch (SQLException e) {
				System.out.println("\n\n*** Can't get company id! *** \n\n");
				e.printStackTrace();
			}
		}

		compName = getCompanyName(compId);

		System.out.println("Name : " + name);
		System.out.println("Introduced in : " + introduced);
		System.out.println("Discontinued in : " + discontinued);
		System.out.println("Manufacturer : " + compName);
	}

	/**
	 * Gets the name of a company via the provided id
	 * @param id the id of the company
	 * @return a String of the name of the company 
	 * @throws SQLException
	 */
	public String getCompanyName(int id) {
		ResultSet rs;
		rs = dbc.executeQuery("select name from company where id = '" + id + "'");
		String name = null;
		try {
			while (rs.next()){
				try {
					name = rs.getString("name");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}

	/**
	 * Gets the id of a company via the provided name
	 * @param name the name of the company
	 * @return an int which is the company's id
	 * @throws SQLException
	 */
	public int getCompanyId(String name) throws SQLException{
		ResultSet rs;
		rs = dbc.executeQuery("select id from company where name = '" + name + "'");
		int id = -1;
		while (rs.next()){
			try {
				id = rs.getInt("id");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}

	/**
	 * Gets the id of a computer via the provided name
	 * @param name the name of the computer
	 * @return an int which is the computer's id, -1 if the id couldn't be found
	 * @throws SQLException
	 */
	public int getComputerId(String name) throws SQLException{
		ResultSet rs;
		rs = dbc.executeQuery("select id from computer where name = '" + name + "'");
		int id = -1;
		while (rs.next()){
			try {
				id = rs.getInt("id");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}

	/**
	 * Adds a computer to the database given the name is at least provided
	 * @param nom name of the computer
	 * @param intro date of the compuer's introduction
	 * @param outro date of the compuer's discontinuation
	 * @param company the manufacturer's name
	 * @return 1 if success, 0 or less otherwise
	 */
	public int createComputer(String nom, String intro, String outro, String company){
		int id = -1;

		// if no names were given, we exit
		if ((nom == null) || (nom.equals(""))){
			return -2;
		}

		// else, if the computer's name already exists
		try {
			id = getComputerId(nom);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (id != -1)
			return -3;

		// if the company does not already exists
		try {
			id = getCompanyId(company);
		} catch (SQLException e) {
			System.out.println("Couldn't get company id");
			e.printStackTrace();
		} // we create it
		if (id == -1){
			createCompany(company);
			try {
				id = getCompanyId(company);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (id == -1)
				return id;
		}

		// if the dates aren't given, we set them to zero
		intro = checkDateValidity(intro);
		outro = checkDateValidity(outro);

		// now we insert the data into the database
		String values = "values ('" + nom + "', '" + intro + "', '" + outro + "', " + id + ");";
		String update = "insert into computer (name,introduced,discontinued,company_id) "+ values;

		int res = -1;
		res = dbc.executeUpdate(update);
		return res;
	}

	/**
	 * Adds a company to the database
	 * @param nom the name of the Company
	 * @return 1 if successful, 0 or less otherwise
	 */
	public int createCompany(String nom){
		int id = -1;
		int res = -1;
		try {
			id = getCompanyId(nom);
		} catch (SQLException e) {
			System.out.println("Couldn't get company id");
			e.printStackTrace();
		}
		if (id == -1){
			String values = "value ('" + nom + "');";
			String update = "insert into company (name) "+ values;
			res = dbc.executeUpdate(update);
		}
		return res;
	}

	/**
	 * Removes a company from the database
	 * @param name the name of the company
	 * @return 1 if successful, 0 or less otherwise
	 */
	public int deleteCompany(String name){
		int res = -2;
		// delete from company where id=cid;
		int id = -1;
		try {
			id = getCompanyId(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (id == -1)
			return id;
		String delete = "delete from company where id='" + id + "'";
		res = dbc.executeUpdate(delete);

		return res;
	}

	/**
	 * Removes a computer from the database
	 * @param name the name of the computer
	 * @return 1 if successful, 0 or less otherwise
	 */
	public int deleteComputer(String name){
		int res = -2;
		// delete from company where id=cid;
		int id = -1;
		try {
			id = getComputerId(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (id == -1)
			return id;
		//		String delete = "delete if exists from computer where id='" + id + "'";
		String delete = "delete from computer where id='" + id + "'";
		res = dbc.executeUpdate(delete);

		return res;
	}
	
	/**
	 * Updates infos about a computer in the database
	 * @param id the computer's id
	 * @param name the computer's name
	 * @param intro the computer's introduction date
	 * @param outro the computer's discontinuation date
	 * @param company the computer's manufacturer
	 * @return
	 */
	public int updateComputer(int id, String name, String intro, String outro, String company){
		int res = -1;

		// if the dates aren't given, we set them to zero
		intro = checkDateValidity(intro);
		outro = checkDateValidity(outro);
		if (!checkDates(intro, outro))
			return -2;

		int companyId = -1;

		// we get the company id, or create one if necessary
		try {
			companyId = getCompanyId(company);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (companyId == -1)
			companyId = createCompany(company);

		String update = "update computer set name='" + name + "', introduced='" + intro + "', discontinued='"
				+ outro + "', company_id='" + companyId + "' where id='" + id + "';";
		System.out.println("update = " + update);
		res = dbc.executeUpdate(update);
		return res;
	}

	/**
	 * Checks whether a date is empty or null, and formats it to TIMESTAMP_ZERO uf it is
	 * @param d the date to test
	 * @return a correct date
	 */
	protected static String checkDateValidity(String d){
		if ((d == null) || (d.equals("")))
			return TIMESTAMP_ZERO;
		return d;
	}

	/**
	 * Check whether before comes before after
	 * @param before the date that's supposed to be first
	 * @param after before the date that's supposed to be last
	 * @return true if the order's right, false else
	 */
	protected static boolean checkDates(String before, String after){
		if (after.equals(checkDateValidity(after)) || (before.compareTo(after) < 0))
			return true;

		return false;
	}
}
