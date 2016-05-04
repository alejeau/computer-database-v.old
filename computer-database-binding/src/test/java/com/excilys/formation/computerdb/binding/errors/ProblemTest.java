package com.excilys.formation.computerdb.binding.errors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.computerdb.errors.Problem;

public class ProblemTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructor() {
		Problem p = new Problem("Field", "Message");
		assertNotNull(p);
	}

	@Test
	public void emptyConstructor() {
		Problem p = new Problem();
		assertNotNull(p);
	}

	@Test
	public void getFieldEmptyConstructor() {
		Problem p = new Problem();
		assertNull(p.getField());
	}

	@Test
	public void getMessageEmptyConstructor() {
		Problem p = new Problem();
		assertNull(p.getMessage());
	}

	@Test
	public void getField() {
		Problem p = new Problem("Field", "Message");
		assertEquals("Field", p.getField());
	}

	@Test
	public void getMessage() {
		Problem p = new Problem("Field", "Message");
		assertEquals("Message", p.getMessage());
	}

}
