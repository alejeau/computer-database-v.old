package com.excilys.formation.computerdb.mapper.date;

public class DateMapper {

	/**
	 * Converts a non-ISO date to an ISO date given a locale.
	 * 
	 * @param date a date to convert
	 * @param locale the date's locale
	 * @return an ISO date
	 */
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

	/**
	 * Converts an ISO date to a non-ISO date given a locale.
	 * 
	 * @param date an ISO date
	 * @param locale the date's locale
	 * @return an localized date
	 */
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
