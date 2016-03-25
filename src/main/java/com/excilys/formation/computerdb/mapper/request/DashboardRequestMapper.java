package com.excilys.formation.computerdb.mapper.request;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.SortedPage;
import com.excilys.formation.computerdb.persistence.Fields;
import com.excilys.formation.computerdb.servlets.Paths;
import com.excilys.formation.computerdb.servlets.request.PageRequest;

public class DashboardRequestMapper {
	
	public static PageRequest<Computer> mapDoGet(HttpServletRequest request) {
		String url = Paths.PATH_DASHBOARD;
		SortedPage<Computer> page = new SortedPage<>();
		
		String stringField = request.getParameter(UrlFields.URL_FIELD);
		String pageNb = request.getParameter(UrlFields.URL_PAGE_NB);
		String displayBy = request.getParameter(UrlFields.URL_DISPLAY_BY);
		String ascending = request.getParameter(UrlFields.URL_ASCENDING);
		
		// Retrieving and setting the sort field
		if (notEmpty(stringField)) {
			Fields field = Fields.NAME;
			if (stringField.equals(Fields.INTRO_DATE.toString())) {
				field = Fields.INTRO_DATE;
			} else if (stringField.equals(Fields.OUTRO_DATE.toString())) {
				field = Fields.OUTRO_DATE;
			} else if (stringField.equals(Fields.COMPANY.toString())) {
				field = Fields.COMPANY;
			}
			
			page.setField(field);
			url = setUrl(url, UrlFields.URL_FIELD, field.toString());
		} else {
			page.setField(Fields.NAME);
			url = setUrl(url, UrlFields.URL_FIELD, Fields.NAME.toString());
		}

		// Retrieving and setting the ascending field
		if (notEmpty(ascending)) {
			boolean ascend = true;
			if (ascending.equals("false")) {
				ascend = false;
			}
			
			page.setAscending(ascend);
			url = setUrl(url, UrlFields.URL_ASCENDING, String.valueOf(ascend));
		} else {
			page.setAscending(true);
			url = setUrl(url, UrlFields.URL_ASCENDING, "true");
		}

		// Retrieving and setting the display number
		if (notEmpty(displayBy)) {
			int db = 10;
			try {
				db = Integer.parseInt(displayBy);
				System.out.println("displayBy = " + displayBy);
			} catch (Exception e) {	}
			
			page.setObjectsPerPages(db);
			url = setUrl(url, UrlFields.URL_DISPLAY_BY, String.valueOf(db));
		} else {
			page.setObjectsPerPages(10);
			url = setUrl(url, UrlFields.URL_DISPLAY_BY, String.valueOf(10));
		}

		// Retrieving and setting the page number
		if (notEmpty(pageNb)) {
			int nb = 0;
			try {
				nb = Integer.parseInt(pageNb);
			} catch (Exception e) {	}
			page.setCurrentPageNumber(nb);
			url = setUrl(url, UrlFields.URL_PAGE_NB, String.valueOf(nb));
		} else {
			page.setCurrentPageNumber(0);
			url = setUrl(url, UrlFields.URL_PAGE_NB, String.valueOf(0));
		}

		return new PageRequest<>(page, url);
	}
	
	/**
	 * Adds var=value to the url, with the correct ? or &
	 * 
	 * @param url the current url
	 * @param var the name of the var
	 * @param value the value of the var
	 * @return url + ?|& + var=value
	 */
	private static String setUrl(String url, String var, String value) {
		if (url.contains("?")) {
			url += "&";
		} else {
			url += "?";
		}
		url += var + "=" + value;
		return url;
	}
	
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
	
	protected static boolean notEmpty(String s) {
		if (s != null && !s.equals("") && !s.equals("null")) {
			return true;
		}
		return false;
	}

}
