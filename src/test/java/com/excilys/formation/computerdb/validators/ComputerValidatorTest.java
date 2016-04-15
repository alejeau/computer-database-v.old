package com.excilys.formation.computerdb.validators;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import org.junit.Test;

import com.excilys.formation.computerdb.errors.Problem;

public class ComputerValidatorTest {
	
	@Test
	public void validateName() {
		Problem pb = ComputerValidator.validateName("");
		//		System.out.println(pb);
		assertNotNull(pb);
	}

	@Test
	public void validateDate1(){
		Problem pb = null;
		pb = ComputerValidator.validateDate("");
		//		System.out.println(pb);
		assertNull(pb);
	}

	@Test
	public void validateDate2(){
		Problem pb = null;
		pb = ComputerValidator.validateDate(null);
		//		System.out.println(pb);
		assertNull(pb);
	}

	@Test
	public void validateDates1(){
		List<Problem> pb = null;
		pb = ComputerValidator.validateDates("", "");
		//		System.out.println(pb);
		assertNull(pb);
	}

	@Test
	public void validateDates2(){
		List<Problem> pb = null;
		pb = ComputerValidator.validateDates(null, "");
		//		System.out.println(pb);
		assertNull(pb);
	}

	@Test
	public void validateDates3(){
		List<Problem> pb = null;
		pb = ComputerValidator.validateDates("", null);
		//		System.out.println(pb);
		assertNull(pb);
	}

	@Test
	public void validateDates4(){
		List<Problem> pb = null;
		pb = ComputerValidator.validateDates(null, null);
		//		System.out.println(pb);
		assertNull(pb);
	}

	@Test
	public void validateComputer1() {
		List<Problem> pb = null;
		pb = ComputerValidator.validateComputer("", null, null);
		//		for (Problem p : pb)
		//			System.out.println(p);
		assertNotNull(pb);
	}

	@Test
	public void validateComputer2() {
		List<Problem> pb = null;
		pb = ComputerValidator.validateComputer("", "", null);
		//		for (Problem p : pb)
		//			System.out.println(p);
		assertNotNull(pb);
	}

	@Test
	public void validateComputer3() {
		List<Problem> pb = null;
		pb = ComputerValidator.validateComputer("", null, "");
		//		for (Problem p : pb)
		//			System.out.println(p);
		assertNotNull(pb);
	}

}
