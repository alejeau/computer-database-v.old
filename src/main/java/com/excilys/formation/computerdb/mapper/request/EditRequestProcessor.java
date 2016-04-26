package com.excilys.formation.computerdb.mapper.request;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.computerdb.controllers.Paths;
import com.excilys.formation.computerdb.controllers.request.ComputerEditObject;
import com.excilys.formation.computerdb.dto.model.ComputerDto;
import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.mapper.date.DateMapper;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.validators.ComputerValidator;

@Component
public class EditRequestProcessor {

	@Autowired
	ComputerDatabaseServiceImpl services;

	public EditRequestProcessor() {
	}

	/**
	 * Sets the corrects information to display about the computer edition
	 * 
	 * @param params the params
	 * @param maw the ModelAndView to modify
	 * @return the modified MAW
	 */
	public ModelAndView processGet(Map<String, String> params, ModelAndView maw) {
		maw.addObject("pathDashboard", Paths.PATH_DASHBOARD);
		maw.addObject("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		maw.addObject("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		maw.addObject("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

		String name = params.get("computer");
		Computer c = services.getComputerByName(name);
		maw = setSelectedComputer(maw, c);

		return maw;
	}

	/**
	 * Creates a ComputerEditObject containing the computer to edit and the URL.
	 * 
	 * @param params the params
	 * @param maw the ModelAndView to modify
	 * @return the modified MAW
	 */
	public ComputerEditObject processPost(Map<String, String> params, ModelAndView maw) {
		List<Problem> listPbs = null;
		long id = Long.valueOf(params.get("computerId"));
		String oldName = services.getComputerById(id).getName();
		String newName = params.get("computerName");
		String intro = params.get("introduced");
		String outro = params.get("discontinued");

		maw.addObject("pathDashboard", Paths.PATH_DASHBOARD);
		maw.addObject("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		maw.addObject("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		maw.addObject("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

		intro = checkDateEntry(intro);
		outro = checkDateEntry(outro);
		intro = parseDate(intro);
		outro = parseDate(outro);

		listPbs = ComputerValidator.validateNewComputer(newName, oldName, intro, outro);

		long cid = Long.valueOf(params.get("companyId"));
		Company cy = this.services.getCompanyById(cid);
		Computer c = new Computer.Builder().id(id).name(newName).intro(intro).outro(outro).company(cy).build();

		if (listPbs == null) {
			listPbs = null;
			listPbs = services.updateComputer(c, oldName);
			maw = setSelectedComputer(maw, c);

			if (listPbs == null) {
				return new ComputerEditObject(maw, null);
			}
		} else {
			maw = setSelectedComputer(maw, c);
		}

		return new ComputerEditObject(maw, listPbs);
	}

	/**
	 * Sets the selectedId of the company's computer.
	 * 
	 * @param params the params
	 * @param maw the ModelAndView to modify
	 * @return the modified MAW
	 */
	private static ModelAndView setSelectedComputer(ModelAndView maw, Computer c) {
		String locale = LocaleContextHolder.getLocale().toString();
		ComputerDto cdto = new ComputerDto(c, locale);
		maw.addObject("cdto", cdto);

		// If the company wasn't set, we set its name to "-1"
		String company = cdto.getCompany();
		if (company.equals("")) {
			maw.addObject("selectedId", "-1");
		} else {
			long cid = c.getCompany().getId();
			maw.addObject("selectedId", String.valueOf(cid));
		}

		return maw;
	}

	private static String checkDateEntry(String date) {
		if (date.equals("")) {
			return null;
		}
		return date;
	}

	private static String parseDate(String date) {
		String locale = LocaleContextHolder.getLocale().toString();
		return DateMapper.mapDate(date, locale);
	}

}
