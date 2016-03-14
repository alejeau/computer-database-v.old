package com.excilys.formation.computerdb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletModifyComputer extends HttpServlet {
	protected static final String VIEW = "/WEB-INF/static/views/editComputer.jsp";

	/**
	 * 
	 */
	private static final long serialVersionUID = 613906361639763913L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
