package com.excilys.formation.computerdb.model;

public enum UsersRoles {
	ROLE_USER,
	ROLE_ADMIN("ROLE_ADMIN");
	
	private String value;
	
	private UsersRoles() {
		this.value = "ROLE_USER";
	}
	
	private UsersRoles(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
