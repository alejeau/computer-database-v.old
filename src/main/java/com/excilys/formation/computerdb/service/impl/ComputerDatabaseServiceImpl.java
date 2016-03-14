package com.excilys.formation.computerdb.service.impl;

import java.util.List;

import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.persistence.impl.CompanyDAOImpl;
import com.excilys.formation.computerdb.persistence.impl.ComputerDAOImpl;
import com.excilys.formation.computerdb.service.ComputerDatabaseService;

public enum ComputerDatabaseServiceImpl implements ComputerDatabaseService{
	INSTANCE;
	
	private ComputerDAOImpl computerDAOImpl;
	private CompanyDAOImpl companyDAOImpl;
	
	private ComputerDatabaseServiceImpl() {
		computerDAOImpl = ComputerDAOImpl.INSTANCE;
		companyDAOImpl 	= CompanyDAOImpl.INSTANCE;
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
	public List<Computer> getComputersFromTo(int from, int nb) {
		return computerDAOImpl.getFromTo(from, nb);
	}

	@Override
	public void createComputer(Computer c) {
		computerDAOImpl.createComputer(c);
	}

	@Override
	public void updateComputer(Computer c) {
		computerDAOImpl.updateComputer(c);
	}

	@Override
	public void deleteComputer(Long id) {
		computerDAOImpl.deleteComputer(id);
	}

	@Override
	public void deleteComputer(String name) {
		computerDAOImpl.deleteComputer(name);
	}

}
