package com.excilys.formation.computerdb.mapper.request;

import java.util.List;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.computerdb.constants.Paths;
import com.excilys.formation.computerdb.dto.model.ComputerDto;
import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.mapper.date.DateMapper;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.validators.ComputerValidator;

@Component
public class EditRequestProcessor {

	public EditRequestProcessor() {
	}

	/**
	 * Sets the corrects information to display about the computer edition
	 * 
	 * @param params the params
	 * @param maw the ModelAndView to modify
	 * @return the modified MAW
	 */
	public ModelAndView processGet(Map<String, String> params, Computer c, ModelAndView maw) {
		maw.addObject("pathDashboard", Paths.PATH_DASHBOARD);
		maw.addObject("pathAddComputer", Paths.PATH_COMPUTER_ADD);
		maw.addObject("pathEditComputer", Paths.PATH_COMPUTER_EDIT);
		maw.addObject("pathDashboardReset", Paths.PATH_DASHBOARD_RESET);

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
	public ComputerEditObject processPostPartI(Map<String, String> params, String oldName, ModelAndView maw) {
		List<Problem> listPbs = null;
		long id = Long.valueOf(params.get("computerId"));
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
		Computer c = new Computer.Builder().id(id).name(newName).intro(intro).outro(outro).build();

		return new ComputerEditObject(maw, listPbs, c);
	}
	
	public ModelAndView processPostPartII(Map<String, String> params, ComputerEditObject ceo) {
		ModelAndView maw = ceo.getMaw();
		Computer c = ceo.getComputer();
		
		maw = setSelectedComputer(maw, c);

		return maw;
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
