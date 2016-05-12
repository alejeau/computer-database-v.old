package com.excilys.formation.computerdb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.computerdb.constants.Fields;
import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.model.pagination.Page;
import com.excilys.formation.computerdb.model.pagination.SearchPage;
import com.excilys.formation.computerdb.model.pagination.SortedPage;
import com.excilys.formation.computerdb.persistence.impl.CompanyDaoImpl;
import com.excilys.formation.computerdb.persistence.impl.ComputerDaoImpl;
import com.excilys.formation.computerdb.service.ComputerDatabaseService;
import com.excilys.formation.computerdb.validators.ComputerValidator;

@Service
public class ComputerDatabaseServiceImpl implements ComputerDatabaseService {

	@Autowired
	private ComputerDaoImpl computerDaoImpl;

	@Autowired
	private CompanyDaoImpl companyDaoImpl;

	protected final Logger logger = LoggerFactory.getLogger(ComputerDatabaseServiceImpl.class);

	public ComputerDatabaseServiceImpl() {
	}
	
	@Override
	public boolean existsCompany(String name) {
		return this.companyDaoImpl.exists(name);
	}
	
	@Override
	public boolean existsCompany(Company c) {
		return this.companyDaoImpl.exists(c);
	}
	
	@Override
	public boolean existsComputer(String name) {
		return this.computerDaoImpl.exists(name);
	}
	
	@Override
	public boolean existsComputer(Computer c) {
		return this.computerDaoImpl.exists(c);
	}

	@Override
	public int getNbCompanies() {
		return companyDaoImpl.getNbEntries();
	}

	@Override
	public int getNbComputers() {
		return computerDaoImpl.getNbEntries();
	}

	@Override
	public int getNbComputersNamed(String search) {
		int nb = computerDaoImpl.getNbEntriesNamed(search);
		return nb;
	}

	@Override
	public Company getCompanyById(Long id) {
		return companyDaoImpl.getCompanyById(id);
	}

	@Override
	public Company getCompanyByName(String name) {
		return companyDaoImpl.getCompanyByName(name);
	}

	@Override
	public Computer getComputerById(Long id) {
		return computerDaoImpl.getComputerById(id);
	}

	@Override
	public Computer getComputerByName(String name) {
		return computerDaoImpl.getComputerByName(name);
	}

	@Override
	public List<Company> listAllCompanies() {
		List<Company> list = companyDaoImpl.getAll();
		return list;
	}

	@Override
	public Page<Company> getAllCompanies() {
		List<Company> list = companyDaoImpl.getAll();
		int len = list.size();
		return new Page<Company>(list, 1, 1, len, len);
	}

	@Override
	public List<Computer> listAllComputers() {
		List<Computer> list = computerDaoImpl.getAll();
		return list;
	}

	@Override
	public Page<Computer> getAllComputers() {
		List<Computer> list = computerDaoImpl.getAll();
		int len = list.size();
		return new Page<Computer>(list, 1, 1, len, len);
	}

	@Override
	public List<Company> getCompanyList(int offset, int limit) {
		List<Company> list = companyDaoImpl.getFromTo(offset, limit);
		return list;
	}

	@Override
	public Page<Company> getCompanyPage(int pageNumber, Page<Company> p) {
		int nbEntries = computerDaoImpl.getNbEntries();
		List<Company> list = companyDaoImpl.getFromTo(pageNumber * p.getObjectsPerPages(), p.getObjectsPerPages());
		p.setPage(list);
		p.setCurrentPageNumber(pageNumber);
		p.setNbEntries(nbEntries);
		return p;
	}

	@Override
	public List<Computer> getComputerList(int offset, int limit) {
		List<Computer> list = computerDaoImpl.getFromTo(offset, limit);
		return list;
	}

	@Override
	public List<Computer> getComputerSortedList(int offset, int limit, Fields field, boolean ascending) {
		List<Computer> list = computerDaoImpl.getFromToSortedBy(offset, limit, field, ascending);
		return list;
	}

	@Override
	public SortedPage<Computer> getComputerSortedPage(int pageNumber, SortedPage<Computer> sp) {
		int nbEntries = computerDaoImpl.getNbEntries();
		List<Computer> list = computerDaoImpl.getFromToSortedBy(pageNumber * sp.getObjectsPerPages(),
				sp.getObjectsPerPages(), sp.getField(), sp.isAscending());
		sp.setCurrentPageNumber(pageNumber);
		sp.setNbEntries(nbEntries);
		sp.setPage(list);

		return sp;
	}

	@Override
	public List<Computer> getComputerSearchList(String keyword, int offset, int limit) {
		List<Computer> list = computerDaoImpl.getNamedFromToSortedBy(keyword,
				offset, limit, Fields.NONE, true);
		
		return list;
	}

	@Override
	public List<Computer> getComputerSearchList(String keyword, int offset, int limit, Fields field, boolean ascending) {
		List<Computer> list = computerDaoImpl.getNamedFromToSortedBy(keyword, offset, limit, field, ascending);
		
		return list;
	}

	@Override
	public SearchPage<Computer> getComputerSearchPage(int pageNumber, SearchPage<Computer> sp) {
		int nbEntries = computerDaoImpl.getNbEntriesNamed(sp.getSearch());
		List<Computer> list = computerDaoImpl.getNamedFromToSortedBy(sp.getSearch(),
				pageNumber * sp.getObjectsPerPages(), sp.getObjectsPerPages(), sp.getField(), sp.isAscending());
		sp.setCurrentPageNumber(pageNumber);
		sp.setNbEntries(nbEntries);
		sp.setPage(list);

		return sp;
	}

	@Override
	@Transactional
	public List<Problem> createComputer(Computer c) {
		List<Problem> listErrors = new ArrayList<>();

		computerDaoImpl.createComputer(c);
		return listErrors;
	}

	@Override
	@Transactional
	public List<Problem> createComputer(String name, String intro, String outro, Company comp) {
		List<Problem> listErrors = null;
		listErrors = ComputerValidator.validateComputer(name, intro, outro);

		if (listErrors == null) {
			Computer c = null;
			c = new Computer.Builder().name(name).intro(intro).outro(outro).company(comp).build();
			computerDaoImpl.createComputer(c);
		}

		return listErrors;
	}

	@Override
	@Transactional
	public List<Problem> createComputer(long id, String name, String intro, String outro, Company comp) {
		List<Problem> listErrors = ComputerValidator.validateComputer(name, intro, outro);

		if (listErrors == null) {
			Computer c = null;
			c = new Computer.Builder().id(id).name(name).intro(intro).outro(outro).company(comp).build();
			computerDaoImpl.createComputer(c);
		}

		return listErrors;
	}

	@Override
	@Transactional
	public void updateComputer(Computer computer) {
			this.computerDaoImpl.updateComputer(computer);
	}

	@Override
	@Transactional
	public List<Problem> updateComputer(Computer computer, String oldName) {
		List<Problem> listErrors = ComputerValidator.validateNewComputer(computer, oldName);

		if (listErrors == null) {
			this.computerDaoImpl.updateComputer(computer);
		}

		return listErrors;
	}

	@Override
	@Transactional
	public void deleteComputer(Computer c) {
		computerDaoImpl.createComputer(c);
	}

	@Override
	@Transactional
	public void deleteComputer(Long id) {
		computerDaoImpl.deleteComputer(id);
	}

	@Override
	@Transactional
	public void deleteComputer(String name) {
		computerDaoImpl.deleteComputer(name);
	}

	@Override
	@Transactional
	public void deleteComputers(long[] listId) {
		logger.debug("Starting mass computer deletion...");

		try {
			computerDaoImpl.deleteComputers(listId);
		} catch (Exception e) {
			logger.error("Couldn't commit the changes!");
			logger.error("Message: " + e.getMessage());
			throw new RuntimeException("Couldn't commit the changes!");
		}

		logger.debug("Mass deletion completed.");
	}

	@Override
	@Transactional
	public void deleteCompany(long id) {
		logger.debug("Starting company \"" + id + "\" deletion and all the related computers...");
		try {
			computerDaoImpl.deleteComputersWhereCompanyIdEquals(id);
			companyDaoImpl.deleteCompany(id);
		} catch (Exception e) {
			logger.error("Couldn't commit the changes!");
			logger.error(e.getMessage());
			throw new RuntimeException("Couldn't commit the changes!");
		}

		logger.debug("Deletion of company \"" + id + "\" its related computers done.");
	}

	@Override
	@Transactional
	public void deleteCompany(Company c) {
		logger.debug("Starting company \"" + c.getName() + "\" deletion and all the related computers...");

		if (c != null) {
			long id = c.getId();
			try {
				computerDaoImpl.deleteComputersWhereCompanyIdEquals(id);
				companyDaoImpl.deleteCompany(id);
			} catch (Exception e) {
				logger.error("Couldn't commit the changes!");
				logger.error(e.getMessage());
				throw new RuntimeException("Couldn't commit the changes!");
			}
		}

		logger.debug("Deletion of company \"" + c.getName() + "\" its related computers done.");
	}

}
