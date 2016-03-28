package com.excilys.formation.computerdb.dto.model;

import java.time.LocalDate;

import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;

public class ComputerDto {
	protected long   id = -1;
	protected String name;
	protected String intro;
	protected String outro;
	protected String company;

	public ComputerDto(Computer c) {
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

	public ComputerDto(long cid, String name, String intro, String outro, String company) {
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
	
	public static class ComputerDTOBuilder {
		private long nestedId = -1;
		private String nestedName = null;
		private String nestedIntro = null;
		private String nestedOutro = null;
		private String nestedCompany = null;


		public ComputerDTOBuilder() {}

		public ComputerDTOBuilder(final long id, final String name) {
			this.nestedId = id;
			this.nestedName = name;
		}

		public ComputerDTOBuilder id(final long id) {
			this.nestedId = id;
			return this;
		}

		public ComputerDTOBuilder name(final String name) {
			this.nestedName = name;
			return this;
		}

		public ComputerDTOBuilder intro(final String intro) {
			this.nestedIntro = intro;
			return this;
		}
		
		public ComputerDTOBuilder outro(final String outro) {
			this.nestedOutro = outro;
			return this;
		}

		public ComputerDTOBuilder company(final String company) {
			this.nestedCompany = company;
			return this;
		}

		public ComputerDto build() {
			return new ComputerDto(nestedId, nestedName, nestedIntro, nestedOutro, nestedCompany);
		}
	}
}
