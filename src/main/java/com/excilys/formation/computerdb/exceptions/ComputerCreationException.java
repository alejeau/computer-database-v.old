package com.excilys.formation.computerdb.exceptions;

public class ComputerCreationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9062903736984345347L;


	public ComputerCreationException(String message) {
		super(message);
	}

	public ComputerCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ComputerCreationException(Throwable cause) {
		super(cause);
	}

}
