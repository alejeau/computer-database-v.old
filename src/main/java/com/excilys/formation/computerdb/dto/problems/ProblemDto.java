package com.excilys.formation.computerdb.dto.problems;

import java.util.HashMap;
import java.util.List;

import com.excilys.formation.computerdb.errors.Problem;

public class ProblemDto {

	/**
	 * Converts a Problem to a HashMap<String, String> with a single entry.
	 * @param p the Problem to convert
	 * @return a HashMap with Problem.field as key and Problem.message as entry
	 */
	public static HashMap<String, String> toHashMap(Problem p) {
		if (p == null) {
			return null;
		}

		HashMap<String, String> h = new HashMap<>();
		h.put(p.getField(), p.getMessage());
		return h;
	}

	/**
	 * Converts a List<Problem> to a HashMap<String, String>.
	 * @param l the List<Problem> to convert
	 * @return a HashMap with Problem.field as key and Problem.message as entry
	 */
	public static HashMap<String, String> toHashMap(List<Problem> l) {
		if (l == null) {
			return null;
		}

		HashMap<String, String> h = new HashMap<>();
		for (Problem p : l) {
			h.put(p.getField(), p.getMessage());
		}

		return h;
	}
}
