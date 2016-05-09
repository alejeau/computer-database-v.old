package com.excilys.formation.computerdb.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdb.constants.Paths;
import com.excilys.formation.computerdb.dto.problems.ProblemDto;
import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.mapper.date.DateMapper;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.pagination.Page;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.validators.ComputerValidator;

/**
 * Allows for the addition
 * 
 * @author excilys
 *
 */
@Controller
@RequestMapping("/access")
public class ControllerAddComputer {
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ComputerDatabaseServiceImpl services;

	/**
	 * Displays problems encountered while adding a Computer if appropriate.
	 * 
	 * @param params the list of parameter given by the URL
	 * @return the ModelAndView modified accordingly
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView get(@RequestParam Map<String, String> params) {
		LOG.info("ADD GET");
		ModelAndView maw = new ModelAndView("addComputer");
		maw = setRequest(maw, null);
		return maw;
	}

	/**
	 * Adds a computer into the database
	 * 
	 * @param params the list of parameter given by the URL
	 * @return the ModelAndView modified accordingly
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView post(@RequestParam Map<String, String> params) {
		LOG.info("ADD POST");
		ModelAndView maw = new ModelAndView("addComputer");
		List<Problem> listPbs = null;

		String name = params.get("computerName");
		String intro = params.get("introduced");
		String outro = params.get("discontinued");
		intro = parseDate(intro);
		outro = parseDate(outro);

		// We check the validity of the data
		listPbs = ComputerValidator.validateComputer(name, intro, outro);

		// If valid, we try to add the computer
		if (listPbs == null) {
			long cid = Long.valueOf(params.get("companyId"));
			Company cy = services.getCompanyById(cid);
			services.createComputer(name, intro, outro, cy);
		}

		maw = setRequest(maw, listPbs);
		return maw;
	}

	private ModelAndView setRequest(ModelAndView maw, List<Problem> listPbs) {
		Page<Company> companies = this.services.getAllCompanies();

		maw.addObject("pathDashboard", Paths.PATH_DASHBOARD);
		maw.addObject("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		maw.addObject("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		maw.addObject("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

		maw.addObject("mapErrors", ProblemDto.toHashMap(listPbs));
		maw.addObject("companies", companies.getPage());

		return maw;
	}

	private static String parseDate(String date) {
		String locale = LocaleContextHolder.getLocale().toString();
		return DateMapper.mapDate(date, locale);
	}

}
