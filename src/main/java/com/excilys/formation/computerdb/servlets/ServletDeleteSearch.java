package com.excilys.formation.computerdb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.excilys.formation.computerdb.mapper.ComputerDTOMapper;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public class ServletDeleteSearch extends HttpServlet {
	private static final long serialVersionUID = -4413514853338781488L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		process(request);

//		setRequestAndResponse(request, response);
		response.sendRedirect(Paths.PATH_COMPUTER_SEARCH);
//		this.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		delete(request);
		
//		response.sendRedirect(Paths.PATH_S);
//		setRequestAndResponse(request, response);
	}
/*
	private void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestPath(request);

		request.setAttribute("nbComputers", this.pager.getNbEntries());
		request.setAttribute("currentPageNumber", this.pager.getCurrentPageNumber());
		request.setAttribute("maxPageNumber", this.pager.getMaxPageNumber());
		request.setAttribute("computers", ComputerDTOMapper.toDTO(this.pager.getCurrentPage()));
		request.setAttribute("pathSource", "../");
		request.setAttribute("currentPath", Paths.PATH_DASHBOARD);

	}

	private void setRequestPath(HttpServletRequest request) {
		// Sending the paths to the project files
		request.setAttribute("pathDashboard", Paths.PATH_DASHBOARD);
		request.setAttribute("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		request.setAttribute("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		request.setAttribute("pathSearchComputer", Paths.PATH_COMPUTER_SEARCH);
		request.setAttribute("pathComputerDelete", Paths.PATH_COMPUTER_DELETE);
		request.setAttribute("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);
	}

	private void process(HttpServletRequest request) {
		String reset = null;
		reset = request.getParameter("reset");
		if (reset != null) {
			if (reset.equals("true")) {
				try {
					this.pager.goToPageNumber(0);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		String move = null;
		move = request.getParameter("page");
		if (move != null) {
			if (move.equals(Paths.PREVIOUS_PAGE)) {
				this.pager.getPreviousPage();
			} else if (move.equals(Paths.NEXT_PAGE)) {
				this.pager.getNextPage();
			}
		}

		String pageNb = null;
		pageNb = request.getParameter("pageNb");
		if (pageNb != null) {
			int nb = Integer.parseInt(pageNb);
			try {
				this.pager.goToPageNumber(nb);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}

		String displayBy = null;
		displayBy = request.getParameter("displayBy");
		if (displayBy != null) {
			int db = Integer.parseInt(displayBy);
			try {
				this.pager.setObjectsPerPages(db);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
//*/
	public void delete(HttpServletRequest request) {
		String del = request.getParameter("selection");
		if (!del.equals("")) {
			String[] list = del.split(",");
			int len = list.length;
			long[] listId = new long[len];
			for (int i = 0; i < len; i++) {
				listId[i] = Long.valueOf(list[i]);
			}
			ComputerDatabaseServiceImpl.INSTANCE.deleteComputers(listId);
//			this.pager.update();
		}
	}
}
