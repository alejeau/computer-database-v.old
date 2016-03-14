package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ServletEditComputer extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 613906361639763913L;
	protected static final String VIEW = "/WEB-INF/static/views/editComputer.jsp";
	protected ComputerDatabaseServiceImpl services;

	public ServletEditComputer() {
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companyList = services.getAllCompanies();
		Collections.sort(companyList);
		request.setAttribute("companies",  companyList);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
