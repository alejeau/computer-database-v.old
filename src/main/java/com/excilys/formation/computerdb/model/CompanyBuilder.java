package com.excilys.formation.computerdb.model;

public abstract class CompanyBuilder {
	protected Company company;
	
	public void createCompany(){
		this.company = new Company();
	}
	
	public void id(long id){
		this.company.setId(id);
	}
	
	public void name(String name){
		this.company.setName(name);
	}
	
	public Company company(){
		return this.company;
	}

}
