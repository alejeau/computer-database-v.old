package com.excilys.formation.computerdb.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.computerdb.constants.Paths;
import com.excilys.formation.computerdb.controllers.req.ComputerSortedPageRequest;
import com.excilys.formation.computerdb.mapper.model.PageDtoMapper;
import com.excilys.formation.computerdb.mapper.request.DashboardRequestMapper;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.model.pagination.SortedPage;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

@Controller
@RequestMapping("/access")
public class ControllerDashboard {

	@Autowired
	ComputerDatabaseServiceImpl services;

	/**
	 * Displays a list of computer in the main page.
	 * 
	 * @param params the list of parameter given by the URL
	 * @return the ModelAndView modified accordingly
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(@RequestParam Map<String, String> params) {
		ModelAndView maw = new ModelAndView("dashboard");
		ComputerSortedPageRequest page = DashboardRequestMapper.mapGet(params);

		// We feed content to the page
		SortedPage<Computer> sp = page.getComputerSortedPage();
		int currentPageNumber = sp.getCurrentPageNumber();
		sp = this.services.getComputerSortedPage(currentPageNumber, sp);
		page.setPage(sp);

		maw = setRequest(maw, page);

		return maw;
	}

	/**
	 * Deletes a list of computer.
	 * 
	 * @param params the list of parameter given by the URL
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView post(@RequestParam Map<String, String> params) {
		ModelAndView maw = new ModelAndView("dashboard");
		long[] listId = DashboardRequestMapper.mapPost(params);
		this.services.deleteComputers(listId);
		
		ComputerSortedPageRequest page = DashboardRequestMapper.mapGet(params);

		// We feed content to the page
		SortedPage<Computer> sp = page.getComputerSortedPage();
		int currentPageNumber = sp.getCurrentPageNumber();
		sp = this.services.getComputerSortedPage(currentPageNumber, sp);
		page.setPage(sp);

		maw = setRequest(maw, page);
		
		return maw;
	}

	protected static ModelAndView setRequest(ModelAndView maw, ComputerSortedPageRequest page) {
		// Setting the paths
		maw.addObject("pathDashboard", Paths.PATH_DASHBOARD);
		maw.addObject("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		maw.addObject("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		maw.addObject("pathSearchComputer", Paths.PATH_COMPUTER_SEARCH);
		maw.addObject("pathComputerDelete", Paths.PATH_COMPUTER_DELETE);
		maw.addObject("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

		// Setting the vars
		String locale = LocaleContextHolder.getLocale().toString();
		maw.addObject("PAGE", PageDtoMapper.toSortedPageDto(page.getComputerSortedPage(), locale));
		maw.addObject("pathSource", "/resources");
		maw.addObject("currentUrl", page.getUrl());
		maw.addObject("searchModeActivated", false);
		maw.addObject("currentPath", Paths.PATH_DASHBOARD);

		return maw;
	}
}
