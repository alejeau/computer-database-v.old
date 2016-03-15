package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.exceptions.PagerSearchException;
import com.excilys.formation.computerdb.pagination.ComputerPager;
import com.excilys.formation.computerdb.pagination.ComputerSearchPager;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ServletDashboard extends HttpServlet {
	private static final long serialVersionUID = -2466894131493728982L;
	ComputerDatabaseServiceImpl services = null;

	protected ComputerPager pager;
	protected ComputerSearchPager cspager;
	protected boolean searchMode = false;
	protected String search = null;

	public ServletDashboard() {
		this.pager = new ComputerPager(10);
		this.cspager = new ComputerSearchPager(10);
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search = request.getParameter("search");
		if ((search != null) && !search.equals("")){
			searchMode = true;
			try {
				this.cspager.setSearch(search);
			} catch (PagerSearchException e) {
				System.out.println(e.getMessage());
			}
		} else {
			if (searchMode) {
				searchMode = false;
			}
		}
		
		setRequestAndResponse(request, response);
		this.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
	}

	private void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestPath(request);
		if (searchMode) {
			request.setAttribute("nbComputers", this.cspager.getNbEntries());
			request.setAttribute("currentPageNumber", this.cspager.getCurrentPageNumber());
			request.setAttribute("computers", this.services.computersToComputersDTO(this.cspager.getCurrentPage()));
		} else {
			request.setAttribute("nbComputers", this.pager.getNbEntries());
			request.setAttribute("currentPageNumber", this.pager.getCurrentPageNumber());
			request.setAttribute("computers", this.services.computersToComputersDTO(this.pager.getCurrentPage()));
		}
	}

	private void setRequestPath(HttpServletRequest request){
		// Sending the paths to the project files
		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
	}



}
