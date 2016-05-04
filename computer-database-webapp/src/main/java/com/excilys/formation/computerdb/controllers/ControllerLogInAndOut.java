package com.excilys.formation.computerdb.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.computerdb.constants.Paths;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

@Controller
@RequestMapping("")
public class ControllerLogInAndOut {

	@Autowired
	ComputerDatabaseServiceImpl services;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam Map<String, String> params) {
		ModelAndView maw = new ModelAndView("login");
		maw = setRequest(maw);
		return maw;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(@RequestParam Map<String, String> params) {
		ModelAndView maw = new ModelAndView("login");
		maw = setRequest(maw);
		return maw;
	}

	protected static ModelAndView setRequest(ModelAndView maw) {

		// Setting the paths
		maw.addObject("pathDashboard", Paths.PATH_DASHBOARD);
		maw.addObject("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		maw.addObject("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		maw.addObject("pathSearchComputer", Paths.PATH_COMPUTER_SEARCH);
		maw.addObject("pathComputerDelete", Paths.PATH_COMPUTER_DELETE);
		maw.addObject("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

		maw.addObject("pathSource", "");
		maw.addObject("currentUrl", Paths.PATH_COMPUTER_LOGIN);
		maw.addObject("currentPath", Paths.PATH_COMPUTER_LOGIN);

		return maw;
	}

}
