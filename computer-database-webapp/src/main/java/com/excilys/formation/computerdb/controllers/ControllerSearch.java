package com.excilys.formation.computerdb.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.computerdb.constants.Paths;
import com.excilys.formation.computerdb.mapper.model.PageDtoMapper;
import com.excilys.formation.computerdb.mapper.request.ComputerSearchPageRequest;
import com.excilys.formation.computerdb.mapper.request.SearchRequestMapper;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.model.pagination.SearchPage;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

@Controller
@RequestMapping("/access")
public class ControllerSearch {
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ComputerDatabaseServiceImpl services;

	/**
	 * Displays a list of computers matching the search keyword.
	 * 
	 * @param params the list of parameter given by the URL
	 * @return the ModelAndView modified accordingly
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView get(@RequestParam Map<String, String> params) {
		LOG.info("SEARCH GET");
		ModelAndView maw = new ModelAndView("dashboard");
		ComputerSearchPageRequest page = SearchRequestMapper.mapGet(params);

		// We feed content to the page
		SearchPage<Computer> sp = page.getComputerSearchPage();
		int currentPageNumber = sp.getCurrentPageNumber();
		sp = this.services.getComputerSearchPage(currentPageNumber, sp);
		page.setPage(sp);

		maw = setRequest(maw, page);
		return maw;
	}
	

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public void loginPost(@RequestParam Map<String, String> params) {
		LOG.info("SEARCH POST");
	}

	protected static ModelAndView setRequest(ModelAndView maw, ComputerSearchPageRequest page) {

		// Setting the paths
		maw.addObject("pathDashboard", Paths.PATH_DASHBOARD);
		maw.addObject("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		maw.addObject("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		maw.addObject("pathSearchComputer", Paths.PATH_COMPUTER_SEARCH);
		maw.addObject("pathComputerDelete", Paths.PATH_COMPUTER_DELETE);
		maw.addObject("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

		// Setting the vars
		String locale = LocaleContextHolder.getLocale().toString();
		maw.addObject("PAGE", PageDtoMapper.toSearchPageDto(page.getComputerSearchPage(), locale));
		maw.addObject("pathSource", "../");
		maw.addObject("currentUrl", page.getUrl());
		maw.addObject("currentPath", Paths.PATH_COMPUTER_SEARCH);
		maw.addObject("searchModeActivated", true);

		return maw;
	}

}
