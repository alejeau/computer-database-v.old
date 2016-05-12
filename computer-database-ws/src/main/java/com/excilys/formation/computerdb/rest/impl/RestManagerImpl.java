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
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;
import com.excilys.formation.computerdb.rest.RestManager;

@RestController
@RequestMapping("rest")
public class RestManagerImpl implements RestManager {
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ComputerDatabaseServiceImpl service;

	public RestManagerImpl() {
	}
	
	@RequestMapping(value = "/computers/total", method = RequestMethod.GET)
	public ResponseEntity<Integer> getNbComputers() {
		Integer nb = this.service.getNbComputers();
		return response(nb);
	}
	
	@RequestMapping(value = "/companies/total", method = RequestMethod.GET)
	public ResponseEntity<Integer> getNbCompanies(){
		Integer nb = this.service.getNbCompanies();
		return response(nb);
	}

	@Override
	@RequestMapping(value = "/computers/all", method = RequestMethod.GET)
	public ResponseEntity<List<ComputerDto>> listAllComputers() {
		List<Computer> tmp = service.listAllComputers();
		List<ComputerDto> list = ComputerDtoMapper.toDtoList(tmp);
		return listResponse(list);
	}

	@Override
	@RequestMapping(value = "/computers/page/{pageNumber}/{objectPerPage}", method = RequestMethod.GET)
	public ResponseEntity<List<ComputerDto>> listComputerPage(
			@PathVariable("pageNumber") int pageNumber,
			@PathVariable("objectPerPage") int objectPerPage) {
		int offset = pageNumber * objectPerPage;
		List<Computer> tmp = service.getComputerList(offset, objectPerPage);
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

		tmp = service.getComputerSortedList(offset, objectPerPage, field, ascending);
		List<ComputerDto> list = ComputerDtoMapper.toDtoList(tmp);
		return listResponse(list);
	}

	@Override
	@RequestMapping(value = "/companies/all", method = RequestMethod.GET)
	public ResponseEntity<List<Company>> listAllCompanies() {
		List<Company> list = null;
		list = service.listAllCompanies();
		return listResponse(list);
	}

	@Override
	@RequestMapping(value = "/companies/page/{pageNumber}/{objectPerPage}", method = RequestMethod.GET)
	public ResponseEntity<List<Company>> listCompanyPage(@PathVariable("pageNumber") int pageNumber,
			@PathVariable("objectPerPage") int objectPerPage) {
		int offset = pageNumber * objectPerPage;
		List<Company> list = service.getCompanyList(offset, objectPerPage);
		return listResponse(list);
	}

	@Override
	@RequestMapping(value = "/computers/search/{keyword}/{pageNumber}/{objectPerPage}", method = RequestMethod.GET)
	public ResponseEntity<List<ComputerDto>> listCompanySearch(
			@PathVariable("keyword") String keyword,
			@PathVariable("pageNumber") int pageNumber,
			@PathVariable("objectPerPage") int objectPerPage) {
		int offset = pageNumber * objectPerPage;
		List<Computer> tmp = service.getComputerSearchList(keyword, offset, objectPerPage);
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
		List<Computer> tmp = service.getComputerSearchList(keyword, offset, objectPerPage, field, ascending);
		List<ComputerDto> list = ComputerDtoMapper.toDtoList(tmp);
		return listResponse(list);
	}
	

	@Override
	@RequestMapping(value = "/company/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<Company> getCompanyByName(@PathVariable("name") String name) {
		Company c = service.getCompanyByName(name);
		return response(c);
	}

	@Override
	@RequestMapping(value = "/computer/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<ComputerDto> getComputerByName(@PathVariable("name") String name) {
		Computer c = service.getComputerByName(name);
		ComputerDto cdto = new ComputerDto(c);
		return response(cdto);
	}

	@Override
	@RequestMapping(value = "/computer/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<ComputerDto> getComputerById(@PathVariable("id") long id) {
		Computer c = service.getComputerById(id);
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

		if (service.existsComputer(name)) {
			pbs.add(new Problem(Fields.NAME.toString(), "A computer with such a name already exists"));
		} else if ((companyName != null) && !service.existsCompany(companyName)) {
			pbs.add(new Problem(Fields.COMPANY.toString(), "No company with such a name exists."));
		} else {
			String intro = cdto.getIntro();
			String outro = cdto.getOutro();
			Company cy = (companyName != null && !companyName.equals("")) ? service.getCompanyByName(companyName) : null;
			pbs = service.createComputer(name, intro, outro, cy);
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
		Computer ori = service.getComputerById(cdto.getId());
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problem pb = null;

		String name = cdto.getName();
		String companyName = cdto.getCompany();

		// If new name is already taken
		if (!name.equals(ori.getName()) && service.existsComputer(name)) {
			pb = new Problem(Fields.NAME.toString(), "A computer with such a name already exists");
		} else if ((companyName != null) && !service.existsCompany(companyName)) {
			// If the company does not exists
			pb = new Problem(Fields.COMPANY.toString(), "No company with such a name exists.");
		} else {
			String intro = cdto.getIntro();
			String outro = cdto.getOutro();
			Company cy = (companyName != null) ? service.getCompanyByName(companyName) : null;
			Computer c = new Computer(cdto.getId(), name, intro, outro, cy);
			service.updateComputer(c);
			status = HttpStatus.CREATED;
		}

		return new ResponseEntity<Problem>(pb, status);
	}

	@Override
	@RequestMapping(value = "/computer/del/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteComputer(@PathVariable("id") long id) {
		LOG.info("REST DELETE COMPUTER");
		Computer c = null;
		c = service.getComputerById(id);

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String pb = null;

		if (c == null) {
			pb = "No such Computer exists!";
		} else {
			service.deleteComputer(id);
			status = HttpStatus.OK;
		}

		return new ResponseEntity<String>(pb, status);
	}

	@Override
	@RequestMapping(value = "/company/del/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCompany(@PathVariable("id") long id) {
		LOG.info("REST DELETE COMPANY");
		Company c = null;
		c = service.getCompanyById(id);

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String pb = null;

		if (c == null) {
			pb = "No such Company exists!";
		} else {
			service.deleteCompany(id);
			status = HttpStatus.OK;
		}

		return new ResponseEntity<String>(pb, status);
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
