package com.excilys.formation.computerdb.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.computerdb.dto.problems.ProblemDto;
import com.excilys.formation.computerdb.mapper.request.EditRequestProcessor;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.pagination.Page;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.servlets.request.ComputerEditObject;

@WebServlet(name = "ServletEdit", urlPatterns = "/access/edit")
public class ServletEditComputer extends HttpServlet {
	private static final long serialVersionUID = 613906361639763913L;

	@Autowired
	ComputerDatabaseServiceImpl services;
	
	@Autowired
	EditRequestProcessor erm;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public ServletEditComputer() {}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request = erm.processDoGet(request);
		Page<Company> companyList = this.services.getAllCompanies();
		request.setAttribute("companies",  companyList.getPage());
		
		request.getServletContext().getRequestDispatcher(Views.EDIT_COMPUTER).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Page<Company> companies = this.services.getAllCompanies();
		ComputerEditObject ceo = erm.processDoPost(request, response);
		
		if ((ceo.getResponse() == null) && (ceo.getListPbs() == null)) {
			response.sendRedirect(Paths.PATH_DASHBOARD_RESET);
			return;
		}
		
		request.setAttribute("mapErrors", ProblemDto.toHashMap(ceo.getListPbs()));
		request.setAttribute("companies",  companies.getPage());
		
		request.getServletContext().getRequestDispatcher(Views.EDIT_COMPUTER).forward(request, response);
	}
}
