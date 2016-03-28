package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.dto.problems.ProblemDto;
import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.pagination.Page;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.validators.ComputerValidator;

public class ServletAddComputer extends HttpServlet {
	private static final long serialVersionUID = 8660445621574175568L;

	public ServletAddComputer() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequest(request, null);
		this.getServletContext().getRequestDispatcher(Views.ADD_COMPUTER).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Problem> listPbs = null;
		ComputerDatabaseServiceImpl services = ComputerDatabaseServiceImpl.INSTANCE;

		String name = request.getParameter("computerName");
		String intro = request.getParameter("introduced");
		String outro = request.getParameter("discontinued");

		listPbs = ComputerValidator.validateComputer(name, intro, outro);

		if (listPbs == null) {
			long cid = Long.valueOf(request.getParameter("companyId"));
			Company cy = services.getCompanyById(cid);
			services.createComputer(name, intro, outro, cy);
		}

		setRequest(request, listPbs);
		this.getServletContext().getRequestDispatcher(Views.ADD_COMPUTER).forward(request, response);
	}

	private void setRequest(HttpServletRequest request, List<Problem> listPbs) throws ServletException, IOException {
		Page<Company> companies = ComputerDatabaseServiceImpl.INSTANCE.getAllCompanies();

		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		request.setAttribute("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

		request.setAttribute("mapErrors", ProblemDto.toHashMap(listPbs));
		request.setAttribute("companies", companies.getPage());
	}

}
