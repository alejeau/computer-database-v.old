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

import com.excilys.formation.computerdb.mapper.model.UserMapper;
import com.excilys.formation.computerdb.model.User;
import com.excilys.formation.computerdb.persistence.impl.UserDaoImpl;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDaoImpl userDaoImpl;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User u = userDaoImpl.getUser(login);
		List<GrantedAuthority> authorities = buildUserAuthority(u);
		return UserMapper.buildUserForAuthentication(u, authorities);
	}

	/**
	 * For this particular app, there only are USERs
	 * 
	 * @return a List<GrantedAuthority> with only the role ROLE_USER
	 */
	private List<GrantedAuthority> buildUserAuthority(User u) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority(u.getRole().getValue()));
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}
}
