package com.excilys.formation.computerdb.exceptions;

public class Exception extends RuntimeException {
	private static final long serialVersionUID = -5508554477303108122L;

	public Exception(String message) {
		super(message);
	}

	public Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public Exception(Throwable cause) {
		super(cause);
	}
}