package com.excilys.formation.computerdb.ws.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.formation.computerdb.dto.model.ComputerDto;
import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.model.Company;

public interface RestManager {
	
	@RequestMapping(value = "/computers/total", method = RequestMethod.GET)
	public ResponseEntity<Integer> getNbComputers();
	
	@RequestMapping(value = "/companies/total", method = RequestMethod.GET)
	public ResponseEntity<Integer> getNbCompanies();

	@RequestMapping(value = "/computers/all", method = RequestMethod.GET)
	public ResponseEntity<List<ComputerDto>> listAllComputers();

	/**
	 * Retrieves a list of Computer by pages
	 */
	@RequestMapping(value = "/computers/page/{pageNumber}/{objectPerPage}", method = RequestMethod.GET)
	public ResponseEntity<List<ComputerDto>> listComputerPage(
			@PathVariable("pageNumber") int pageNumber,
			@PathVariable("objectPerPage") int objectPerPage);

	/**
	 * Retrieves a list of Computer by pages
	 */
	@RequestMapping(value = "/computers/page/{pageNumber}/{objectPerPage}/{field}/{ascending}", method = RequestMethod.GET)
	public ResponseEntity<List<ComputerDto>> listComputerSortedPage(
			@PathVariable("pageNumber") int pageNumber,
			@PathVariable("objectPerPage") int objectPerPage,
			@PathVariable("field") String stringField,
			@PathVariable("ascending") boolean ascending);

	@RequestMapping(value = "/companies/all", method = RequestMethod.GET)
	public ResponseEntity<List<Company>> listAllCompanies();

	/**
	 * Retrieves a list of Company by pages
	 */
	@RequestMapping(value = "/companies/page/{pageNumber}/{objectPerPage}", method = RequestMethod.GET)
	public ResponseEntity<List<Company>> listCompanyPage(
			@PathVariable("pageNumber") int pageNumber,
			@PathVariable("objectPerPage") int objectPerPage);

	/**
	 * Retrieves a List of ComputerDto containing the keyword either in the computer's name 
	 * or in the company name.
	 * 
	 * @param keyword the keyword
	 * @param pageNumber the page number
	 * @param objectPerPage the number of object per pages
	 * @return a sorted List<ComputerDto>
	 */
	@RequestMapping(value = "/computers/search/{keyword}/{pageNumber}/{objectPerPage}", method = RequestMethod.GET)
	public ResponseEntity<List<ComputerDto>> listCompanySearch(
			@PathVariable("keyword") String keyword,
			@PathVariable("pageNumber") int pageNumber,
			@PathVariable("objectPerPage") int objectPerPage);

	/**
	 * Retrieves a List of ComputerDto containing the keyword either in the computer's name 
	 * or in the company name, sorted by field.
	 * 
	 * @param keyword the keyword
	 * @param pageNumber the page number
	 * @param objectPerPage the number of object per pages
	 * @param stringField the sorting field
	 * @param ascending whether it's ascending or not 
	 * @return a sorted List<ComputerDto>
	 */
	@RequestMapping(value = "/computers/search/{keyword}/{pageNumber}/{objectPerPage}/{field}/{ascending}", method = RequestMethod.GET)
	public ResponseEntity<List<ComputerDto>> listCompanySortedSearch(
			@PathVariable("keyword") String keyword,
			@PathVariable("pageNumber") int pageNumber,
			@PathVariable("objectPerPage") int objectPerPage,
			@PathVariable("field") String stringField,
			@PathVariable("ascending") boolean ascending);


	/**
	 * Retrieves the infos of the desired computer
	 */
	@RequestMapping(value = "/company/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<Company> getCompanyByName(@PathVariable("name") String name);
	
	/**
	 * Retrieves the infos of the desired computer
	 */
	@RequestMapping(value = "/computer/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<ComputerDto> getComputerByName(@PathVariable("name") String name);

	/**
	 * Retrieves the infos of the desired computer
	 */
	@RequestMapping(value = "/computer/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<ComputerDto> getComputerById(@PathVariable("id") long id);

	/**
	 * Creates a computer
	 */
	@RequestMapping(value = "/computer/add", method = RequestMethod.POST)
	public ResponseEntity<List<Problem>> createComputer(@RequestBody ComputerDto cdto);

	/**
	 * Updates a given computer
	 */
	@RequestMapping(value = "/computer/update", method = RequestMethod.PUT)
	public ResponseEntity<Problem> updateComputer(@RequestBody ComputerDto cdto);

	/**
	 * Deletes a given computer
	 */
	@RequestMapping(value = "/computer/del/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteComputer(@PathVariable("id") long id);

	/**
	 * Deletes a given computer
	 */
	@RequestMapping(value = "/company/del/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCompany(@PathVariable("id") long id);
}
