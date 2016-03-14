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

public class ServletAddComputer extends HttpServlet {
	private static final long serialVersionUID = 8660445621574175568L;
	protected static final String VIEW = "/WEB-INF/static/views/addComputer.jsp";
	protected ComputerDatabaseServiceImpl services;
	protected List<Company> list;
	
	public ServletAddComputer(){
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companyList = services.getAllCompanies();
		Collections.sort(companyList);
		request.setAttribute("companies",  companyList);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

}
