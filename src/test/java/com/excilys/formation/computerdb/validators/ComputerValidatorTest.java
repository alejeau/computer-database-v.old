package com.excilys.formation.computerdb.validators;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.computerdb.errors.Problem;

public class ComputerValidatorTest {

	@Autowired
	ComputerValidator cval;

	@Test
	public void validateName() {
		Problem pb = cval.validateName("");
		//		System.out.println(pb);
		assertNotNull(pb);
	}

	@Test
	public void validateDate1(){
		Problem pb = null;
		pb = cval.validateDate("");
		//		System.out.println(pb);
		assertNull(pb);
	}

	@Test
	public void validateDate2(){
		Problem pb = null;
		pb = cval.validateDate(null);
		//		System.out.println(pb);
		assertNull(pb);
	}

	@Test
	public void validateDates1(){
		List<Problem> pb = null;
		pb = cval.validateDates("", "");
		//		System.out.println(pb);
		assertNull(pb);
	}

	@Test
	public void validateDates2(){
		List<Problem> pb = null;
		pb = cval.validateDates(null, "");
		//		System.out.println(pb);
		assertNull(pb);
	}

	@Test
	public void validateDates3(){
		List<Problem> pb = null;
		pb = cval.validateDates("", null);
		//		System.out.println(pb);
		assertNull(pb);
	}

	@Test
	public void validateDates4(){
		List<Problem> pb = null;
		pb = cval.validateDates(null, null);
		//		System.out.println(pb);
		assertNull(pb);
	}

	@Test
	public void validateComputer1() {
		List<Problem> pb = null;
		pb = cval.validateComputer("", null, null);
		//		for (Problem p : pb)
		//			System.out.println(p);
		assertNotNull(pb);
	}

	@Test
	public void validateComputer2() {
		List<Problem> pb = null;
		pb = cval.validateComputer("", "", null);
		//		for (Problem p : pb)
		//			System.out.println(p);
		assertNotNull(pb);
	}

	@Test
	public void validateComputer3() {
		List<Problem> pb = null;
		pb = cval.validateComputer("", null, "");
		//		for (Problem p : pb)
		//			System.out.println(p);
		assertNotNull(pb);
	}

}
