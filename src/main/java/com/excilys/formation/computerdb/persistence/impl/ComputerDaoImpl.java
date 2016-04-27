package com.excilys.formation.computerdb.persistence.impl;

import static java.lang.Math.toIntExact;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.computerdb.constants.Fields;
import com.excilys.formation.computerdb.mapper.model.ComputerMapper;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.persistence.ComputerDao;

@Repository
public class ComputerDaoImpl implements ComputerDao {
	protected final Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	protected ComputerMapper compMap;

	public ComputerDaoImpl() {
	}

	@Override
	public boolean exists(String name) {
		Computer tmp = null;

		final String QUERY_TXT = "SELECT c FROM Computer WHERE c.name = :name ORDER BY c.name;";
		TypedQuery<Computer> query = entityManager.createQuery(QUERY_TXT, Computer.class);
		query.setParameter("name", name);
		tmp = query.getSingleResult();

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
		List<Computer> list = null;
		TypedQuery<Computer> query = null;

		// Creating the query
		String query_txt = "SELECT c FROM Computer c WHERE c.name LIKE :name OR c.company IN (SELECT cy FROM Company cy WHERE cy.name LIKE :name)";
		query = entityManager.createQuery(query_txt, Computer.class);
		if (field != Fields.NONE) {
			String order = (ascending) ? "ASC" : "DESC";
			if (field == Fields.COMPANY) {
				query_txt = "SELECT c FROM Computer c LEFT JOIN Company cy ON c.id=cy.id WHERE c.name LIKE :name OR c.company IN (SELECT cy.id FROM Company cy WHERE name LIKE :name)";
			}
			query_txt += " ORDER BY " + field.toString() + " " + order;

			query = entityManager.createQuery(query_txt, Computer.class);
			query.setParameter("name", "%" + name + "%");
		}
		logger.debug(query_txt);

		query.setFirstResult(offset);
		query.setMaxResults(limit);

		list = query.getResultList();

		return list;
	}

	@Override
	public List<Computer> getFromTo(int offset, int limit) {
		return getFromToSortedBy(offset, limit, Fields.NONE, true);
	}

	@Override
	public List<Computer> getFromToSortedBy(int offset, int limit, Fields field, boolean ascending) {
		List<Computer> list = null;
		TypedQuery<Computer> query = null;

		String query_txt = "SELECT c FROM Computer c";
		if (field != Fields.NONE) {
			String order = (ascending) ? "ASC" : "DESC";
			if (field == Fields.COMPANY) {
				query_txt += " LEFT JOIN Company cy ON c.id=cy.id";
			}
			query_txt += " ORDER BY " + field.toString() + " " + order;
		}
		logger.debug(query_txt);

		query = entityManager.createQuery(query_txt, Computer.class);
		query.setFirstResult(offset);
		query.setMaxResults(limit);

		list = query.getResultList();

		return list;
	}

	@Override
	public int getNbEntries() {
		Long nbEntries = 0L;
		final String QUERY_TXT = "SELECT COUNT(c) FROM Computer c";
		TypedQuery<Long> query = entityManager.createQuery(QUERY_TXT, Long.class);

		nbEntries = query.getSingleResult();

		return toIntExact(nbEntries);
	}

	@Override
	public int getNbEntriesNamed(String name) {
		Long nbEntries = 0L;
		final String QUERY_TXT = "SELECT count(c) FROM Computer c WHERE c.name LIKE :name OR c.company IN (SELECT c.id FROM Company c WHERE c.name LIKE :name)";
		TypedQuery<Long> query = entityManager.createQuery(QUERY_TXT, Long.class);
		query.setParameter("name", "%" + name + "%");

		logger.debug(QUERY_TXT);

		nbEntries = query.getSingleResult();

		return toIntExact(nbEntries);
	}

	@Override
	public List<Computer> getAll() {
		return getAllSortedBy(Fields.NONE, true);
	}

	@Override
	public List<Computer> getAllSortedBy(Fields field, boolean ascending) {
		List<Computer> list = null;
		TypedQuery<Computer> query = null;

		String query_txt = "SELECT * FROM computer";
		if (field != Fields.NONE) {
			String order = (ascending) ? "ASC" : "DESC";
			if (field == Fields.COMPANY) {
				query_txt += " LEFT JOIN company on computer.company_id=company.id";
			}
			query_txt += " ORDER BY " + field.toString() + " " + order + ";";
		}

		query = entityManager.createQuery(query_txt, Computer.class);
		list = query.getResultList();

		return list;
	}

	@Override
	public Computer getComputerById(long id) {
		Computer computer = null;
		TypedQuery<Computer> query = null;
		final String QUERY_TXT = "SELECT c FROM Computer c WHERE c.id = :id";

		query = entityManager.createQuery(QUERY_TXT, Computer.class);
		query.setParameter("id", id);
		computer = query.getSingleResult();

		return computer;
	}

	public Computer getComputerByName(String name) {
		Computer computer = null;
		TypedQuery<Computer> query = null;
		final String QUERY_TXT = "SELECT c FROM Computer c WHERE c.name = :name";

		query = entityManager.createQuery(QUERY_TXT, Computer.class);
		query.setParameter("name", name);
		computer = query.getSingleResult();

		return computer;
	}

	/**
	 * Tries to insert a computer in the database
	 * 
	 * @param computer the computer to insert
	 * @return
	 */
	@Override
	public void createComputer(Computer computer) {
		entityManager.persist(computer);
	}

	@Override
	public void updateComputer(Computer computer) {
		Computer tmp = entityManager.find(Computer.class, computer.getId());
		tmp.setName(computer.getName());
		tmp.setIntro(computer.getIntro());
		tmp.setOutro(computer.getOutro());
		tmp.setCompany(computer.getCompany());
	}

	@Override
	public void deleteComputer(Computer c) {
		entityManager.remove(c);
	}

	@Override
	public void deleteComputer(long id) {
		final String QUERY_TXT = "DELETE FROM Computer c WHERE c.id = :id";
		Query query = entityManager.createQuery(QUERY_TXT);
		query.setParameter("id", id);
		query.executeUpdate();
	}

	@Override
	public void deleteComputer(String name) {
		final String QUERY_TXT = "DELETE FROM Computer c WHERE c.name = :name";
		Query query = entityManager.createQuery(QUERY_TXT);
		query.setParameter("name", name);
		query.executeUpdate();
	}

	@Override
	public void deleteComputers(long[] listId) {
		final String QUERY_TXT = "DELETE FROM Computer c WHERE c.id = :id";
		Query query = entityManager.createQuery(QUERY_TXT);

		for (long id : listId) {
			query.setParameter("id", id);
			query.executeUpdate();
		}
	}

	@Override
	public void deleteComputersWhereCompanyIdEquals(long id) {
		String QUERY_TXT = "DELETE FROM Computer c WHERE c.id = :id";
		Query query = entityManager.createQuery(QUERY_TXT);
		query.setParameter("id", id);
		query.executeUpdate();
	}
}