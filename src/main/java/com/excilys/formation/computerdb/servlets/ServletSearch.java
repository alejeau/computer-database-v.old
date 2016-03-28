package com.excilys.formation.computerdb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.mapper.model.PageDtoMapper;
import com.excilys.formation.computerdb.mapper.request.SearchRequestMapper;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.servlets.request.ComputerSearchPageRequest;

public class ServletSearch extends HttpServlet {
	private static final long serialVersionUID = -2466894131493728982L;

	public ServletSearch() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerSearchPageRequest<Computer> page = SearchRequestMapper.mapDoGet(request);
		request = setRequest(request, page);
		this.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long[] listId = SearchRequestMapper.mapDoPost(request);
		ComputerDatabaseServiceImpl.INSTANCE.deleteComputers(listId);
	}
	
	protected static HttpServletRequest setRequest(HttpServletRequest request, ComputerSearchPageRequest<Computer> page){
		// Setting the paths 
		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		request.setAttribute("pathSearchComputer", Paths.PATH_COMPUTER_SEARCH);
		request.setAttribute("pathComputerDelete", Paths.PATH_COMPUTER_DELETE);
		request.setAttribute("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

		// Setting the vars
		request.setAttribute("PAGE", PageDtoMapper.toSearchPageDto(page.getComputerSearchPage()));
		request.setAttribute("pathSource", "../");
		request.setAttribute("currentUrl", page.getUrl());
		request.setAttribute("currentPath", Paths.PATH_COMPUTER_SEARCH);
		request.setAttribute("searchModeActivated", true);
		
		return request;
	}

}
