package com.excilys.formation.computerdb.mapper.request;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formation.computerdb.constants.Fields;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.SearchPage;
import com.excilys.formation.computerdb.servlets.Paths;
import com.excilys.formation.computerdb.servlets.request.ComputerSearchPageRequest;

public class SearchRequestMapper {
	
	public static ComputerSearchPageRequest mapDoGet(HttpServletRequest request) {
		String url = Paths.PATH_COMPUTER_SEARCH;
		SearchPage<Computer> page = new SearchPage<>();

		String pageNb = request.getParameter(UrlFields.URL_PAGE_NB);
		String displayBy = request.getParameter(UrlFields.URL_DISPLAY_BY);
		String stringField = request.getParameter(UrlFields.URL_FIELD);
		String ascending = request.getParameter(UrlFields.URL_ASCENDING);
		String searchField = request.getParameter(UrlFields.URL_SEARCH);

		// Retrieving and setting the page number
		int nb = 0;
		if (notEmpty(pageNb)) {
			try {
				nb = Integer.parseInt(pageNb);
			} catch (Exception e) {	}
		}
		page.setCurrentPageNumber(nb);
		url = setUrl(url, UrlFields.URL_PAGE_NB, String.valueOf(nb));

		// Retrieving and setting the display number
		int db = 10;
		if (notEmpty(displayBy)) {
			try {
				db = Integer.parseInt(displayBy);
			} catch (Exception e) {	}
		}
		page.setObjectsPerPages(db);
		url = setUrl(url, UrlFields.URL_DISPLAY_BY, String.valueOf(db));
		
		// Retrieving and setting the sort field
		Fields field = Fields.NAME;
		if (notEmpty(stringField)) {
			if (stringField.equals(Fields.INTRO_DATE.toString())) {
				field = Fields.INTRO_DATE;
			} else if (stringField.equals(Fields.OUTRO_DATE.toString())) {
				field = Fields.OUTRO_DATE;
			} else if (stringField.equals(Fields.COMPANY.toString())) {
				field = Fields.COMPANY;
			}
		}
		page.setField(field);
		url = setUrl(url, UrlFields.URL_FIELD, field.toString());

		// Retrieving and setting the ascending field
		boolean ascend = true;
		if (notEmpty(ascending)) {
			if (ascending.equals("false")) {
				ascend = false;
			}
		}
		page.setAscending(ascend);
		url = setUrl(url, UrlFields.URL_ASCENDING, String.valueOf(ascend));
		
		// Retrieving and setting the research field
		String search = null;
		if ((searchField != null) && !searchField.equals("")) {
			search = searchField;
		}
		page.setSearch(search);
		url = setUrl(url, UrlFields.URL_SEARCH, search);

		return new ComputerSearchPageRequest(page, url);
	}
	
	/**
	 * Adds var=value to the url, with the correct ? or &
	 * 
	 * @param url the current url
	 * @param var the name of the var
	 * @param value the value of the var
	 * @return url + ?|& + var=value
	 */
	protected static String setUrl(String url, String var, String value) {
		if (url.contains("?")) {
			url += "&";
		} else {
			url += "?";
		}
		url += var + "=" + value;
		return url;
	}
	
	/**
	 * Returns the list of computer IDs to delete
	 * @param request the request to process
	 * @return the list of computer IDs to delete
	 */
	public static long[] mapDoPost(HttpServletRequest request) {
		String del = request.getParameter("selection");
		long[] listId = null;
		if (!del.equals("")) {
			String[] list = del.split(",");
			int len = list.length;
			listId = new long[len];
			for (int i = 0; i < len; i++) {
				listId[i] = Long.valueOf(list[i]);
			}
		}
		return listId;
	}
	
	/**
	 * Checks if a String is not null, different than "" and different than "null".
	 * @param s the String to check
	 * @return true if the String is not null, different than "" and different than "null"
	 */
	protected static boolean notEmpty(String s) {
		if (s != null && !s.equals("") && !s.equals("null")) {
			return true;
		}
		return false;
	}
}
