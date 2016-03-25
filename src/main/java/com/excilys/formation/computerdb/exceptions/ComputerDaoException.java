package com.excilys.formation.computerdb.exceptions;

public class ComputerDaoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4376967049015393721L;

	public ComputerDaoException(String message) {
		super(message);
	}

	public ComputerDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ComputerDaoException(Throwable cause) {
		super(cause);
	}

}
