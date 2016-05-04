package com.excilys.formation.computerdb.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.computerdb.model.Company;

public class CompanyTest {
	private static final long ID = 0;
	private static final String NAME = "Testy";
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructor() {
		Company c = new Company(ID, NAME);
		assertNotNull(c);
	}

	@Test
	public void getId(){
		Company c = new Company(ID, NAME);
		assertEquals(ID, c.getId());
	}

	@Test
	public void getName(){
		Company c = new Company(ID, NAME);
		assertEquals(NAME, c.getName());
	}

}
