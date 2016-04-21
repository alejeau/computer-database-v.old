package com.excilys.formation.computerdb.controllers.request;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.computerdb.errors.Problem;

public class ComputerEditObject {
	ModelAndView maw;
	List<Problem> listPbs = null;

	public ComputerEditObject() {
	}

	public ComputerEditObject(ModelAndView response, List<Problem> listPbs) {
		this.maw = response;
		this.listPbs = listPbs;
	}

	public ModelAndView getResponse() {
		return maw;
	}

	public void setResponse(ModelAndView response) {
		this.maw = response;
	}

	public List<Problem> getListPbs() {
		return listPbs;
	}

	public void setListPbs(List<Problem> listPbs) {
		this.listPbs = listPbs;
	}

}
