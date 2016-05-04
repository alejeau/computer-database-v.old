package com.excilys.formation.computerdb.mapper.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserMapper {

	/**
	 * Converts a User from Model to a Spring User
	 * @param u the User from the Database
	 * @param authorities its permissions
	 * @return a Spring User
	 */
	public static User buildUserForAuthentication(com.excilys.formation.computerdb.model.User u,
			List<GrantedAuthority> authorities) {
		return new User(u.getLogin(), u.getPassword(), authorities);
	}

}
