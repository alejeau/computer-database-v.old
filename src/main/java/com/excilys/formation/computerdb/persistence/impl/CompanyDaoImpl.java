package com.excilys.formation.computerdb.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.formation.computerdb.mapper.model.CompanyMapper;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.persistence.CompanyDao;

@Repository
public class CompanyDaoImpl implements CompanyDao {
	
	@PersistenceContext
	protected EntityManager entityManager;

	protected final Logger LOG = LoggerFactory.getLogger(CompanyDaoImpl.class);

	protected CompanyDaoImpl() {
	}

	@Override
	public int getNbEntries() {
		final String QUERY_TXT = "SELECT COUNT(*) as nb_companys FROM company";
		Query query = entityManager.createQuery(QUERY_TXT);
		int nbEntries = 0;
		nbEntries = (int) query.getSingleResult();
		return nbEntries;
	}

	@Override
	public List<Company> getFromTo(int offset, int limit) {
		List<Company> list = null;
		list = new ArrayList<>();
		String query = "SELECT * FROM company ORDER BY name LIMIT ?, ? ";

		Object args[] = new Object[2];
		args[0] = offset;
		args[1] = limit;
//		list = jdbcTemplate.query(query, args, new CompanyMapper());

		return list;
	}

	@Override
	public List<Company> getAll() {
		List<Company> list = null;
		list = new ArrayList<>();
		String query = "SELECT * FROM company ORDER BY name";
//		list = jdbcTemplate.query(query, new CompanyMapper());

		return list;
	}

	@Override
	public Company getCompanyById(long id) {
		if ((id == -1l) || (id == 0L)) {
			return null;
		}

		Company company = null;
		String query = "SELECT * FROM company WHERE id = ?";

		Object args[] = new Object[1];
		args[0] = id;
//		company = jdbcTemplate.queryForObject(query, args, new CompanyMapper());

		return company;
	}

	@Override
	public Company getCompanyByName(String name) {
		Company company = null;
		String query = "SELECT * FROM company WHERE name = ?";

		Object args[] = new Object[] { name };
		try {
//			company = jdbcTemplate.queryForObject(query, args, new CompanyMapper());
		} catch (Exception e) {
			String error = "An error occured while deleting the company. Try to check the company name.";
			LOG.error(error);
			// throw new RuntimeException(error + "\n" + e.getMessage());
		}

		return company;
	}

	@Override
	public void deleteCompany(long id) {
		LOG.info("Company deletion: \"DELETE FROM company WHERE id = " + id + "\"");
		String query = "DELETE FROM company WHERE id = ?";

		Object args[] = new Object[1];
		args[0] = id;
		try {
//			jdbcTemplate.update(query, args);
		} catch (Exception e) {
			String error = "An error occured while deleting the company. Try to check the company name.";
			LOG.error(error);
			throw new RuntimeException(error + "\n" + e.getMessage());
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
}