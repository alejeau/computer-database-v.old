package com.excilys.formation.computerdb.persistence.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.computerdb.constants.Fields;
import com.excilys.formation.computerdb.constants.Time;
import com.excilys.formation.computerdb.exceptions.ComputerCreationException;
import com.excilys.formation.computerdb.mapper.model.ComputerMapper;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.persistence.ComputerDao;

@Repository
public class ComputerDaoImpl implements ComputerDao {
	protected final Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

	@Autowired
	protected ComputerMapper compMap;

	@Autowired
	protected CompanyDaoImpl companyDAOImpl;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	protected List<Computer> list = null;

	protected ComputerDaoImpl() {
	}

	@Override
	public boolean exists(String name) {
		List<Computer> tmp = null;
		Object[] args = new Object[] { name };

		String query = "SELECT * FROM computer WHERE name=? ORDER BY name;";
		logger.debug(query);

		// Executing the query
		tmp = jdbcTemplate.query(query, args, new ComputerMapper());

		// Getting the company for each computer
		if (tmp != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean exists(Computer computer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Computer> getNamedFromTo(String name, int offset, int limit) {
		return getNamedFromToSortedBy(name, offset, limit, Fields.NONE, true);
	}

	@Override
	public List<Computer> getNamedFromToSortedBy(String name, int offset, int limit, Fields field, boolean ascending) {
		List<Computer> tmp = null;
		list = new ArrayList<>();
		Object[] args;

		// Creating the query
		String query = "SELECT * FROM computer WHERE name LIKE ? OR company_id IN (SELECT id FROM company WHERE name LIKE ?)";
		if (field != Fields.NONE) {
			String order = (ascending) ? "ASC" : "DESC";
			if (field == Fields.COMPANY) {
				query = "SELECT * FROM computer LEFT JOIN company on computer.company_id=company.id WHERE computer.name LIKE ? OR company_id IN (SELECT id FROM company WHERE name LIKE ?)";
			}
			query += " ORDER BY " + field.toString() + " " + order;

			args = new Object[4];
			args[0] = "%" + name + "%";
			args[1] = "%" + name + "%";
			args[2] = offset;
			args[3] = limit;
		} else {
			args = new Object[2];
			args[2] = offset;
			args[3] = limit;
		}
		query += " LIMIT ?, ?;";

		logger.debug(query);

		// Executing the query
		tmp = jdbcTemplate.query(query, args, new ComputerMapper());

		// Getting the company for each computer
		for (Computer c : tmp) {
			long cid = -1L;
			Company company = null;
			cid = c.getCompany().getId();
			company = this.companyDAOImpl.getCompanyById(cid);
			c.setCompany(company);
			list.add(c);
		}

		return list;
	}

	@Override
	public List<Computer> getFromTo(int offset, int limit) {
		return getFromToSortedBy(offset, limit, Fields.NONE, true);
	}

	@Override
	public List<Computer> getFromToSortedBy(int offset, int limit, Fields field, boolean ascending) {
		List<Computer> tmp = null;
		list = new ArrayList<>();
		Object[] args = new Object[] { offset, limit };

		String query = "SELECT * FROM computer";
		if (field != Fields.NONE) {
			String order = (ascending) ? "ASC" : "DESC";
			if (field == Fields.COMPANY) {
				query += " LEFT JOIN company on computer.company_id=company.id";
			}
			query += " ORDER BY " + field.toString() + " " + order;
		}
		query += " LIMIT ?, ?;";
		System.out.println(query);
		logger.debug(query);

		// Executing the query
		tmp = jdbcTemplate.query(query, args, new ComputerMapper());

		// Getting the company for each computer
		for (Computer c : tmp) {
			long cid = -1L;
			Company company = null;
			cid = c.getCompany().getId();
			company = this.companyDAOImpl.getCompanyById(cid);
			c.setCompany(company);
			list.add(c);
		}

		return list;
	}

	@Override
	public int getNbEntries() {
		int nbEntries = 0;
		String query = "SELECT COUNT(*) as nb_computers FROM computer";
		nbEntries = jdbcTemplate.queryForObject(query, Integer.class);
		return nbEntries;
	}

	@Override
	public int getNbEntriesNamed(String name) {
		Object[] args = null;
		int nbEntries = 0;
		String query = "SELECT count(*) as nb_computers FROM computer where name LIKE ? OR company_id IN (SELECT id FROM company where name LIKE ?)";

		logger.debug(query);

		String arg = "%" + name + "%";
		args = new Object[] { arg, arg };
		// Executing the query
		nbEntries = jdbcTemplate.queryForObject(query, args, Integer.class);

		return nbEntries;
	}

	@Override
	public List<Computer> getAll() {
		return getAllSortedBy(Fields.NONE, true);
	}

	@Override
	public List<Computer> getAllSortedBy(Fields field, boolean ascending) {
		List<Computer> tmp = null;
		list = new ArrayList<>();

		String query = "SELECT * FROM computer";
		if (field != Fields.NONE) {
			String order = (ascending) ? "ASC" : "DESC";
			if (field == Fields.COMPANY) {
				query += " LEFT JOIN company on computer.company_id=company.id";
			}
			query += " ORDER BY " + field.toString() + " " + order + ";";
		}
		logger.debug(query);

		// Executing the query
		tmp = jdbcTemplate.query(query, new ComputerMapper());

		// Getting the company for each computer
		for (Computer c : tmp) {
			long cid = -1L;
			Company company = null;
			cid = c.getCompany().getId();
			company = this.companyDAOImpl.getCompanyById(cid);
			c.setCompany(company);
			list.add(c);
		}

		return list;
	}

	@Override
	public Computer getComputerById(long id) {
		List<Computer> tmp = null;
		Object[] args = new Object[] { id };

		Computer computer = null;
		String query = "SELECT * FROM computer WHERE id = ?";
		logger.debug(query);

		// Executing the query
		tmp = jdbcTemplate.query(query, args, new ComputerMapper());

		// Getting the company for each computer
		if (tmp != null) {
			Company company = null;
			long cid = -1L;
			computer = tmp.get(0);

			cid = computer.getCompany().getId();
			company = this.companyDAOImpl.getCompanyById(cid);
			computer.setCompany(company);
		}

		return computer;
	}

	public Computer getComputerByName(String name) {
		List<Computer> tmp = null;
		Object[] args = new Object[] { name };

		Computer computer = null;
		String query = "SELECT * FROM computer WHERE name = ?";
		logger.debug(query);

		// Executing the query
		tmp = jdbcTemplate.query(query, args, new ComputerMapper());

		// Getting the company for each computer
		if ((tmp != null) && (!tmp.isEmpty())) {
			Company company = null;
			long cid = -1L;
			computer = tmp.get(0);

			cid = computer.getCompany().getId();
			company = this.companyDAOImpl.getCompanyById(cid);
			computer.setCompany(company);
			list = new ArrayList<>();
		}

		return computer;
	}

	/**
	 * Tries to insert a computer in the database
	 * 
	 * @param computer
	 *            the computer to insert
	 * @return
	 * @throws ComputerCreationException
	 */
	@Override
	public int createComputer(Computer computer) {
		Object[] args = null;
		int nbRow = 0;

		Computer tmp = null;
		tmp = getComputerByName(computer.getName());

		if (tmp != null) {
			nbRow = -1;
		} else {
			args = new Object[3];
			String query = "INSERT INTO computer (name, introduced, discontinued) VALUES (?, ?, ?)";
			String intro = (computer.getIntro() != null) ? (computer.getIntro().toString()) : Time.TIMESTAMP_ZERO;
			String outro = (computer.getOutro() != null) ? (computer.getOutro().toString()) : Time.TIMESTAMP_ZERO;

			boolean hasACompany = computer.hasACompany();
			if (hasACompany) {
				query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
				args = new Object[4];
				args[3] = computer.getCompany().getId();
			}
			args[0] = computer.getName();
			args[1] = intro;
			args[2] = outro;

			logger.debug(query);

			jdbcTemplate.update(query, args);
		}

		return nbRow;
	}

	@Override
	public void updateComputer(Computer computer) {
		String intro = (computer.getIntro() != null) ? (computer.getIntro().toString()) : Time.TIMESTAMP_ZERO;
		String outro = (computer.getOutro() != null) ? (computer.getOutro().toString()) : Time.TIMESTAMP_ZERO;
		Object[] args = new Object[] { computer.getName(), intro, outro, computer.getId() };

		String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?";
		boolean hasACompany = computer.hasACompany();
		if (hasACompany) {
			query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
			args = new Object[] { computer.getName(), intro, outro, computer.getCompany().getId(), computer.getId() };
		}
		logger.debug(query);

		jdbcTemplate.update(query, args);
	}

	@Override
	public void deleteComputer(long id) {
		Object[] args = new Object[] { id };

		String query = "DELETE FROM computer WHERE id = ?";
		logger.debug(query);

		jdbcTemplate.update(query, args);

	}

	@Override
	public void deleteComputer(String name) {
		Object[] args = new Object[] { name };

		String query = "DELETE FROM computer WHERE name = ?";
		logger.debug(query);

		jdbcTemplate.update(query, args);
	}

	@Override
	@Transactional
	public void deleteComputers(long[] listId) throws SQLException {
		Object[] args = new Object[1];

		String query = "DELETE FROM computer WHERE id = ?";
		logger.debug(query);

		for (long l : listId) {
			args[0] = l;
			jdbcTemplate.update(query, args);	
		}
	}

	@Override
	@Transactional
	public void deleteComputersWhereCompanyIdEquals(long id) throws SQLException {
		Object[] args = new Object[] { id };

		String query = "DELETE FROM computer WHERE company_id = ?";
		logger.debug(query);

		jdbcTemplate.update(query, args);
	}
}