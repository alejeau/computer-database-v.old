package com.excilys.formation.computerdb.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.formation.computerdb.model.User;
import com.excilys.formation.computerdb.persistence.impl.UserDaoImpl;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDaoImpl userDaoImpl;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User u = userDaoImpl.getUser(login);
		List<GrantedAuthority> authorities = buildUserAuthority();
		return buildUserForAuthentication(u, authorities);
	}
	
	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private org.springframework.security.core.userdetails.User
		buildUserForAuthentication(User u, List<GrantedAuthority> authorities) {
			return new org.springframework.security.core.userdetails.User(u.getLogin(), u.getPassword(), true, true, true, true, authorities);
	}

//	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
	private List<GrantedAuthority> buildUserAuthority() {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
//		for (UserRole userRole : userRoles) {
//			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
//		}

		setAuths.add(new SimpleGrantedAuthority("ROLE_USER"));

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}
}
