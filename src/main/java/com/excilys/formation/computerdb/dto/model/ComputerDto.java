package com.excilys.formation.computerdb.dto.model;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import com.excilys.formation.computerdb.mapper.date.DateMapper;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.validators.annotations.ValidDate;

public class ComputerDto {
	protected long id = -1;

	@Size(min = 1, message = "Name must have at least 1 character")
	protected String name;
	@ValidDate
	protected String intro;
	@ValidDate
	protected String outro;
	protected String company;

	/**
	 * Creates a lighter Computer version with only Company name instead of a Company.
	 * 
	 * @param c the Computer to convert
	 * @param locale the language chosen
	 */
	public ComputerDto(Computer c, String locale) {
		LocalDate i = c.getIntro(), o = c.getOutro();
		Company cy = c.getCompany();
		final String EMPTY = "";

		this.id = c.getId();
		this.name = c.getName();
		this.intro = (i != null) ? (DateMapper.unmapDate(i.toString(), locale)) : (EMPTY);
		this.outro = (o != null) ? (DateMapper.unmapDate(o.toString(), locale)) : (EMPTY);
		this.company = (cy != null) ? (cy.getName()) : (EMPTY);
	}

	/**
	 * Creates a lighter Computer version with only Company name instead of a Company.
	 * 
	 * @param cid the Computer cid
	 * @param name the Computer name
	 * @param intro the Computer intro
	 * @param outro the Computer outro
	 * @param company the Computer company
	 * @param locale the Computer locale
	 */
	public ComputerDto(long cid, String name, String intro, String outro, String company, String locale) {
		this.id = (cid > -1L) ? (cid) : (-1L);
		this.name = name;
		this.intro = DateMapper.unmapDate(intro, locale);
		this.outro = DateMapper.unmapDate(outro, locale);
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
		private String nestedLocale = null;

		public ComputerDTOBuilder() {
		}

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

		public ComputerDTOBuilder locale(final String locale) {
			this.nestedLocale = locale;
			return this;
		}

		public ComputerDto build() {
			return new ComputerDto(nestedId, nestedName, nestedIntro, nestedOutro, nestedCompany, nestedLocale);
		}
	}
}
