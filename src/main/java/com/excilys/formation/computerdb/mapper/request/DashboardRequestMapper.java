package com.excilys.formation.computerdb.mapper.request;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formation.computerdb.mapper.model.PageDtoMapper;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.SortedPage;
import com.excilys.formation.computerdb.persistence.Fields;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.servlets.Paths;

public class DashboardRequestMapper {
	
	public static HttpServletRequest map(HttpServletRequest request) {
		String url = Paths.PATH_DASHBOARD;
		SortedPage<Computer> page = new SortedPage<>();
		
		String stringField = request.getParameter(UrlFields.URL_FIELD);
		String pageNb = request.getParameter(UrlFields.URL_PAGE_NB);
		String displayBy = request.getParameter(UrlFields.URL_DISPLAY_BY);
		String ascending = request.getParameter(UrlFields.URL_ASCENDING);

		if ((stringField != null) && !stringField.equals("") && !stringField.equals("null")) {
			Fields field;
			if (stringField.equals(Fields.NAME.toString())) {
				field = Fields.NAME;
			} else if (stringField.equals(Fields.INTRO_DATE.toString())) {
				field = Fields.INTRO_DATE;
			} else if (stringField.equals(Fields.OUTRO_DATE.toString())) {
				field = Fields.OUTRO_DATE;
			} else if (stringField.equals(Fields.COMPANY.toString())) {
				field = Fields.COMPANY;
			} else {
				field = Fields.NAME;
			}
			
			page.setField(field);
			url = setUrl(url, UrlFields.URL_FIELD, field.toString());
		}
		
		if (ascending != null) {
			boolean ascend = true;
			if (ascending.equals("false")) {
				ascend = false;
			}
			page.setAscending(false);
			url = setUrl(url, UrlFields.URL_ASCENDING, "false");
		}

		if (displayBy != null) {
			int db = 10;
			try {
				db = Integer.parseInt(displayBy);
			} catch (Exception e) {	}
			page.setObjectsPerPages(db);
			url = setUrl(url, UrlFields.URL_DISPLAY_BY, String.valueOf(db));
		}
		
		if (pageNb != null) {
			int nb = 0;
			try {
				nb = Integer.parseInt(pageNb);
			} catch (Exception e) {	}
			page = ComputerDatabaseServiceImpl.INSTANCE.getComputerSortedPage(nb, page);
			url = setUrl(url, UrlFields.URL_PAGE_NB, String.valueOf(nb));
		}

		// Sending the paths to the project files
		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		request.setAttribute("pathSearchComputer", Paths.PATH_COMPUTER_SEARCH);
		request.setAttribute("pathComputerDelete", Paths.PATH_COMPUTER_DELETE);
		request.setAttribute("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

		
		request.setAttribute("page", PageDtoMapper.toDTO(pager.getCurrentPage()));
		request.setAttribute("pathSource", "");
		request.setAttribute("currentUrl", url);
		request.setAttribute("currentPath", Paths.PATH_DASHBOARD);
		
		return request;
	}
	
	private static String setUrl(String url, String var, String value) {
		if (url.contains("?")) {
			url += "&";
		} else {
			url += "?";
		}
		url += var + "=" + value;
		return url;
	}

}
