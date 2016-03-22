package com.excilys.formation.computerdb.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdb.errors.Problem;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.persistence.impl.CompanyDAOImpl;
import com.excilys.formation.computerdb.persistence.impl.ComputerDAOImpl;
import com.excilys.formation.computerdb.service.ComputerDatabaseService;
import com.excilys.formation.computerdb.validators.ComputerValidator;

public enum ComputerDatabaseServiceImpl implements ComputerDatabaseService {
	INSTANCE;

	private ComputerDAOImpl computerDAOImpl;
	private CompanyDAOImpl companyDAOImpl;

	private ComputerDatabaseServiceImpl() {
		computerDAOImpl = ComputerDAOImpl.INSTANCE;
		companyDAOImpl 	= CompanyDAOImpl.INSTANCE;
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
	public List<Computer> getComputersFromTo(int from, int nb) {
		return computerDAOImpl.getFromTo(from, nb);
	}

	@Override
	public List<Computer> getComputersNamedFromTo(String search, int from, int to) {
		return computerDAOImpl.getNamedFromTo(search, from, to);
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

}
