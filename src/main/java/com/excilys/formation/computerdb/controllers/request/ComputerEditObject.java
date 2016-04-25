package com.excilys.formation.computerdb.controllers.request;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.computerdb.errors.Problem;

/**
 * Container for the ModelAndView and a list of Problem
 * for the edition of a computer.
 */
public class ComputerEditObject {
	ModelAndView maw;
	List<Problem> listPbs = null;

	public ComputerEditObject() {
	}

	public ComputerEditObject(ModelAndView maw, List<Problem> listPbs) {
		this.maw = maw;
		this.listPbs = listPbs;
	}

	public ModelAndView getMaw() {
		return maw;
	}

	public void setMaw(ModelAndView maw) {
		this.maw = maw;
	}

	public List<Problem> getListPbs() {
		return listPbs;
	}

	public void setListPbs(List<Problem> listPbs) {
		this.listPbs = listPbs;
	}
	
	

}
