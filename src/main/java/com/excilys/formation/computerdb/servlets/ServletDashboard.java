package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
//import java.sql.Connection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.computerdb.mapper.model.PageDtoMapper;
import com.excilys.formation.computerdb.mapper.request.DashboardRequestMapper;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.SortedPage;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.servlets.request.ComputerSortedPageRequest;

@WebServlet(name = "ServletDashboard", urlPatterns = "/access")
public class ServletDashboard extends HttpServlet {
	private static final long serialVersionUID = -2466894131493728982L;

	@Autowired
	ComputerDatabaseServiceImpl services;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public ServletDashboard() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerSortedPageRequest page = DashboardRequestMapper.mapDoGet(request);

		// We feed content to the page
		SortedPage<Computer> sp = page.getComputerSortedPage();
		int currentPageNumber = sp.getCurrentPageNumber();
		sp = this.services.getComputerSortedPage(currentPageNumber, sp);
		page.setPage(sp);

		request = setRequest(request, page);
		request.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long[] listId = DashboardRequestMapper.mapDoPost(request);

		this.services.deleteComputers(listId);
	}

	protected static HttpServletRequest setRequest(HttpServletRequest request, ComputerSortedPageRequest page) {
		// Setting the paths
		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		request.setAttribute("pathSearchComputer", Paths.PATH_COMPUTER_SEARCH);
		request.setAttribute("pathComputerDelete", Paths.PATH_COMPUTER_DELETE);
		request.setAttribute("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

		// Setting the vars
		request.setAttribute("PAGE", PageDtoMapper.toSortedPageDto(page.getComputerSortedPage()));
		request.setAttribute("pathSource", "");
		request.setAttribute("currentUrl", page.getUrl());
		request.setAttribute("searchModeActivated", false);
		request.setAttribute("currentPath", Paths.PATH_DASHBOARD);

		return request;
	}
}
