package com.exclilys.formation.computerdb.exception;

public class DAOException extends RuntimeException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -39271619872297086L;

	public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}