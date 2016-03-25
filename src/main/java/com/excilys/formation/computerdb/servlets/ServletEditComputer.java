package com.excilys.formation.computerdb.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.dto.ComputerDto;
import com.excilys.formation.computerdb.dto.ProblemDTO;
import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.validators.ComputerValidator;

public class ServletEditComputer extends HttpServlet {
	private static final long serialVersionUID = 613906361639763913L;
	protected ComputerDatabaseServiceImpl services;

	public ServletEditComputer() {
		this.services = ComputerDatabaseServiceImpl.INSTANCE;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		request.setAttribute("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

		String name	= request.getParameter("computer");
		Computer c = this.services.getComputerByName(name);
		request = setComputerDisplay(request, c);
		
//		List<Company> companyList = services.getAllCompanies();
//		request.setAttribute("companies",  companyList);
		this.getServletContext().getRequestDispatcher(Views.EDIT_COMPUTER).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Problem> listPbs = null;
		long   id		= Long.valueOf(request.getParameter("computerId"));
		String oldName 	= this.services.getComputerById(id).getName();
		String newName	= request.getParameter("computerName");
		String intro 	= request.getParameter("introduced");
		String outro 	= request.getParameter("discontinued");

		intro = checkDateEntry(intro);
		outro = checkDateEntry(outro);

		listPbs = ComputerValidator.validateNewComputer(newName, oldName, intro, outro);

		long   cid = Long.valueOf(request.getParameter("companyId"));
		Company cy = this.services.getCompanyById(cid);
		Computer c = new Computer.Builder()
				.id(id)
				.name(newName)
				.intro(intro)
				.outro(outro)
				.company(cy)
				.build();
		if (listPbs == null) {
			listPbs = null;
			listPbs = this.services.updateComputer(c, oldName);
			request = setComputerDisplay(request, c);
			
			if (listPbs == null){
				response.sendRedirect(Paths.PATH_DASHBOARD_RESET);
				return;
			}
		} else {
			request = setComputerDisplay(request, c);
		}
		
		setRequestAndResponse(request, response, listPbs);
		this.getServletContext().getRequestDispatcher(Views.EDIT_COMPUTER).forward(request, response);
	}

	private void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response, List<Problem> listPbs)
			throws ServletException, IOException {
//		List<Company> companyList = services.getAllCompanies();
//		Collections.sort(companyList);

		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		request.setAttribute("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

		request.setAttribute("mapErrors", ProblemDTO.toHashMap(listPbs));

//		request.setAttribute("companies",  companyList);
	}
	
	private HttpServletRequest setComputerDisplay(HttpServletRequest request, Computer c)
			throws ServletException, IOException {
		ComputerDto cdto = new ComputerDto(c);
		request.setAttribute("cdto",  cdto);

		// If the company wasn't set, we set its name to "-1"
		String company = cdto.getCompany();
		if (company.equals("")) {
			request.setAttribute("selectedId",  "-1");	
		} else {
			long cid = this.services.getCompanyByName(company).getId();
			request.setAttribute("selectedId",  String.valueOf(cid));
		}
		
		return request;
	}

	private static String checkDateEntry(String date){
		if (date.equals("")) {
			return null;
		}
		return date;
	}
}
