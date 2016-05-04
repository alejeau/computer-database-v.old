package com.excilys.formation.computerdb.binding.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.Test;

import com.excilys.formation.computerdb.dto.model.ComputerDto;

public class ComputerDtoTest {
private static final long 	COMPUTER_ID 		= 0;
private static final String COMPUTER_NAME 		= "TESTOUILLE";
private static final String COMPUTER_INTRO 		= "01-03-2016";
private static final String COMPUTER_INTRO_ISO 	= "2016-03-01";
private static final String COMPUTER_OUTRO	 	= "17-03-2016";
private static final String COMPUTER_OUTRO_ISO 	= "2016-03-17";
private static final String COMPANY 			= "Testy";
private static final String LOCALE 				= "fr";


@Test
public void constructor() {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY, LOCALE);
	assertNotNull(c);
}

@Test
public void getId() {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY, LOCALE);
	assertEquals(COMPUTER_ID, c.getId());
}

@Test
public void getCompany() {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY, LOCALE);
	assertEquals(COMPANY, c.getCompany());
}

@Test
public void getName() {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY, LOCALE);
	assertEquals(COMPUTER_NAME, c.getName());
}

@Ignore
public void getNameNull() {
	new ComputerDto(COMPUTER_ID, null, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY, LOCALE);
}

@Test
public void getIntro() {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY, LOCALE);
	assertEquals(COMPUTER_INTRO_ISO, c.getIntro());
}

@Test
public void getOutro() {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, COMPUTER_OUTRO, COMPANY, LOCALE);
	assertEquals(COMPUTER_OUTRO_ISO, c.getOutro());
}

@Test
public void getIntroNull() {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, null, COMPUTER_OUTRO, COMPANY, LOCALE);
	assertNull(c.getIntro());
}

@Test
public void getOutroNull() {
	ComputerDto c = new ComputerDto(COMPUTER_ID, COMPUTER_NAME, COMPUTER_INTRO, null, COMPANY, LOCALE);
	assertNull(c.getOutro());
}

}
