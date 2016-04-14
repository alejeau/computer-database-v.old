package com.excilys.formation.computerdb.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.formation.computerdb.persistence.impl.ConnectionFactoryImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/applicationContext.xml" })
public class ConnectionFactoryTest {
//	protected final Logger LOG = LoggerFactory.getLogger(ConnectionFactoryTest.class);

	@Autowired
	ConnectionFactoryImpl cf;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void getConnection(){
		Connection connec = null;
		connec = cf.getConnection();
		assertNotNull(connec);
	}

	@Test
	public void getValidConnection(){
		Connection connec = null;
		connec = cf.getConnection();
		boolean isValid = false;
		try {
			isValid = connec.isValid(1);
		} catch (SQLException e) {
//			LOG.error(e.getMessage());
		}
		assertTrue(isValid);
	}
}
