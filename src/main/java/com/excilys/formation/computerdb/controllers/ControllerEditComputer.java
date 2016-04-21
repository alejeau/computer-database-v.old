package com.excilys.formation.computerdb.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.computerdb.controllers.request.ComputerEditObject;
import com.excilys.formation.computerdb.dto.problems.ProblemDto;
import com.excilys.formation.computerdb.mapper.request.EditRequestProcessor;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.pagination.Page;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

@Controller
@RequestMapping("/access")
public class ControllerEditComputer {

	@Autowired
	ComputerDatabaseServiceImpl services;
	
	@Autowired
	EditRequestProcessor erm;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView get(@RequestParam Map<String, String> params) {
		ModelAndView maw = new ModelAndView("editComputer");
		maw = erm.processGet(params, maw);
		Page<Company> companyList = this.services.getAllCompanies();
		maw.addObject("companies",  companyList.getPage());
		return maw;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView post(@RequestParam Map<String, String> params) {
		ModelAndView maw = new ModelAndView("editComputer");
		Page<Company> companies = this.services.getAllCompanies();
		ComputerEditObject ceo = erm.processPost(params, maw);
		
		if ((ceo.getResponse() == null) && (ceo.getListPbs() == null)) {
			return maw;
		}
		
		maw.addObject("mapErrors", ProblemDto.toHashMap(ceo.getListPbs()));
		maw.addObject("companies",  companies.getPage());
		
		return maw;
	}
}
