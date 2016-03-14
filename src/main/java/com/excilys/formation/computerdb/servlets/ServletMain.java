package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.pagination.ComputerPager;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ServletMain extends HttpServlet {
	private static final long serialVersionUID = -2466894131493728982L;
	ComputerDatabaseServiceImpl services = null;
	protected static final String VIEW = "/WEB-INF/static/views/dashboard.jsp";
	
	protected ComputerPager pager;
	
	public ServletMain() {
		this.pager = new ComputerPager(10);
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("nbComputers", this.services.getNbComputers());
		request.setAttribute("currentPageNumber", this.pager.getCurrentPageNumber());
		request.setAttribute("computers", this.pager.getCurrentPage());
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

}
