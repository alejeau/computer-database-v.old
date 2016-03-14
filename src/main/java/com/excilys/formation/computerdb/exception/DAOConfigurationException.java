package com.excilys.formation.computerdb.exception;

public class DAOConfigurationException extends RuntimeException {
	private static final long serialVersionUID = -5508554477303108122L;

	public DAOConfigurationException(String message) {
		super(message);
	}

	public DAOConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOConfigurationException(Throwable cause) {
		super(cause);
	}
}