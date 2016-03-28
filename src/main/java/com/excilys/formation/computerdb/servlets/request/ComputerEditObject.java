package com.excilys.formation.computerdb.servlets.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdb.errors.Problem;

public class ComputerEditObject {
	
	HttpServletRequest request;
	HttpServletResponse response;
	List<Problem> listPbs = null;
	
	public ComputerEditObject() {}
	
	public ComputerEditObject(HttpServletRequest request, HttpServletResponse response, List<Problem> listPbs) {
		this.request = request;
		this.response = response;
		this.listPbs = listPbs;
	}
	
	

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public List<Problem> getListPbs() {
		return listPbs;
	}

	public void setListPbs(List<Problem> listPbs) {
		this.listPbs = listPbs;
	}
	
}
