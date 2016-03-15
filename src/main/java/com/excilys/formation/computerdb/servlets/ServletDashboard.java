package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.ComputerPager;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ServletDashboard extends HttpServlet {
	private static final long serialVersionUID = -2466894131493728982L;
	ComputerDatabaseServiceImpl services = null;

	protected ComputerPager pager;
	protected List<Computer> list = null;

	public ServletDashboard() {
		this.pager = new ComputerPager(10);
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
	}

	private void process(HttpServletRequest request) {
		String move = null;
		move = request.getParameter("page");
		if (move != null) {
			if (move.equals(Paths.PREVIOUS_PAGE)) {
				this.pager.getPreviousPage();
			} else if (move.equals(Paths.NEXT_PAGE)) {
				this.pager.getNextPage();
			} 
		}

		String pageNb = null;
		pageNb = request.getParameter("pageNb");
		if (pageNb != null) {
			int nb = Integer.parseInt(pageNb);
			this.pager.goToPageNumber(nb);
		}
	}
}
