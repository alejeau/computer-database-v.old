package com.excilys.formation.computerdb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.pagination.ComputerPager;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ServletSearch extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6504697528969458049L;
	ComputerDatabaseServiceImpl services = null;

	protected ComputerPager pager;

	public ServletSearch() {
		this.pager = new ComputerPager(15);
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestAndResponse(request, response);

		this.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
	}

	private void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Sending the paths to the project files
		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);

		request.setAttribute("nbComputers", this.services.getNbComputers());
		request.setAttribute("currentPageNumber", this.pager.getCurrentPageNumber());
		request.setAttribute("computers", this.services.computersToComputersDTO(this.pager.getCurrentPage()));
	}
}
