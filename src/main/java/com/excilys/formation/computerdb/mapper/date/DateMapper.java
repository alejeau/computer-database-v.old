package com.excilys.formation.computerdb.mapper.date;

public class DateMapper {

	public static String mapDate(String date, String locale) {
		String tmp = null;
		if (date != null && !date.equals("")) {
			String[] datum = date.split("-");
			if (locale.equals("en")) {
				tmp = datum[2] + "-" + datum[0] + "-" + datum[1];
			} else {
				tmp = datum[2] + "-" + datum[1] + "-" + datum[0];
			}
		}
		
		return tmp;
	}
	
	public static String unmapDate(String date, String locale) {
		String tmp = null;
		if (date != null && !date.equals("")) {
			String[] datum = date.split("-");
			if (locale.equals("en")) {
				tmp = datum[1] + "-" + datum[2] + "-" + datum[0];
			} else {
				tmp = datum[2] + "-" + datum[1] + "-" + datum[0];
			}
		}
		
		return tmp;
	}

}
