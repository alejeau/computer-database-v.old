package com.excilys.formation.computerdb.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.computerdb.dto.problems.ProblemDto;
import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.mapper.request.ComputerEditObject;
import com.excilys.formation.computerdb.mapper.request.EditRequestProcessor;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.model.pagination.Page;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

@Controller
@RequestMapping("/access")
public class ControllerEditComputer {
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ComputerDatabaseServiceImpl services;

	@Autowired
	EditRequestProcessor erm;

	/**
	 * Displays a list of Computer.
	 * 
	 * @param params the list of parameter given by the URL
	 * @return the ModelAndView modified accordingly
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView get(@RequestParam Map<String, String> params) {
		LOG.info("EDIT GET");
		ModelAndView maw = new ModelAndView("editComputer");

		String name = params.get("computer");
		Computer c = services.getComputerByName(name);
		
		maw = erm.processGet(params, c, maw);

		Page<Company> companyList = this.services.getAllCompanies();
		maw.addObject("companies", companyList.getPage());
		return maw;
	}

	/**
	 * Deletes a list of Computer.
	 * 
	 * @param params the list of parameter given by the URL
	 * @return the ModelAndView modified accordingly
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView post(@RequestParam Map<String, String> params) {
		LOG.info("EDIT POST");
		ModelAndView maw = new ModelAndView("editComputer");
		Page<Company> companies = this.services.getAllCompanies();

		long id = Long.valueOf(params.get("computerId"));
		String oldName = services.getComputerById(id).getName();
		
		ComputerEditObject ceo = erm.processPostPartI(params, oldName, maw);
		
		long cid = Long.valueOf(params.get("companyId"));
		Company cy = this.services.getCompanyById(cid);
		Computer c = ceo.getComputer();
		c.setCompany(cy);
		ceo.setComputer(c);
		ceo.setMaw(maw);
		
		List<Problem> listPbs = ceo.getListPbs();
		
		if (listPbs == null) {
			listPbs = null;
			listPbs = services.updateComputer(c, oldName);
		}
		
		maw = erm.processPostPartII(params, ceo);

		if ((maw == null) && (listPbs == null)) {
			return maw;
		}

		maw.addObject("mapErrors", ProblemDto.toHashMap(listPbs));
		maw.addObject("companies", companies.getPage());

		return maw;
	}
}
