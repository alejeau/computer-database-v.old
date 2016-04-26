package com.excilys.formation.computerdb.validators;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.model.Computer;

public class ComputerValidator {
	private static final String FIELD_NAME = "name";
	private static final String FIELD_DATES = "dates";
	private static final String FIELD_DATE_INTRO = "intro";
	private static final String FIELD_DATE_OUTRO = "outro";

	public static Problem validateName(String name) {
		String field = FIELD_NAME;
		Problem pb = null;
		if (name == null) {
			pb = new Problem(field, "can't be null!");
		} else if (name.equals("")) {
			pb = new Problem(field, "can't be empty!");
		} else if (name.startsWith(" ")) {
			pb = new Problem(field, "can't start with space (' ') char !");
		}
		return pb;
	}

	public static Problem validateNewName(String newName, String oldName) {
		String field = FIELD_NAME;
		Problem pb = null;
		if (newName == null) {
			pb = new Problem(field, "can't be null!");
		} else if (newName.equals("")) {
			pb = new Problem(field, "can't be empty!");
		} else if (newName.startsWith(" ")) {
			pb = new Problem(field, "can't start with space (' ') char !");
		}
		return pb;
	}

	private static boolean canBeCompared(String date) {
		if ((date != null) && (!date.equals(""))) {
			return true;
		}
		return false;
	}

	public static Problem validateDate(String date) {
		String field = FIELD_DATES;
		Problem pb = null;
		if (date != null) {
			if (!date.equals("")) {
				// If the date ain't correctly formatted
				try {
					LocalDate.parse(date);
				} catch (DateTimeParseException e) {
					pb = new Problem(field, "must follow the template \"yyyy-MM-dd\"!");
				}
			}
		}

		return pb;
	}

	public static List<Problem> validateDates(String intro, String outro) {
		List<Problem> list = null;
		Problem pb1 = null, pb2 = null;
		String field = FIELD_DATES;

		pb1 = validateDate(intro);
		pb2 = validateDate(outro);

		if (pb1 != null) { // if the first date wasn't correctly formatted
			pb1.setField(FIELD_DATE_INTRO);
			list = addToList(list, pb1);
		}
		if (pb2 != null) { // else if the second date wasn't correctly formatted
			pb2.setField(FIELD_DATE_OUTRO);
			list = addToList(list, pb2);
		}
		if ((pb1 == null) && (pb2 == null) && (intro != null) && (outro != null) && canBeCompared(intro)
				&& canBeCompared(outro)) {
			if (intro.compareTo(outro) >= 0) { // else if the intro date isn't
												// inferior to the outro date
				list = addToList(list, new Problem(field, "Intro date must be inferior to outro date!"));
			}
		}

		return list;
	}

	/**
	 * Checks whether data parameters are valid or not
	 * 
	 * @param name name of the computer
	 * @param intro date of introduction of the computer
	 * @param outro date of end of life of the computer
	 * @return null if everything went well, a list of Problem else
	 */
	public static List<Problem> validateComputer(Computer c) {
		List<Problem> listErrors = null;
		String i = null, o = null;

		if (c.getIntro() != null) {
			i = c.getIntro().toString();
		}

		if (c.getOutro() != null) {
			o = c.getOutro().toString();
		}

		listErrors = addToList(listErrors, validateName(c.getName()));
		listErrors = addToList(listErrors, validateDates(i, o));

		return listErrors;
	}

	/**
	 * Checks whether data parameters are valid or not
	 * 
	 * @param name name of the computer
	 * @param intro date of introduction of the computer
	 * @param outro date of end of life of the computer
	 * @return null if everything went well, a list of Problem else
	 */
	public static List<Problem> validateComputer(String name, String intro, String outro) {
		List<Problem> listErrors = null;

		listErrors = addToList(listErrors, validateName(name));

		listErrors = addToList(listErrors, validateDates(intro, outro));

		return listErrors;
	}

	/**
	 * Checks whether data parameters are valid or not
	 * 
	 * @param newName new name of the computer
	 * @param oldName old name of the computer
	 * @param intro date of introduction of the computer
	 * @param outro date of end of life of the computer
	 * @return null if everything went well, a list of Problem else
	 */
	public static List<Problem> validateNewComputer(Computer c, String oldName) {
		List<Problem> listErrors = null;
		String i = null, o = null;

		if (c.getIntro() != null) {
			i = c.getIntro().toString();
		}

		if (c.getOutro() != null) {
			o = c.getOutro().toString();
		}

		listErrors = addToList(listErrors, validateNewName(c.getName(), oldName));

		listErrors = addToList(listErrors, validateDates(i, o));

		return listErrors;
	}

	/**
	 * Checks whether data parameters are valid or not
	 * 
	 * @param newName new name of the computer
	 * @param oldName old name of the computer
	 * @param intro date of introduction of the computer
	 * @param outro date of end of life of the computer
	 * @return null if everything went well, a list of Problem else
	 */
	public static List<Problem> validateNewComputer(String newName, String oldName, String intro, String outro) {
		List<Problem> listErrors = null;

		listErrors = addToList(listErrors, validateNewName(newName, oldName));

		listErrors = addToList(listErrors, validateDates(intro, outro));

		return listErrors;
	}

	protected static List<Problem> addToList(List<Problem> list, Problem p) {
		if (p != null) {
			if (list == null) {
				list = new ArrayList<Problem>();
			}

			list.add(p);
		}

		return list;
	}

	protected static List<Problem> addToList(List<Problem> list, List<Problem> listToAdd) {
		if (listToAdd != null) {
			if (list == null) {
				list = new ArrayList<Problem>();
			}

			list.addAll(listToAdd);
		}

		return list;
	}
}
