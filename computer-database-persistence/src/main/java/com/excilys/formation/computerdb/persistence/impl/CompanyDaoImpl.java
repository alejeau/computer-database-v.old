package com.excilys.formation.computerdb.persistence.impl;

import static java.lang.Math.toIntExact;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
	public boolean exists(String name) {
		List<Company> tmp = null;

		final String QUERY_TXT = "SELECT c FROM Company c WHERE c.name = :name";
		TypedQuery<Company> query = entityManager.createQuery(QUERY_TXT, Company.class);
		query.setParameter("name", name);
		tmp = query.getResultList();

		if (tmp.isEmpty()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean exists(Company c) {
		List<Company> tmp = null;

		final String QUERY_TXT = "SELECT c FROM Company c WHERE c.id = :id";
		TypedQuery<Company> query = entityManager.createQuery(QUERY_TXT, Company.class);
		query.setParameter("id", c.getId());
		tmp = query.getResultList();

		if (tmp.isEmpty()) {
			return false;
		}

		return true;
	}

	@Override
	public int getNbEntries() {
		final String QUERY_TXT = "SELECT COUNT(c) FROM Company c";
		TypedQuery<Long> query = entityManager.createQuery(QUERY_TXT, Long.class);

		Long nbEntries = 0L;
		nbEntries = query.getSingleResult();

		return toIntExact(nbEntries);
	}

	@Override
	public List<Company> getFromTo(int offset, int limit) {
		final String QUERY_TXT = "SELECT c FROM Company c ORDER BY c.name";
		TypedQuery<Company> query = entityManager.createQuery(QUERY_TXT, Company.class);
		query.setFirstResult(offset);
		query.setMaxResults(limit);

		List<Company> list = query.getResultList();

		return list;
	}

	@Override
	public List<Company> getAll() {
		final String QUERY_TXT = "SELECT c FROM Company c ORDER BY c.name";
		TypedQuery<Company> query = entityManager.createQuery(QUERY_TXT, Company.class);

		List<Company> list = query.getResultList();

		return list;
	}

	@Override
	public Company getCompanyById(long id) {
		if ((id == -1l) || (id == 0L)) {
			return null;
		}

		final String QUERY_TXT = "SELECT c FROM Company c WHERE c.id = :id";
		TypedQuery<Company> query = entityManager.createQuery(QUERY_TXT, Company.class);
		query.setParameter("id", id);

		Company company = null;
		company = query.getSingleResult();

		return company;
	}

	@Override
	public Company getCompanyByName(String name) {
		final String QUERY_TXT = "SELECT c FROM Company c WHERE c.name = :name";

		TypedQuery<Company> query = entityManager.createQuery(QUERY_TXT, Company.class);
		query.setParameter("name", name);

		Company company = null;
		company = query.getSingleResult();

		return company;
	}

	@Override
	public void deleteCompany(long id) {
		LOG.info("Company deletion: \"DELETE FROM Company c WHERE c.id = " + id + "\"");
		final String QUERY_TXT = "DELETE FROM Company c WHERE c.id = :id";

		Query query = null;
		query = entityManager.createQuery(QUERY_TXT);
		query.setParameter("id", id);
		query.executeUpdate();
	}
}