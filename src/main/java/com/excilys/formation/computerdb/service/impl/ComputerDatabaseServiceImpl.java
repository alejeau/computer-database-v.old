package com.excilys.formation.computerdb.service.impl;

import java.util.List;

import com.excilys.formation.computerdb.dto.ComputerDTO;
import com.excilys.formation.computerdb.exceptions.ComputerCreationException;
import com.excilys.formation.computerdb.mapper.ComputerDTOMapper;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.persistence.impl.CompanyDAOImpl;
import com.excilys.formation.computerdb.persistence.impl.ComputerDAOImpl;
import com.excilys.formation.computerdb.service.ComputerDatabaseService;

public enum ComputerDatabaseServiceImpl implements ComputerDatabaseService {
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
	public Computer getComputerById(Long id) throws ComputerCreationException {
		return computerDAOImpl.getComputerById(id);
	}

	@Override
	public Computer getComputerByName(String name) throws ComputerCreationException {
		return computerDAOImpl.getComputerByName(name);
	}

	@Override
	public List<Company> getAllCompanies() {
		return companyDAOImpl.getAll();
	}

	@Override
	public List<Computer> getAllComputers() throws ComputerCreationException {
		return computerDAOImpl.getAll();
	}

	@Override
	public List<Company> getCompaniesFromTo(int from, int nb) {
		return companyDAOImpl.getFromTo(from, nb);
	}

	@Override
	public List<Computer> getComputersFromTo(int from, int nb) throws ComputerCreationException {
		return computerDAOImpl.getFromTo(from, nb);
	}

	@Override
	public List<Computer> getComputersNamedFromTo(String search, int from, int to) throws ComputerCreationException {
		return computerDAOImpl.getNamedFromTo(search, from, to);
	}

	@Override
	public void createComputer(Computer c) {
		computerDAOImpl.createComputer(c);
	}

	@Override
	public void createComputer(String name, String intro, String outro, Company comp) throws ComputerCreationException {
		Computer c = null;
		c = new Computer(name, intro, outro, comp);
		computerDAOImpl.createComputer(c);
	}

	@Override
	public void createComputer(long id, String name, String intro, String outro, Company comp) throws ComputerCreationException {
		Computer c = null;
		c = new Computer(id, name, intro, outro, comp);
		computerDAOImpl.createComputer(c);
	}

	@Override
	public void updateComputer(Computer computer) {
		this.computerDAOImpl.updateComputer(computer);

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
	public ComputerDTO computerToComputerDTO(Computer computer) {
		return ComputerDTOMapper.map(computer);
	}

	@Override
	public List<ComputerDTO> computersToComputersDTO(List<Computer> computers) {
		return ComputerDTOMapper.map(computers);
	}

}
