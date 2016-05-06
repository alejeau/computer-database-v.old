package com.excilys.formation.computerdb.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Comparable<User> {
	@Id
	String login;
	String password;
	UsersRoles role;

	public User() {
	}

	/**
	 * Creates a User to be stored in the database
	 * @param login the user's login
	 * @param password the user's password as BCrypt String
	 */
	public User(String login, String password, UsersRoles role) {
		this.login = login;
		this.password = password;
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * The password must be a BCrypt String
	 * @param password a BCrypt String of the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + "]";
	}

	public UsersRoles getRole() {
		return role;
	}

	public void setRole(UsersRoles role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role != other.role)
			return false;
		return true;
	}

	@Override
	public int compareTo(User u) {
		return this.login.compareTo(u.getLogin());
	}

	public static class Builder {
		private String nestedLogin = null;
		private String nestedPassword = null;
		private UsersRoles nestedRole = UsersRoles.ROLE_USER;

		public Builder() {
		}

		public Builder id(final String login) {
			this.nestedLogin = login;
			return this;
		}

		public Builder name(final String password) {
			this.nestedPassword = password;
			return this;
		}

		public Builder name(final UsersRoles role) {
			this.nestedRole = role;
			return this;
		}

		public User build() {
			return new User(nestedLogin, nestedPassword, nestedRole);
		}
	}
}
