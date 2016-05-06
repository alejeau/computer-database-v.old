package com.excilys.formation.computerdb.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserTest {
	private static final String LOGIN 		= "test";
	private static final String LOGIN2 		= "test2";
	private static final String PASSWORD 	= encode("password");
	private static final String PASSWORD2 	= encode("password2");
	private static final UsersRoles ROLE 	= UsersRoles.ROLE_ADMIN;
	private static final User 	USER	 	= new User(LOGIN, PASSWORD, ROLE);

	@Test
	public void constructor() {
		User u = new User();
		assertNotNull(u);
	}
	
	@Test
	public void getLogin() {
		assertEquals(LOGIN, USER.getLogin());
	}
	
	@Test
	public void setLogin() {
		User u = new User(LOGIN, PASSWORD, ROLE);
		u.setLogin(LOGIN2);
		assertEquals(LOGIN2, u.getLogin());
	}
	
	@Test
	public void getPassword() {
		assertEquals(PASSWORD, USER.getPassword());
	}
	
	@Test
	public void setPassword() {
		User u = new User(LOGIN, PASSWORD, ROLE);
		u.setPassword(PASSWORD2);
		assertEquals(PASSWORD2, u.getPassword());
	}

	private static String encode(final String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
	
}
