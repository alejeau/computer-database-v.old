package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.exceptions.ComputerCreationException;
import com.excilys.formation.computerdb.exceptions.PagerSearchException;
import com.excilys.formation.computerdb.mapper.ComputerDTOMapper;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.ComputerSearchPager;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ServletSearch extends HttpServlet {
	private static final long serialVersionUID = -2466894131493728982L;
	ComputerDatabaseServiceImpl services = null;

	protected ComputerSearchPager cspager;
	protected String search = null;
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

	private void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestPath(request);
		request.setAttribute("nbComputers", this.cspager.getNbEntries());
		request.setAttribute("currentPageNumber", this.cspager.getCurrentPageNumber());
		request.setAttribute("computers", ComputerDTOMapper.toDTO(this.cspager.getCurrentPage()));
		request.setAttribute("maxPageNumber", this.cspager.getMaxPageNumber());
		request.setAttribute("pathSource", "../");
		request.setAttribute("currentPath", Paths.PATH_COMPUTER_SEARCH);
	}

	private void setRequestPath(HttpServletRequest request) {
		// Sending the paths to the project files
		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		request.setAttribute("pathSearchComputer", Paths.PATH_COMPUTER_SEARCH);
		request.setAttribute("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);
	}

	private void process(HttpServletRequest request) {
		search = request.getParameter("search");
		if ((search != null) && !search.equals("")) {
			try {
				try {
					this.cspager.setSearch(search);
				} catch (ComputerCreationException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				try {
					this.cspager.goToPageNumber(0);
				} catch (ComputerCreationException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			} catch (PagerSearchException e) {
				System.out.println(e.getMessage());
			}
		}

		String move = null;
		move = request.getParameter("page");
		if (move != null) {
			if (move.equals(Paths.PREVIOUS_PAGE)) {
				try {
					this.cspager.getPreviousPage();
				} catch (ComputerCreationException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			} else if (move.equals(Paths.NEXT_PAGE)) {
				try {
					this.cspager.getNextPage();
				} catch (ComputerCreationException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		String pageNb = null;
		pageNb = request.getParameter("pageNb");
		if (pageNb != null) {
			int nb = Integer.parseInt(pageNb);
			try {
				this.cspager.goToPageNumber(nb);
			} catch (ComputerCreationException e) {
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
			} catch (ComputerCreationException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
