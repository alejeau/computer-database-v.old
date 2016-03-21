package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.validators.ComputerValidator;

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
		List<Problem> listPbs = null;
		String name	 = request.getParameter("computerName");
		String intro = request.getParameter("introduced");
		String outro = request.getParameter("discontinued");
		
		listPbs = ComputerValidator.validateComputer(name, intro, outro);
		
		if (listPbs == null) {
			long   cid 	 = Long.valueOf(request.getParameter("companyId"));
			Company cy = this.services.getCompanyById(cid);
			this.services.createComputer(name, intro, outro, cy);
		} else {
			System.out.println("There were errors!");
			for (Problem p : listPbs)
				System.out.println(p);
		}
		
		setRequestAndResponse(request, response, listPbs);
		this.getServletContext().getRequestDispatcher(Views.ADD_COMPUTER).forward(request, response);
	}

	private void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response, List<Problem> listPbs)
			throws ServletException, IOException {
		List<Company> companyList = services.getAllCompanies();
		Collections.sort(companyList);

		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		request.setAttribute("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);
		
		request.setAttribute("listErrors", listPbs);

		request.setAttribute("companies",  companyList);
	}

}
