package com.excilys.formation.computerdb.persistence;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.computerdb.persistence.ConnectionFactory;

public class ConnectionFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void factory(){
		ConnectionFactory cf = null;
		cf = ConnectionFactory.getInstance();
		assertNotEquals(null, cf);
	}
	
	@Test
	public void getConnection(){
		Connection connec = null;
		ConnectionFactory cf = null;
		cf = ConnectionFactory.getInstance();
		connec = cf.getConnection();
		assertNotEquals(null, connec);
	}

	@Test
	public void getValidConnection(){
		Connection connec = null;
		ConnectionFactory cf = null;
		cf = ConnectionFactory.getInstance();
		connec = cf.getConnection();
		boolean isValid = false;
		try {
			isValid = connec.isValid(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(isValid);
	}
}
