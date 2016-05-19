package com.excilys.formation.computerdb.rest.impl;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.computerdb.constants.Fields;
import com.excilys.formation.computerdb.dto.model.ComputerDto;
import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.mapper.model.ComputerDtoMapper;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.model.User;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.service.impl.UserServiceImpl;
import com.excilys.formation.computerdb.rest.RestManager;

@RestController
@RequestMapping("rest")
public class RestManagerImpl implements RestManager {
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ComputerDatabaseServiceImpl services;

	@Autowired
	UserServiceImpl userServices;
	
	public RestManagerImpl() {
	}
	
	@RequestMapping(value = "/computers/total", method = RequestMethod.GET)
	public ResponseEntity<Integer> getNbComputers() {
		Integer nb = this.services.getNbComputers();
		return response(nb);
	}
	
	@RequestMapping(value = "/companies/total", method = RequestMethod.GET)
	public ResponseEntity<Integer> getNbCompanies(){
		Integer nb = this.services.getNbCompanies();
		return response(nb);
	}

	@Override
	@RequestMapping(value = "/computers/all", method = RequestMethod.GET)
	public ResponseEntity<List<ComputerDto>> listAllComputers() {
		List<Computer> tmp = services.listAllComputers();
		List<ComputerDto> list = ComputerDtoMapper.toDtoList(tmp);
		return listResponse(list);
	}

	@Override
	@RequestMapping(value = "/computers/page/{pageNumber}/{objectPerPage}", method = RequestMethod.GET)
	public ResponseEntity<List<ComputerDto>> listComputerPage(
			@PathVariable("pageNumber") int pageNumber,
			@PathVariable("objectPerPage") int objectPerPage) {
		int offset = pageNumber * objectPerPage;
		List<Computer> tmp = services.getComputerList(offset, objectPerPage);
		List<ComputerDto> list = ComputerDtoMapper.toDtoList(tmp);
		return listResponse(list);
	}

	@Override
	@RequestMapping(value = "/computers/page/{pageNumber}/{objectPerPage}/{field}/{ascending}", method = RequestMethod.GET)
	public ResponseEntity<List<ComputerDto>> listComputerSortedPage(
			@PathVariable("pageNumber") int pageNumber,
			@PathVariable("objectPerPage") int objectPerPage,
			@PathVariable("field") String stringField,
			@PathVariable("ascending") boolean ascending) {
		List<Computer> tmp;
		int offset = pageNumber * objectPerPage;

		// Converting stringField into an actual Fields
		Fields field = toFields(stringField);

		tmp = services.getComputerSortedList(offset, objectPerPage, field, ascending);
		List<ComputerDto> list = ComputerDtoMapper.toDtoList(tmp);
		return listResponse(list);
	}

	@Override
	@RequestMapping(value = "/companies/all", method = RequestMethod.GET)
	public ResponseEntity<List<Company>> listAllCompanies() {
		List<Company> list = null;
		list = services.listAllCompanies();
		return listResponse(list);
	}

	@Override
	@RequestMapping(value = "/companies/page/{pageNumber}/{objectPerPage}", method = RequestMethod.GET)
	public ResponseEntity<List<Company>> listCompanyPage(@PathVariable("pageNumber") int pageNumber,
			@PathVariable("objectPerPage") int objectPerPage) {
		int offset = pageNumber * objectPerPage;
		List<Company> list = services.getCompanyList(offset, objectPerPage);
		return listResponse(list);
	}

	@Override
	@RequestMapping(value = "/computers/search/{keyword}/{pageNumber}/{objectPerPage}", method = RequestMethod.GET)
	public ResponseEntity<List<ComputerDto>> listCompanySearch(
			@PathVariable("keyword") String keyword,
			@PathVariable("pageNumber") int pageNumber,
			@PathVariable("objectPerPage") int objectPerPage) {
		int offset = pageNumber * objectPerPage;
		List<Computer> tmp = services.getComputerSearchList(keyword, offset, objectPerPage);
		List<ComputerDto> list = ComputerDtoMapper.toDtoList(tmp);
		return listResponse(list);
	}

	@Override
	@RequestMapping(value = "/computers/search/{keyword}/{pageNumber}/{objectPerPage}/{field}/{ascending}", method = RequestMethod.GET)
	public ResponseEntity<List<ComputerDto>> listCompanySortedSearch(
			@PathVariable("keyword") String keyword,
			@PathVariable("pageNumber") int pageNumber,
			@PathVariable("objectPerPage") int objectPerPage,
			@PathVariable("field") String stringField,
			@PathVariable("ascending") boolean ascending) {
		int offset = pageNumber * objectPerPage;
		Fields field = toFields(stringField);
		List<Computer> tmp = services.getComputerSearchList(keyword, offset, objectPerPage, field, ascending);
		List<ComputerDto> list = ComputerDtoMapper.toDtoList(tmp);
		return listResponse(list);
	}
	

	@Override
	@RequestMapping(value = "/company/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<Company> getCompanyByName(@PathVariable("name") String name) {
		Company c = services.getCompanyByName(name);
		return response(c);
	}

	@Override
	@RequestMapping(value = "/computer/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<ComputerDto> getComputerByName(@PathVariable("name") String name) {
		Computer c = services.getComputerByName(name);
		ComputerDto cdto = new ComputerDto(c);
		return response(cdto);
	}

	@Override
	@RequestMapping(value = "/computer/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<ComputerDto> getComputerById(@PathVariable("id") long id) {
		Computer c = services.getComputerById(id);
		ComputerDto cdto = new ComputerDto(c);
		return response(cdto);
	}

	@Override
	@RequestMapping(value = "/computer/add", method = RequestMethod.POST)
	public ResponseEntity<List<Problem>> createComputer(@RequestBody ComputerDto cdto) {
		LOG.info("REST CREATE POST");
		HttpStatus status = HttpStatus.BAD_REQUEST;
		List<Problem> pbs = new LinkedList<>();

		String name = cdto.getName();
		String companyName = cdto.getCompany();

		if (services.existsComputer(name)) {
			pbs.add(new Problem(Fields.NAME.toString(), "A computer with such a name already exists"));
		} else if ((companyName != null) && !services.existsCompany(companyName)) {
			pbs.add(new Problem(Fields.COMPANY.toString(), "No company with such a name exists."));
		} else {
			String intro = cdto.getIntro();
			String outro = cdto.getOutro();
			Company cy = (companyName != null && !companyName.equals("")) ? services.getCompanyByName(companyName) : null;
			pbs = services.createComputer(name, intro, outro, cy);
			if (pbs == null || pbs.isEmpty()) {
				status = HttpStatus.CREATED;
			}
		}

		return new ResponseEntity<List<Problem>>(pbs, status);
	}

	@Override
	@RequestMapping(value = "/computer/update", method = RequestMethod.PUT)
	public ResponseEntity<Problem> updateComputer(@RequestBody ComputerDto cdto) {
		LOG.info("REST UPDATE PUT");
		Computer ori = services.getComputerById(cdto.getId());
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problem pb = null;

		String name = cdto.getName();
		String companyName = cdto.getCompany();

		// If new name is already taken
		if (!name.equals(ori.getName()) && services.existsComputer(name)) {
			pb = new Problem(Fields.NAME.toString(), "A computer with such a name already exists");
		} else if ((companyName != null) && !services.existsCompany(companyName)) {
			// If the company does not exists
			pb = new Problem(Fields.COMPANY.toString(), "No company with such a name exists.");
		} else {
			String intro = cdto.getIntro();
			String outro = cdto.getOutro();
			Company cy = (companyName != null) ? services.getCompanyByName(companyName) : null;
			Computer c = new Computer(cdto.getId(), name, intro, outro, cy);
			services.updateComputer(c);
			status = HttpStatus.CREATED;
		}

		return new ResponseEntity<Problem>(pb, status);
	}

	@Override
	@RequestMapping(value = "/computer/del/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteComputer(@PathVariable("id") long id) {
		LOG.info("REST DELETE COMPUTER");
		Computer c = null;
		c = services.getComputerById(id);

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String pb = null;

		if (c == null) {
			pb = "No such Computer exists!";
		} else {
			services.deleteComputer(id);
			status = HttpStatus.OK;
		}

		return new ResponseEntity<String>(pb, status);
	}

	@Override
	@RequestMapping(value = "/company/del/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCompany(@PathVariable("id") long id) {
		LOG.info("REST DELETE COMPANY");
		Company c = null;
		c = services.getCompanyById(id);

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String pb = null;

		if (c == null) {
			pb = "No such Company exists!";
		} else {
			services.deleteCompany(id);
			status = HttpStatus.OK;
		}

		return new ResponseEntity<String>(pb, status);
	}

//	/**
//	 * Retrieves a list of all Users
//	 */
//	@RequestMapping(value = "/user/all/", method = RequestMethod.GET)
//	public ResponseEntity<String> showListUser() {
//		
//	}

	/**
	 * Adds a user
	 */
	@Override
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public ResponseEntity<String> addUser(@RequestBody User u) {
		if (u == null) {
			return new ResponseEntity<String>("User is null.", HttpStatus.BAD_REQUEST);
		}
		if (u.getLogin().length() == 0) {
			return new ResponseEntity<String>("User login can't be null.", HttpStatus.BAD_REQUEST);
		}
		if (u.getPassword().length() == 0) {
			return new ResponseEntity<String>("User password can't be null.", HttpStatus.BAD_REQUEST);
		}
		
		userServices.createUser(u);
		return new ResponseEntity<String>("", HttpStatus.CREATED);
	}

	/**
	 * Deletes a given User
	 */
	@Override
	@RequestMapping(value = "/user/del/{login}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable("login") String login){
		if (login == null || (login.length() == 0)) {
			return new ResponseEntity<String>("Username can't be null.", HttpStatus.BAD_REQUEST);
		}
		
		userServices.deleteUser(login);
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	protected static Fields toFields(String f) {
		// Converting stringField into an actual Fields
		Fields field = Fields.NAME;
		if (f.equals(Fields.INTRO_DATE.toString())) {
			field = Fields.INTRO_DATE;
		} else if (f.equals(Fields.OUTRO_DATE.toString())) {
			field = Fields.OUTRO_DATE;
		} else if (f.equals(Fields.COMPANY.toString())) {
			field = Fields.COMPANY;
		}
		return field;
	}

	protected <T> ResponseEntity<T> response(T object) {
		if (object == null) {
			return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<T>(object, HttpStatus.OK);
	}

	protected <T> ResponseEntity<List<T>> listResponse(List<T> list) {
		if (list.isEmpty()) {
			return new ResponseEntity<List<T>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<T>>(list, HttpStatus.OK);
	}
}
