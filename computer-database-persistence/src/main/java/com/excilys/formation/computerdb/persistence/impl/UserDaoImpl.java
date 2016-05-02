package com.excilys.formation.computerdb.persistence.impl;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.formation.computerdb.model.User;
import com.excilys.formation.computerdb.persistence.UserDao;

@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	protected EntityManager entityManager;

	protected final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);

	@Override
	public User getUser(String login) {
		final String QUERY_TXT = "SELECT u FROM User u WHERE u.login = :login";

		TypedQuery<User> query = entityManager.createQuery(QUERY_TXT, User.class);
		query.setParameter("name", login);

		User u = null;
		u = query.getSingleResult();

		return u;
	}

	@Override
	public void createUser(User u) {
		entityManager.persist(u);
	}

	@Override
	public void deleteUser(User u) throws SQLException {
		final String QUERY_TXT = "DELETE FROM User u WHERE u.login = :login";

		Query query = null;
		query = entityManager.createQuery(QUERY_TXT);
		query.setParameter("login", u.getLogin());
		query.executeUpdate();
	}

}
