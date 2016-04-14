package com.excilys.formation.computerdb.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.Page;
import com.excilys.formation.computerdb.pagination.SearchPage;
import com.excilys.formation.computerdb.pagination.SortedPage;
import com.excilys.formation.computerdb.persistence.impl.CompanyDaoImpl;
import com.excilys.formation.computerdb.persistence.impl.ComputerDaoImpl;
import com.excilys.formation.computerdb.persistence.impl.ConnectionFactoryImpl;
import com.excilys.formation.computerdb.service.ComputerDatabaseService;
import com.excilys.formation.computerdb.thread.ThreadLocalConnection;
import com.excilys.formation.computerdb.validators.ComputerValidator;

@Service
public class ComputerDatabaseServiceImpl implements ComputerDatabaseService {
	
	@Autowired
	private ComputerDaoImpl computerDaoImpl;
	
	@Autowired
	private CompanyDaoImpl companyDAOImpl;
	
	@Autowired
	private ComputerValidator cval;
	
	@Autowired
	private ConnectionFactoryImpl connectionFactory;
	private ThreadLocalConnection threadLocalConnection;
	
	
	protected final Logger logger = LoggerFactory.getLogger(ComputerDatabaseServiceImpl.class);

	private ComputerDatabaseServiceImpl() {
		threadLocalConnection = ThreadLocalConnection.INSTANCE;
	}

	@Override
	public boolean alreadyExists(String name) {
		return this.computerDaoImpl.exists(name);
	}

	@Override
	public int getNbCompanies() {
		return companyDAOImpl.getNbEntries();
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
		return companyDAOImpl.getCompanyById(id);
	}

	@Override
	public Company getCompanyByName(String name) {
		return companyDAOImpl.getCompanyByName(name);
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
	public Page<Company> getAllCompanies() {
		List<Company> list = companyDAOImpl.getAll();
		int len = list.size();
		return new Page<Company>(list, 1, 1, len, len);
	}

	@Override
	public Page<Computer> getAllComputers() {
		List<Computer> list = computerDaoImpl.getAll();
		int len = list.size();
		return new Page<Computer>(list, 1, 1, len, len);
	}

	@Override
	public Page<Company> getCompanyPage(int pageNumber, Page<Company> p) {
		int nbEntries = computerDaoImpl.getNbEntries();
		List<Company> list = companyDAOImpl.getFromTo(pageNumber * p.getObjectsPerPages(), p.getObjectsPerPages());
		p.setPage(list);
		p.setCurrentPageNumber(pageNumber);
		p.setNbEntries(nbEntries);
		return p;
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
	public List<Problem> createComputer(Computer c) {
		List<Problem> listErrors = new ArrayList<>();

		computerDaoImpl.createComputer(c);
		return listErrors;
	}

	@Override
	public List<Problem> createComputer(String name, String intro, String outro, Company comp) {
		List<Problem> listErrors = null;
		listErrors = cval.validateComputer(name, intro, outro);
		System.out.println("listErrors = " + listErrors);
		if (listErrors == null) {
			Computer c = null;
			c = new Computer.Builder().name(name).intro(intro).outro(outro).company(comp).build();
			System.out.println("c = " + c);
			computerDaoImpl.createComputer(c);
		}

		return listErrors;
	}

	@Override
	public List<Problem> createComputer(long id, String name, String intro, String outro, Company comp) {
		List<Problem> listErrors = cval.validateComputer(name, intro, outro);

		if (listErrors == null) {
			Computer c = null;
			c = new Computer.Builder().id(id).name(name).intro(intro).outro(outro).company(comp).build();
			computerDaoImpl.createComputer(c);
		}

		return listErrors;
	}

	@Override
	public List<Problem> updateComputer(Computer computer, String oldName) {
		List<Problem> listErrors = cval.validateNewComputer(computer, oldName);

		if (listErrors == null) {
			this.computerDaoImpl.updateComputer(computer);
		}

		return listErrors;
	}

	@Override
	public void deleteComputer(Long id) {
		computerDaoImpl.deleteComputer(id);
	}

	@Override
	public void deleteComputer(String name) {
		computerDaoImpl.deleteComputer(name);
	}

	@Override
	public void deleteComputers(long[] listId) {
		Connection connection = null;
		logger.info("Starting mass computer deletion...");
		try {
			connection = connectionFactory.getTransaction();
			threadLocalConnection.set(connection);
			computerDaoImpl.deleteComputers(listId);

			connectionFactory.commitTransaction(connection);
		} catch (SQLException e) {
			logger.error("Couldn't commit the changes!");
			logger.error(e.getMessage());
			logger.error("Rolling back now...");
			connectionFactory.rollbackTransaction(connection);
		} finally {
			try {
				threadLocalConnection.remove();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
		logger.info("Mass deletion done.");
	}

	@Override
	public void deleteCompany(Company c) {
		Connection connection = null;
		logger.info("Starting company \"" + c.getName() + "\" deletion and all the related computers...");
		if (c != null) {
			long id = c.getId();
			try {
				connection = connectionFactory.getTransaction();
				threadLocalConnection.set(connection);

				computerDaoImpl.deleteComputersWhereCompanyIdEquals(id);
				companyDAOImpl.deleteCompany(id, connection);

				connection.commit();
			} catch (SQLException e) {
				logger.error("Couldn't commit the changes!");
				logger.error(e.getMessage());
				logger.error("Rolling back now...");
				connectionFactory.rollbackTransaction(connection);
			} finally {
				try {
					threadLocalConnection.remove();
					connection.close();
				} catch (SQLException e) {
					logger.error(e.getMessage());
				}
			}
		}
		logger.info("Deletion of company \"" + c.getName() + "\" its related computers done.");
	}

}
