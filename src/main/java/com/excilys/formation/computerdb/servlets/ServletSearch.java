package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.exceptions.PagerSearchException;
import com.excilys.formation.computerdb.mapper.ComputerDTOMapper;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.ComputerSearchPager;
import com.excilys.formation.computerdb.persistence.Fields;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ServletSearch extends HttpServlet {
	private static final long serialVersionUID = -2466894131493728982L;
	ComputerDatabaseServiceImpl services = null;

	protected ComputerSearchPager cspager;
	protected String url = null;
	protected String search = null;
	protected Fields field = Fields.NAME;
	protected boolean ascending = true;
	protected List<Computer> list = null;

	public ServletSearch() {
		this.cspager = new ComputerSearchPager(10);
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request);

		setRequestAndResponse(request, response);
		this.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		delete(request);
	}

	private void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestPath(request);
		request.setAttribute("nbComputers", this.cspager.getNbEntries());
		request.setAttribute("currentPageNumber", this.cspager.getCurrentPageNumber());
		request.setAttribute("computers", ComputerDTOMapper.toDTO(this.cspager.getCurrentPage()));
		request.setAttribute("maxPageNumber", this.cspager.getMaxPageNumber());
		request.setAttribute("pathSource", "../");
		request.setAttribute("currentUrl", this.url);
		request.setAttribute("currentPath", Paths.PATH_COMPUTER_SEARCH);
	}

	private void setRequestPath(HttpServletRequest request) {
		// Sending the paths to the project files
		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		request.setAttribute("pathSearchComputer", Paths.PATH_COMPUTER_SEARCH);
		request.setAttribute("pathComputerDelete", Paths.PATH_COMPUTER_DELETE);
		request.setAttribute("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);
	}

	private void process(HttpServletRequest request) {
		this.url = Paths.PATH_COMPUTER_SEARCH;
		search = request.getParameter("search");
		if ((search != null) && !search.equals("")) {
			try {
				this.cspager.setSearch(search);
				if (url.contains("?")) {
					url += "&";
				} else {
					url += "?";
				}
				url += "search=" + search;
			} catch (PagerSearchException e) {
				System.out.println(e.getMessage());
			}
		}

		String stringField = request.getParameter("field");
		if ((stringField != null) && !stringField.equals("") && !stringField.equals("null")) {
			Fields tmp = this.field;
			if (stringField.equals(Fields.NAME.toString())) {
				this.field = Fields.NAME;
			} else if (stringField.equals(Fields.INTRO_DATE.toString())) {
				this.field = Fields.INTRO_DATE;
			} else if (stringField.equals(Fields.OUTRO_DATE.toString())) {
				this.field = Fields.OUTRO_DATE;
			} else if (stringField.equals(Fields.COMPANY.toString())) {
				this.field = Fields.NAME;
			}

			if (tmp.toString().equals(field.toString())) {
				ascending = !ascending;
				this.cspager.setOrder(ascending);
			} else {
				this.cspager.setField(this.field);
				ascending = true;
				this.cspager.setOrder(ascending);
			}
		}

		String move = null;
		move = request.getParameter("page");
		if (move != null) {
			if (move.equals(Paths.PREVIOUS_PAGE)) {
				this.cspager.getPreviousPage();
				if (url.contains("?")) {
					url += "&";
				} else {
					url += "?";
				}
				url += "page=prev";
			} else if (move.equals(Paths.NEXT_PAGE)) {
				this.cspager.getNextPage();
				if (url.contains("?")) {
					url += "&";
				} else {
					url += "?";
				}
				url += "page=next";
			}
		}

		String pageNb = null;
		pageNb = request.getParameter("pageNb");
		if (pageNb != null) {
			int nb = Integer.parseInt(pageNb);
			try {
				this.cspager.goToPageNumber(nb);
				if (url.contains("?")) {
					url += "&";
				} else {
					url += "?";
				}
				url += "pageNb=" + this.cspager.getCurrentPageNumber();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}

		String displayBy = null;
		displayBy = request.getParameter("displayBy");
		if (displayBy != null) {
			int db = Integer.parseInt(displayBy);
			try {
				this.cspager.setObjectsPerPages(db);
				if (url.contains("?")) {
					url += "&";
				} else {
					url += "?";
				}
				url += "displayBy=" + this.cspager.getObjectsPerPages();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void delete(HttpServletRequest request) {
		String del = request.getParameter("selection");
		if (!del.equals("")) {
			String[] list = del.split(",");
			int len = list.length;
			long[] listId = new long[len];
			for (int i = 0; i < len; i++) {
				listId[i] = Long.valueOf(list[i]);
			}
			ComputerDatabaseServiceImpl.INSTANCE.deleteComputers(listId);
			this.cspager.update();
		}
	}
}
