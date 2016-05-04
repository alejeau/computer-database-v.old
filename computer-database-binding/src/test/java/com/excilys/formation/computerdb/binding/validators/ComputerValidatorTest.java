package com.excilys.formation.computerdb.binding.validators;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.validators.ComputerValidator;

public class ComputerValidatorTest {
	
	@Test
	public void validateName() {
		Problem pb = ComputerValidator.validateName("");
		assertNotNull(pb);
	}

	@Test
	public void validateDate1(){
		Problem pb = null;
		pb = ComputerValidator.validateDate("");
		assertNull(pb);
	}

	@Test
	public void validateDate2(){
		Problem pb = null;
		pb = ComputerValidator.validateDate(null);
		assertNull(pb);
	}

	@Test
	public void validateDates1(){
		List<Problem> pb = null;
		pb = ComputerValidator.validateDates("", "");
		assertNull(pb);
	}

	@Test
	public void validateDates2(){
		List<Problem> pb = null;
		pb = ComputerValidator.validateDates(null, "");
		assertNull(pb);
	}

	@Test
	public void validateDates3(){
		List<Problem> pb = null;
		pb = ComputerValidator.validateDates("", null);
		assertNull(pb);
	}

	@Test
	public void validateDates4(){
		List<Problem> pb = null;
		pb = ComputerValidator.validateDates(null, null);
		assertNull(pb);
	}

	@Test
	public void validateComputer1() {
		List<Problem> pb = null;
		pb = ComputerValidator.validateComputer("", null, null);
		assertNotNull(pb);
	}

	@Test
	public void validateComputer2() {
		List<Problem> pb = null;
		pb = ComputerValidator.validateComputer("", "", null);
		assertNotNull(pb);
	}

	@Test
	public void validateComputer3() {
		List<Problem> pb = null;
		pb = ComputerValidator.validateComputer("", null, "");
		assertNotNull(pb);
	}

}
