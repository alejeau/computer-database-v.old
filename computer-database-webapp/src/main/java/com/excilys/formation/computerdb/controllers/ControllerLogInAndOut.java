package com.excilys.formation.computerdb.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ComputerDatabaseServiceImpl services;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam Map<String, String> params) {
		LOG.info("LOGIN GET");
		ModelAndView maw = new ModelAndView("login");
		maw = setRequest(maw);
		return maw;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void loginPost(@RequestParam Map<String, String> params) {
		LOG.info("LOGIN POST");
	}

	@RequestMapping(value = "/j_spring_security_check", method = RequestMethod.GET)
	public ModelAndView j(@RequestParam Map<String, String> params) {
		LOG.info("J GET");
		ModelAndView maw = new ModelAndView("redirect:/access");
		maw = setRequest(maw);
		return maw;
	}
	
	@RequestMapping(value = "/j_spring_security_check", method = RequestMethod.POST)
	public void jPost(@RequestParam Map<String, String> params) {
		LOG.info("J POST");
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(@RequestParam Map<String, String> params) {
		LOG.info("LOGOUT GET");
		
		SecurityContextHolder.getContext().setAuthentication(null);
        
//		ModelAndView maw = new ModelAndView("redirect:/login");
		ModelAndView maw = new ModelAndView("redirect:/j_spring_security_check");
        maw.addObject("logout", "out");
		maw = setRequest(maw);
		
		return maw;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public void logoutPost(@RequestParam Map<String, String> params) {
		LOG.info("LOGOUT POST");
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
