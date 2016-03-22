package com.excilys.formation.computerdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.excilys.formation.computerdb.exceptions.ComputerCreationException;

public class ComputerTest  {
	private static final long 	 COMPANY_ID = 0;
	private static final String  COMPANY_NAME = "Testy";
	private static final Company COMPANY = new Company(COMPANY_ID, COMPANY_NAME);
	
	private static final long 	COMPUTER_ID = 0;
	private static final String COMPUTER_NAME = "TESTOUILLE";
	private static final String COMPUTER_INTRO = "2016-03-01";
	private static final String COMPUTER_OUTRO = "2016-03-17";
	

	@Before
	public void setUp() throws Exception  {
	}

	@After
	public void tearDown() throws Exception  {
	}



	@Test
	public void constructor() throws ComputerCreationException  {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
		assertNotNull(c);
	}

	@Test
	public void getId() throws ComputerCreationException {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
		assertEquals(COMPUTER_ID, c.getId());
	}
	
	@Test
	public void getCompany() throws ComputerCreationException {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
		assertEquals(COMPANY, c.getCompany());
	}

	@Test
	public void getName() throws ComputerCreationException {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
		assertEquals(COMPUTER_NAME, c.getName());
	}

	@Ignore
	public void getNameNull() throws ComputerCreationException {
		new Computer(COMPUTER_ID, null, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
	}
	
	@Test
	public void getIntro() throws ComputerCreationException {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
		LocalDate ld = LocalDate.parse(COMPUTER_INTRO);
		assertEquals(ld, c.getIntro());
	}
	
	@Test
	public void getOutro() throws ComputerCreationException {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
		LocalDate ld = LocalDate.parse(COMPUTER_OUTRO);
		assertEquals(ld, c.getOutro());
	}
	
	@Test
	public void getIntroNull() throws ComputerCreationException {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, null, COMPUTER_OUTRO, COMPANY);
		assertNull(c.getIntro());
	}
	
	@Test
	public void getOutroNull() throws ComputerCreationException {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, null, COMPANY);
		assertNull(c.getOutro());
	}

}
