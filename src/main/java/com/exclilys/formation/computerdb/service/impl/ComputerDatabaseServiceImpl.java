package com.exclilys.formation.computerdb.service.impl;

import java.util.List;

import com.exclilys.formation.computerdb.model.Company;
import com.exclilys.formation.computerdb.model.Computer;
import com.exclilys.formation.computerdb.persistence.CompanyDAO;
import com.exclilys.formation.computerdb.persistence.ComputerDAO;
import com.exclilys.formation.computerdb.persistence.ConnectionFactory;
import com.exclilys.formation.computerdb.persistence.impl.CompanyDAOImpl;
import com.exclilys.formation.computerdb.persistence.impl.ComputerDAOImpl;
import com.exclilys.formation.computerdb.service.ComputerDatabaseService;

public enum ComputerDatabaseServiceImpl implements ComputerDatabaseService{
	INSTANCE;
	
	private ConnectionFactory daoFactory;
	private ComputerDAO computerDao;
	private CompanyDAO companyDao;
	
	private ComputerDatabaseServiceImpl() {
		daoFactory = ConnectionFactory.getInstance();
		computerDao = new ComputerDAOImpl(daoFactory);
		companyDao 	= new CompanyDAOImpl(daoFactory);
	}
	

	@Override
	public List<Computer> getAllComputers() {
		return computerDao.getAll();
	}


	@Override
	public List<Company> getAllCompanies() {
		return companyDao.getAll();
	}

	@Override
	public void deleteComputer(Long id) {
		computerDao.deleteComputer(id);
	}

	@Override
	public List<Computer> getComputersFromTo(int from, int nb) {
		return computerDao.getFromTo(from, nb);
	}


	@Override
	public int getNbComputers() {
		return computerDao.getNbEntries();
	}


	@Override
	public Computer getComputerById(Long id) {
		return computerDao.getComputerById(id);
	}


	@Override
	public void createComputer(Computer c) {
		computerDao.createComputer(c);
	}


	@Override
	public void updateComputer(Computer c) {
		computerDao.updateComputer(c);
	}


	@Override
	public int getNbCompanies() {
		return companyDao.getNbEntries();
	}

	@Override
	public List<Company> getCompaniesFromTo(int from, int nb) {
		return companyDao.getFromTo(from, nb);
	}

}
