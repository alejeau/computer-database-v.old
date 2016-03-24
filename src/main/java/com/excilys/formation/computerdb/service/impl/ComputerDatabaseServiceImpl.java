package com.excilys.formation.computerdb.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.persistence.Fields;
import com.excilys.formation.computerdb.persistence.impl.CompanyDAOImpl;
import com.excilys.formation.computerdb.persistence.impl.ComputerDAOImpl;
import com.excilys.formation.computerdb.persistence.impl.ConnectionFactoryImpl;
import com.excilys.formation.computerdb.service.ComputerDatabaseService;
import com.excilys.formation.computerdb.validators.ComputerValidator;

public enum ComputerDatabaseServiceImpl implements ComputerDatabaseService {
	INSTANCE;

	private ComputerDAOImpl computerDAOImpl;
	private CompanyDAOImpl companyDAOImpl;
	private ConnectionFactoryImpl connectionFactory;
	protected final Logger logger = LoggerFactory.getLogger(ComputerDatabaseServiceImpl.class);

	private ComputerDatabaseServiceImpl() {
		computerDAOImpl = ComputerDAOImpl.INSTANCE;
		companyDAOImpl 	= CompanyDAOImpl.INSTANCE;
		connectionFactory = ConnectionFactoryImpl.INSTANCE;
	}
	@Override
	public boolean alreadyExists(String name){
		return this.computerDAOImpl.exists(name);
	}

	@Override
	public int getNbCompanies() {
		return companyDAOImpl.getNbEntries();
	}

	@Override
	public int getNbComputers() {
		return computerDAOImpl.getNbEntries();
	}

	@Override
	public int getNbComputersNamed(String search) {
		int nb = computerDAOImpl.getNbEntriesNamed(search);
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
		return computerDAOImpl.getComputerById(id);
	}

	@Override
	public Computer getComputerByName(String name) {
		return computerDAOImpl.getComputerByName(name);
	}

	@Override
	public List<Company> getAllCompanies() {
		return companyDAOImpl.getAll();
	}

	@Override
	public List<Computer> getAllComputers() {
		return computerDAOImpl.getAll();
	}

	@Override
	public List<Company> getCompaniesFromTo(int from, int nb) {
		return companyDAOImpl.getFromTo(from, nb);
	}

	@Override
	public List<Computer> getComputersFromToSortedBy(int from, int nb, Fields field, boolean ascending) {
		return computerDAOImpl.getFromToSortedBy(from, nb, field, ascending);
	}

	@Override
	public List<Computer> getComputersNamedFromToSortedBy(String search, int from, int to, Fields field, boolean ascending) {
		return computerDAOImpl.getNamedFromToSortedBy(search, from, to, field, ascending);
	}

	@Override
	public List<Problem> createComputer(Computer c) {
		List<Problem> listErrors = new ArrayList<>();

		computerDAOImpl.createComputer(c);
		return listErrors;
	}

	@Override
	public List<Problem> createComputer(String name, String intro, String outro, Company comp) {
		List<Problem> listErrors = null;
		listErrors = ComputerValidator.validateComputer(name, intro, outro);
		System.out.println("listErrors = " + listErrors);
		if (listErrors == null){
			Computer c = null;
			c = new Computer.Builder()
					.name(name)
					.intro(intro)
					.outro(outro)
					.company(comp)
					.build();
			System.out.println("c = " + c);
			computerDAOImpl.createComputer(c);
		}

		return listErrors;
	}

	@Override
	public List<Problem> createComputer(long id, String name, String intro, String outro, Company comp) {
		List<Problem> listErrors = ComputerValidator.validateComputer(name, intro, outro);

		if (listErrors == null){
			Computer c = null;
			c = new Computer.Builder()
					.id(id)
					.name(name)
					.intro(intro)
					.outro(outro)
					.company(comp)
					.build();
			computerDAOImpl.createComputer(c);
		}

		return listErrors;
	}

	@Override
	public List<Problem> updateComputer(Computer computer, String oldName) {
		List<Problem> listErrors = ComputerValidator.validateNewComputer(computer, oldName);

		if (listErrors == null) {
			this.computerDAOImpl.updateComputer(computer);
		}

		return listErrors;
	}

	@Override
	public void deleteComputer(Long id) {
		computerDAOImpl.deleteComputer(id);
	}

	@Override
	public void deleteComputer(String name) {
		computerDAOImpl.deleteComputer(name);
	}

	@Override
	public void deleteComputers(long[] listId) {
		Connection connection = null;
		connection = connectionFactory.getConnection();
		logger.info("Starting mass computer deletion...");
		try {
			connection.setAutoCommit(false);
			
			computerDAOImpl.deleteComputers(listId, connection);
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error(e1.getMessage());
			}
			logger.error(e.getMessage());
		} finally {
			try {
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
		connection = connectionFactory.getConnection();
		logger.info("Starting company \"" + c.getName() + "\" deletion and all the related computers...");
		if (c != null) {
			long id = c.getId();
			try {
				connection.setAutoCommit(false);
				
				computerDAOImpl.deleteComputersWhereCompanyIdEquals(id, connection);
				companyDAOImpl.deleteCompany(id, connection);
				
				connection.commit();
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error(e1.getMessage());
				}
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error(e.getMessage());
				}
			}
		}
		logger.info("Deletion of company \"" + c.getName() + "\" its related computers done.");
	}
	
}
