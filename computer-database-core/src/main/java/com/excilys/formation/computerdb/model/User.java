package com.excilys.formation.computerdb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Comparable<User> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	String login;
	String password;
	
	public User() {}
	
	public User(String login, String password){
		this.login = login;
		this.password = password;
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

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		return true;
	}

	@Override
	public int compareTo(User u) {
		return this.login.compareTo(u.getLogin());
	}
	
	public static class Builder {
		private String nestedLogin = null;
		private String nestedPassword = null;

		public Builder() {
		}
		
		public Builder(final String login, final String password) {
			this.nestedLogin = login;
			this.nestedPassword = password;
		}

		public Builder id(final String login) {
			this.nestedLogin = login;
			return this;
		}

		public Builder name(final String password) {
			this.nestedPassword = password;
			return this;
		}

		public User build() {
			return new User(nestedLogin, nestedPassword);
		}
	}
}
