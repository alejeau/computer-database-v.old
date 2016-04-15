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

import com.excilys.formation.computerdb.mapper.model.CompanyMapper;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.persistence.CompanyDao;

@Repository
public class CompanyDaoImpl implements CompanyDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	protected final Logger LOG = LoggerFactory.getLogger(CompanyDaoImpl.class);
	protected List<Company> list = null;

	protected CompanyDaoImpl() {
		list = new ArrayList<Company>();
	}

	@Override
	public List<Company> getFromTo(int offset, int limit) {
		list = new ArrayList<>();
		String query = "SELECT * FROM company ORDER BY name LIMIT ?, ? ";

		Object args[] = new Object[2];
		args[0] = offset;
		args[1] = limit;
		list = jdbcTemplate.query(query, args, new CompanyMapper());

		return list;
	}

	@Override
	public int getNbEntries() {
		int nbEntries = 0;
		String query = "SELECT COUNT(*) as nb_companys FROM company";
		nbEntries = jdbcTemplate.queryForObject(query, Integer.class);
		return nbEntries;
	}

	@Override
	public List<Company> getAll() {
		list = new ArrayList<>();
		String query = "SELECT * FROM company ORDER BY name";
		list = jdbcTemplate.query(query, new CompanyMapper());

		return list;
	}

	public Company getCompanyById(long id) {
		if ((id == -1l) || (id == 0L)) {
			return null;
		}

		Company company = null;
		String query = "SELECT * FROM company WHERE id = ?";

		Object args[] = new Object[1];
		args[0] = id;
		company = jdbcTemplate.queryForObject(query, args, new CompanyMapper());

		return company;
	}

	public Company getCompanyByName(String name) {
		Company company = null;
		String query = "SELECT * FROM company WHERE name = ?";

		Object args[] = new Object[1];
		args[0] = name;
		company = jdbcTemplate.queryForObject(query, args, new CompanyMapper());

		return company;
	}

	@Override
	@Transactional
	public void deleteCompany(long id) throws SQLException {
		LOG.info("Company deletion: \"DELETE FROM company WHERE id = " + id + "\"");
		String query = "DELETE FROM company WHERE id = ?";

		Object args[] = new Object[1];
		args[0] = id;
		jdbcTemplate.update(query, args);
	}
}