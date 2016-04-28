package com.excilys.formation.computerdb.exceptions;

public class CompanyDaoException extends RuntimeException {
	private static final long serialVersionUID = -39271619872297086L;

	public CompanyDaoException(String message) {
		super(message);
	}

	public CompanyDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CompanyDaoException(Throwable cause) {
		super(cause);
	}
}