package com.excilys.formation.computerdb.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.computerdb.mapper.request.DashboardRequestMapper;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

@Controller
@RequestMapping("/delete")
public class ControllerDelete {

	@Autowired
	ComputerDatabaseServiceImpl services;

	/**
	 * Reroutes to the dashboard.
	 * 
	 * @param params the list of parameter given by the URL
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get() {
		System.out.println("DELETE GET");
		ModelAndView maw = new ModelAndView("redirect:/access");
		return maw;
	}

	/**
	 * Deletes a list of computer.
	 * 
	 * @param params the list of parameter given by the URL
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void post(@RequestParam Map<String, String> params) {
		System.out.println("DELETE POST");
		long[] listId = DashboardRequestMapper.mapPost(params);
		this.services.deleteComputers(listId);
	}
}