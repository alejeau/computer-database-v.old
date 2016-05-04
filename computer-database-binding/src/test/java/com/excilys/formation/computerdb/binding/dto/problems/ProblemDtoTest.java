package com.excilys.formation.computerdb.binding.dto.problems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.excilys.formation.computerdb.dto.problems.ProblemDto;
import com.excilys.formation.computerdb.errors.Problem;

public class ProblemDtoTest {

	@Test
	public void toHasMapNullProblem() {
		Problem p = null;
		HashMap<String, String> h = ProblemDto.toHashMap(p);
		assertNull(h);
	}

	@Test
	public void toHasMapNullProblems() {
		List<Problem> list = null;
		HashMap<String, String> h = ProblemDto.toHashMap(list);
		assertNull(h);
	}
	
	@Test
	public void toHasMapProblem() {
		final String FIELD = "Field";
		final String MESSAGE = "Message";
		Problem p = new Problem(FIELD, MESSAGE);
		HashMap<String, String> h = ProblemDto.toHashMap(p);
		assertNotNull(h);
		assertEquals(MESSAGE, h.get(FIELD));
	}
	
	@Test
	public void toHasMapProblems() {
		final String FIELD1 = "Field1";
		final String MESSAGE1 = "Message1";
		final String FIELD2 = "Field2";
		final String MESSAGE2 = "Message2";
		Problem p1 = new Problem(FIELD1, MESSAGE1);
		Problem p2 = new Problem(FIELD2, MESSAGE2);
		List<Problem> list = new LinkedList<>();
		list.add(p1);
		list.add(p2);
		HashMap<String, String> h = ProblemDto.toHashMap(list);
		assertNotNull(h);
		assertEquals(MESSAGE1, h.get(FIELD1));
		assertEquals(MESSAGE2, h.get(FIELD2));
	}

}
