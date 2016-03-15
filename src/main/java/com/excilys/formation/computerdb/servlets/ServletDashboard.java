package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.pagination.ComputerPager;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ServletDashboard extends HttpServlet {
	private static final long serialVersionUID = -2466894131493728982L;
	ComputerDatabaseServiceImpl services = null;
	
	protected ComputerPager pager;
	
	public ServletDashboard() {
		this.pager = new ComputerPager(15);
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Sending the paths to the project files
		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_ADD_COMPUTER);
		request.setAttribute("pathEditComputer", Paths.PATH_EDIT_COMPUTER);
		
		request.setAttribute("nbComputers", this.services.getNbComputers());
		request.setAttribute("currentPageNumber", this.pager.getCurrentPageNumber());
		request.setAttribute("computers", this.services.computersToComputersDTO(this.pager.getCurrentPage()));
		
		this.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
	}

}
