package com.excilys.formation.computerdb.dto;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.computerdb.model.Computer;

public class ComputerDTOTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void ComputerDTO() {
		String nil = null;
		Computer c = new Computer(0, "n", nil, nil, null);
		ComputerDto cdto = new ComputerDto(c);
		assertNotNull(cdto);
	}

	@Test
	public void ComputerDTOCompany() {
		String nil = null;
		Computer c = new Computer(0, "n", nil, nil, null);
		ComputerDto cdto = new ComputerDto(c);
		assertNotNull(cdto);
	}

}
