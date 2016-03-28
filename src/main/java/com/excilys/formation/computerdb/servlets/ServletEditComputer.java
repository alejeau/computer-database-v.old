package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.dto.problems.ProblemDto;
import com.excilys.formation.computerdb.mapper.request.EditRequestMapper;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.pagination.Page;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.servlets.request.ComputerEditObject;

public class ServletEditComputer extends HttpServlet {
	private static final long serialVersionUID = 613906361639763913L;

	public ServletEditComputer() {}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request = EditRequestMapper.mapDoGet(request);
		Page<Company> companyList = ComputerDatabaseServiceImpl.INSTANCE.getAllCompanies();
		request.setAttribute("companies",  companyList.getPage());
		
		this.getServletContext().getRequestDispatcher(Views.EDIT_COMPUTER).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Page<Company> companies = ComputerDatabaseServiceImpl.INSTANCE.getAllCompanies();
		ComputerEditObject ceo = EditRequestMapper.mapDoPost(request, response);
		
		if ((ceo.getResponse() == null) && (ceo.getListPbs() == null)) {
			response.sendRedirect(Paths.PATH_DASHBOARD_RESET);
			return;
		}
		
		request.setAttribute("mapErrors", ProblemDto.toHashMap(ceo.getListPbs()));
		request.setAttribute("companies",  companies.getPage());
		
		this.getServletContext().getRequestDispatcher(Views.EDIT_COMPUTER).forward(request, response);
	}
}
