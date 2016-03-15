package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.exceptions.ComputerCreationException;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ServletAddComputer extends HttpServlet {
	private static final long serialVersionUID = 8660445621574175568L;;
	protected ComputerDatabaseServiceImpl services;

	public ServletAddComputer() {
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestAndResponse(request, response, null);
		this.getServletContext().getRequestDispatcher(Views.ADD_COMPUTER).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name	 = request.getParameter("computerName");
		if (name != null) {
			String intro = request.getParameter("introduced");
			String outro = request.getParameter("discontinued");
			long   cid 	 = Long.valueOf(request.getParameter("companyId"));

			Company cy = this.services.getCompanyById(cid);
			try {
				this.services.createComputer(name, intro, outro, cy);
			} catch (ComputerCreationException e) {
				System.out.println(e.getMessage());
			}
		}
		setRequestAndResponse(request, response, null);
		this.getServletContext().getRequestDispatcher(Views.ADD_COMPUTER).forward(request, response);
	}
	
	private void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response, String[] errors)
			throws ServletException, IOException {
		List<Company> companyList = services.getAllCompanies();
		Collections.sort(companyList);

		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_ADD_COMPUTER);
		request.setAttribute("pathEditComputer", Paths.PATH_EDIT_COMPUTER);

		request.setAttribute("companies",  companyList);
	}

}
