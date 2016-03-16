package com.excilys.formation.computerdb.dto;

import java.time.LocalDate;

import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;

public class ComputerDTO {
	long   id = -1;
	String name;
	String intro;
	String outro;
	String company;

	public ComputerDTO(Computer c) {
		LocalDate i = c.getIntro(), o = c.getOutro();
		Company  cy = c.getCompany();
		final String EMPTY = "";
		long cid = c.getId();

		this.id 	 = 	(cid > -1L) ? (cid) : (-1L);
		this.name 	 = 	c.getName();
		this.intro   = 	(i  != null) ? (i.toString()) : (EMPTY);
		this.outro   = 	(o  != null) ? (o.toString()) : (EMPTY);
		this.company =  (cy != null) ? (cy.getName()) : (EMPTY);
	}

	public ComputerDTO(long cid, String name, String intro, String outro, String company) {
		this.id = (cid > -1L) ? (cid) : (-1L);
		this.name = name;
		this.intro = intro;
		this.outro = outro;
		this.company = company;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getOutro() {
		return outro;
	}

	public void setOutro(String outro) {
		this.outro = outro;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
