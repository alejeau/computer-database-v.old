package com.excilys.formation.computerdb.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Ignore;
import org.junit.Test;

import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;

public class ComputerTest  {
	private static final long 	 COMPANY_ID = 0;
	private static final String  COMPANY_NAME = "Testy";
	private static final Company COMPANY = new Company(COMPANY_ID, COMPANY_NAME);
	
	private static final long 	COMPUTER_ID = 0;
	private static final String COMPUTER_NAME = "TESTOUILLE";
	private static final String COMPUTER_INTRO = "2016-03-01";
	private static final String COMPUTER_OUTRO = "2016-03-17";
	
	@Test
	public void constructor() {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
		assertNotNull(c);
	}

	@Test
	public void getId() {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
		assertEquals(COMPUTER_ID, c.getId());
	}
	
	@Test
	public void getCompany() {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
		assertEquals(COMPANY, c.getCompany());
	}

	@Test
	public void getName() {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
		assertEquals(COMPUTER_NAME, c.getName());
	}

	@Ignore
	public void getNameNull() {
		new Computer(COMPUTER_ID, null, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
	}
	
	@Test
	public void getIntro() {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
		LocalDate ld = LocalDate.parse(COMPUTER_INTRO);
		assertEquals(ld, c.getIntro());
	}
	
	@Test
	public void getOutro() {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
		LocalDate ld = LocalDate.parse(COMPUTER_OUTRO);
		assertEquals(ld, c.getOutro());
	}
	
	@Test
	public void getIntroNull() {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, null, COMPUTER_OUTRO, COMPANY);
		assertNull(c.getIntro());
	}
	
	@Test
	public void getOutroNull() {
		Computer c = new Computer(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, null, COMPANY);
		assertNull(c.getOutro());
	}

}
