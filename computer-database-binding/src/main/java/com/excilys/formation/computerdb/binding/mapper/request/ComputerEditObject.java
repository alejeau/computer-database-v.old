package com.excilys.formation.computerdb.mapper.request;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.model.Computer;

/**
 * Container for the ModelAndView and a list of Problem for the edition of a computer.
 */
public class ComputerEditObject {
	ModelAndView maw;
	List<Problem> listPbs = null;
	Computer computer;

	public ComputerEditObject() {
	}

	public ComputerEditObject(ModelAndView maw, List<Problem> listPbs, Computer c) {
		this.maw = maw;
		this.listPbs = listPbs;
		this.computer = c;
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

	public Computer getComputer() {
		return computer;
	}

	public void setComputer(Computer c) {
		this.computer = c;
	}
}
