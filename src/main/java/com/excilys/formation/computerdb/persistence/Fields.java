package com.excilys.formation.computerdb.persistence;

public enum Fields {
	NONE (null),
	NAME ("name"),
	INTRO_DATE ("introduced"),
	OUTRO_DATE ("discontinued"),
	COMPANY ("company.name");
	
	private String field = "";
	   
	  //Constructeur
	  Fields(String field){
	    this.field = field;
	  }
	   
	  public String toString(){
	    return field;
	  }
}
