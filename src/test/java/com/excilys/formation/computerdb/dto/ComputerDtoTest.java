package com.excilys.formation.computerdb.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.Test;

import com.excilys.formation.computerdb.dto.model.ComputerDto;
import com.excilys.formation.computerdb.exceptions.ComputerCreationException;

public class ComputerDtoTest {
private static final long 	COMPUTER_ID 	= 0;
private static final String COMPUTER_NAME 	= "TESTOUILLE";
private static final String COMPUTER_INTRO 	= "2016-03-01";
private static final String COMPUTER_OUTRO 	= "2016-03-17";
private static final String COMPANY 		= "Testy";


@Test
public void constructor() throws ComputerCreationException  {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
	assertNotNull(c);
}

@Test
public void getId() throws ComputerCreationException {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
	assertEquals(COMPUTER_ID, c.getId());
}

@Test
public void getCompany() throws ComputerCreationException {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
	assertEquals(COMPANY, c.getCompany());
}

@Test
public void getName() throws ComputerCreationException {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
	assertEquals(COMPUTER_NAME, c.getName());
}

@Ignore
public void getNameNull() throws ComputerCreationException {
	new ComputerDto(COMPUTER_ID, null, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
}

@Test
public void getIntro() throws ComputerCreationException {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
	assertEquals(COMPUTER_INTRO, c.getIntro());
}

@Test
public void getOutro() throws ComputerCreationException {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY);
	assertEquals(COMPUTER_OUTRO, c.getOutro());
}

@Test
public void getIntroNull() throws ComputerCreationException {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, null, COMPUTER_OUTRO, COMPANY);
	assertNull(c.getIntro());
}

@Test
public void getOutroNull() throws ComputerCreationException {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, null, COMPANY);
	assertNull(c.getOutro());
}

}