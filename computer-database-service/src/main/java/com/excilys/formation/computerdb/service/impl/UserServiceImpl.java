package com.excilys.formation.computerdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.computerdb.model.User;
import com.excilys.formation.computerdb.persistence.impl.UserDaoImpl;
import com.excilys.formation.computerdb.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDaoImpl userDaoImpl;

	@Override
	public User getUser(String login) {
		return userDaoImpl.getUser(login);
	}

	@Override
	@Transactional
	public void createUser(User u) {
		userDaoImpl.createUser(u);
	}

	@Override
	@Transactional
	public void deleteUser(String login) {
		userDaoImpl.deleteUser(login);
	}
}
