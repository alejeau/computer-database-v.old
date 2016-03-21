package com.excilys.formation.computerdb.errors;

public class Problem {
	protected String field = null;
	protected String message = null;
	
	public Problem() {}
	
	public Problem(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString() {
		if ((this.field == null) && (this.message == null)) {
			return "Error not initialized!";
		} else if ((this.field != null) && (this.message == null)) {
			return "Field : \"" + this.field + "\", no specific error message attached!";
		}
		
		return "Field : \"" + this.field + "\", message : \"" + this.message + "\".";
	}
}
