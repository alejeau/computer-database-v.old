package com.excilys.formation.computerdb.validators;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ComputerValidator {
	private static final String FIELD_NAME = "Computer.name";
	private static final String FIELD_DATES = "Dates";
	private static final String FIELD_DATE_INTRO = "Date.intro";
	private static final String FIELD_DATE_OUTRO = "Date.outro";
	private static ComputerDatabaseServiceImpl services = ComputerDatabaseServiceImpl.INSTANCE; 

	public static Problem validateName(String name) {
		String field = FIELD_NAME;
		Problem pb = null;
		if (name == null) {
			pb = new Problem(field, "can't be null!");
		} else if (name.equals("")) {
			pb = new Problem(field, "can't be empty!");
		} else if (services.alreadyExists(name)) {
			pb = new Problem(field, "A computer with this name (\"" + name + "\") already exists!");
		}
		return pb;
	}

	public static Problem validateDate(String date) {
		String field = FIELD_DATES;
		Problem pb = null;
		if (date != null) {
			// If the date ain't correctly formatted
			try {
				LocalDate.parse(date);
			} catch (DateTimeParseException e){
				pb = new Problem(field, "must follow the template \"yyyy-MM-dd\"!");
			}
		}
		
		return pb;
	}

	public static Problem validateDates(String intro, String outro) {
		Problem pb = null;
		Problem pb1 = null, pb2 = null;
		String field = FIELD_DATES;

		pb1 = validateDate(intro);
		pb2 = validateDate(outro);
		
		if (pb1 != null) { // if the first date wasn't correctly formatted
			pb1.setField(FIELD_DATE_INTRO);
			return pb1;
		} else if (pb2 != null) { // else if the second date wasn't correctly formatted
			pb2.setField(FIELD_DATE_OUTRO);
			return pb2;
		} else if ((intro != null) && (outro != null)) { 
			if (intro.compareTo(outro) >= 0) { // else if the intro date isn't inferior to the outro date 
				return new Problem(field, "Intro date must be inferior to outro date!");
			}
		}
		
		return pb;
	}
	
	/**
	 * Checks whether data parameters are valid or not
	 * @param name name of the computer
	 * @param intro date of introduction of the computer
	 * @param outro date of end of life  of the computer
	 * @return null if everything went well, a list of Problem else
	 */
	public static List<Problem> validateComputer(String name, String intro, String outro){
		List<Problem> listErrors = null;
		Problem tmp = null;
		
		tmp = ComputerValidator.validateName(name);
		if (tmp != null) {
			listErrors = addToList(listErrors, tmp);
		}

		tmp = ComputerValidator.validateDates(intro, outro);
		if (tmp != null) {
			listErrors = addToList(listErrors, tmp);
		}
		
		return listErrors;
	}
	
	protected static List<Problem> addToList(List<Problem> list, Problem p){
		if (list == null) {
			list = new ArrayList<Problem>();
		}
		
		list.add(p);
		
		return list;
	}
}
