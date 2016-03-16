package com.excilys.formation.computerdb.exceptions;

public class PagerSearchException extends Exception {
	private static final long serialVersionUID = -6787237325971831287L;

	public PagerSearchException(String message) {
		super(message);
	}

	public PagerSearchException(String message, Throwable cause) {
		super(message, cause);
	}

	public PagerSearchException(Throwable cause) {
		super(cause);
	}

}
