package com.excilys.formation.computerdb.validators;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.computerdb.errors.Problem;

public class ComputerValidatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void validateName() {
		Problem pb = ComputerValidator.validateName("");
//		System.out.println(pb);
		assertNotNull(pb);
	}
	
	@Test
	public void validateComputer1() {
		List<Problem> pb = null;
		pb = ComputerValidator.validateComputer("", null, null);
		for (Problem p : pb)
			System.out.println(p);
		assertNotNull(pb);
	}
	
	@Test
	public void validateComputer2() {
		List<Problem> pb = null;
		pb = ComputerValidator.validateComputer("", "", null);
		for (Problem p : pb)
			System.out.println(p);
		assertNotNull(pb);
	}
	
	@Test
	public void validateComputer3() {
		List<Problem> pb = null;
		pb = ComputerValidator.validateComputer("", null, "");
		for (Problem p : pb)
			System.out.println(p);
		assertNotNull(pb);
	}

}
