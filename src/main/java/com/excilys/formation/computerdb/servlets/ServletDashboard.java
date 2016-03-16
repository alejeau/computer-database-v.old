package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.exceptions.ComputerCreationException;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.ComputerPager;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ServletDashboard extends HttpServlet {
	private static final long serialVersionUID = -2466894131493728982L;
	ComputerDatabaseServiceImpl services = null;

	protected ComputerPager pager;
	protected List<Computer> list = null;

	public ServletDashboard() {
		try {
			this.pager = new ComputerPager(10);
		} catch (ComputerCreationException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request);

		setRequestAndResponse(request, response);
		this.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
	}

	private void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestPath(request);

		request.setAttribute("nbComputers", this.pager.getNbEntries());
		request.setAttribute("currentPageNumber", this.pager.getCurrentPageNumber());
		request.setAttribute("maxPageNumber", this.pager.getMaxPageNumber());
		request.setAttribute("computers", this.services.computersToComputersDTO(this.pager.getCurrentPage()));
		request.setAttribute("pathSource", "");
		request.setAttribute("currentPath", Paths.PATH_DASHBOARD);

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
		String reset = null;
		reset = request.getParameter("reset");
		if (reset != null) {
			if (reset.equals("true")) {
				try {
					this.pager.goToPageNumber(0);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		String move = null;
		move = request.getParameter("page");
		if (move != null) {
			if (move.equals(Paths.PREVIOUS_PAGE)) {
				try {
					this.pager.getPreviousPage();
				} catch (ComputerCreationException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			} else if (move.equals(Paths.NEXT_PAGE)) {
				try {
					this.pager.getNextPage();
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
				this.pager.goToPageNumber(nb);
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
				this.pager.setObjectsPerPages(db);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
