package com.excilys.formation.computerdb.mapper.request;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.computerdb.dto.model.ComputerDto;
import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.servlets.Paths;
import com.excilys.formation.computerdb.servlets.request.ComputerEditObject;
import com.excilys.formation.computerdb.validators.ComputerValidator;

@Component
public class EditRequestProcessor {
	
	@Autowired
	ComputerDatabaseServiceImpl services;
	
	@Autowired
	ComputerValidator cval;
	
	public EditRequestProcessor() {
	}
	
	public HttpServletRequest processDoGet(HttpServletRequest request) {
		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		request.setAttribute("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

		String name	= request.getParameter("computer");
		Computer c = services.getComputerByName(name);
		request = setComputerDisplay(request, c);
		
		return request;
	}
	
	public ComputerEditObject processDoPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Problem> listPbs = null;
		long   id		= Long.valueOf(request.getParameter("computerId"));
		String oldName 	= services.getComputerById(id).getName();
		String newName	= request.getParameter("computerName");
		String intro 	= request.getParameter("introduced");
		String outro 	= request.getParameter("discontinued");

		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		request.setAttribute("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);
		

		intro = checkDateEntry(intro);
		outro = checkDateEntry(outro);

		listPbs = cval.validateNewComputer(newName, oldName, intro, outro);

		long   cid = Long.valueOf(request.getParameter("companyId"));
		Company cy = services.getCompanyById(cid);
		Computer c = new Computer.Builder()
				.id(id)
				.name(newName)
				.intro(intro)
				.outro(outro)
				.company(cy)
				.build();
		
		if (listPbs == null) {
			listPbs = null;
			listPbs = services.updateComputer(c, oldName);
			request = setComputerDisplay(request, c);
			
			if (listPbs == null){
				return new ComputerEditObject(null, response, null);
			}
		} else {
			request = setComputerDisplay(request, c);
		}
		
		return new ComputerEditObject(request, response, listPbs);
	}

	
	private HttpServletRequest setComputerDisplay(HttpServletRequest request, Computer c) {
		ComputerDto cdto = new ComputerDto(c);
		request.setAttribute("cdto",  cdto);

		// If the company wasn't set, we set its name to "-1"
		String company = cdto.getCompany();
		if (company.equals("")) {
			request.setAttribute("selectedId",  "-1");	
		} else {
			long cid = services.getCompanyByName(company).getId();
			request.setAttribute("selectedId",  String.valueOf(cid));
		}
		
		return request;
	}

	private String checkDateEntry(String date){
		if (date.equals("")) {
			return null;
		}
		return date;
	}
	
}