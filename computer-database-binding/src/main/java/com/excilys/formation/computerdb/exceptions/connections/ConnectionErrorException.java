package com.excilys.formation.computerdb.exceptions.connections;

public class ConnectionErrorException extends RuntimeException {
	private static final long serialVersionUID = -8697242556529565648L;

	public ConnectionErrorException(String message) {
		super(message);
	}

	public ConnectionErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionErrorException(Throwable cause) {
		super(cause);
	}
}
