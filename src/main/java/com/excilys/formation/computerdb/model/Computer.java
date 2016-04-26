package com.excilys.formation.computerdb.model;

import java.time.LocalDate;

import com.excilys.formation.computerdb.constants.Time;

public class Computer implements Comparable<Computer> {
	protected long id = -1;
	protected String name = null;
	protected LocalDate intro = null;
	protected LocalDate outro = null;
	protected Company company = null;

	public Computer() {
	}

	/**
	 * Creates a computer without id, useful to insert it into the database.
	 * 
	 * @param name Computer's name
	 * @param intro Date of start of production
	 * @param outro Date of end of production
	 * @param comp The manufacturer
	 */
	public Computer(String name, LocalDate intro, LocalDate outro, Company comp) {
		this.name = name;
		this.intro = intro;
		this.outro = outro;
		this.company = comp;
	}

	/**
	 * Creates a computer with an existing id
	 * 
	 * @param id the computer's id in the database
	 * @param name Computer's name
	 * @param intro Date of start of production
	 * @param outro Date of end of production
	 * @param comp The manufacturer
	 */
	public Computer(long id, String name, LocalDate intro, LocalDate outro, Company comp) {
		this.id = id;
		this.name = name;
		this.intro = intro;
		this.outro = outro;
		this.company = comp;
	}

	/**
	 * Creates a computer without id, useful to insert it into the database
	 * 
	 * @param name Computer's name
	 * @param intro Date of start of production
	 * @param outro Date of end of production
	 * @param comp The manufacturer
	 */
	public Computer(String name, String intro, String outro, Company comp) {
		this.name = name;

		if (intro != null) {
			intro = intro.split(" ")[0];
			this.intro = LocalDate.parse(intro);
		} else {
			intro = null;
		}

		if (outro != null) {
			outro = outro.split(" ")[0];
			this.outro = LocalDate.parse(outro);
		} else {
			outro = null;
		}

		this.company = comp;
	}

	/**
	 * Creates a computer with an existing id
	 * 
	 * @param id the computer's id in the database
	 * @param name Computer's name
	 * @param intro Date of start of production
	 * @param outro Date of end of production
	 * @param comp The manufacturer
	 */
	public Computer(long id, String name, String intro, String outro, Company comp) {
		this.id = id;
		this.name = name;

		if (intro != null) {
			intro = intro.split(" ")[0];
			this.intro = LocalDate.parse(intro);
		} else {
			intro = null;
		}

		if (outro != null) {
			outro = outro.split(" ")[0];
			this.outro = LocalDate.parse(outro);
		} else {
			outro = null;
		}

		this.company = comp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntro() {
		return intro;
	}

	public void setIntro(LocalDate intro) {
		this.intro = intro;
	}

	/**
	 * If the param is null, the LocalDate will be set to null
	 * 
	 * @param intro the date of start of production
	 */
	public void setIntro(String intro) {
		if (intro != null) {
			intro = intro.split(" ")[0];
			this.intro = LocalDate.parse(intro);
		} else {
			intro = null;
		}
	}

	public LocalDate getOutro() {
		return outro;
	}

	public void setOutro(LocalDate outro) {
		this.outro = outro;
	}

	/**
	 * If the param is null, the LocalDate will be set to null
	 * 
	 * @param outro the date of start of production
	 */
	public void setOutro(String outro) {
		if (outro != null) {
			outro = outro.split(" ")[0];
			this.outro = LocalDate.parse(outro);
		} else {
			outro = null;
		}
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public long getId() {
		return id;
	}

	public static String getTimestampZero() {
		return Time.TIMESTAMP_ZERO;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Checks whether the computer is associated to manufacturing company
	 * 
	 * @return true if there is a manufacturing company associated to the computer, false else
	 */
	public boolean hasACompany() {
		return (this.company != null) ? true : false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((intro == null) ? 0 : intro.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((outro == null) ? 0 : outro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		Computer other = (Computer) obj;

		if (company == null) {
			if (other.company != null) {
				return false;
			}
		} else if (!company.equals(other.company)) {
			return false;
		}
		if (intro == null) {
			if (other.intro != null) {
				return false;
			}
		} else if (!intro.equals(other.intro)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (outro == null) {
			if (other.outro != null) {
				return false;
			}
		} else if (!outro.equals(other.outro)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String s = "\"" + name + "\"";
		if (intro != null) {
			s += ", introduced in " + intro;
		}
		if (outro != null) {
			s += ", discontinued in " + outro;
		}
		if (company != null) {
			s += ", manufactured by " + company.getName();
		}
		return s;
	}

	@Override
	public int compareTo(Computer c) {
		return this.compareTo(c);
	}

	public static class Builder {
		private long nestedId = -1;
		private String nestedName = null;
		private LocalDate nestedIntro = null;
		private LocalDate nestedOutro = null;
		private Company nestedCompany = null;

		public Builder() {
		}

		public Builder id(final long id) {
			this.nestedId = id;
			return this;
		}

		public Builder name(final String name) {
			this.nestedName = name;
			return this;
		}

		public Builder intro(final LocalDate intro) {
			this.nestedIntro = intro;
			return this;
		}

		public Builder intro(final String intro) {
			if ((intro == null) || (intro.equals(""))) {
				this.nestedIntro = null;
			} else {
				String tmp = intro.split(" ")[0];
				this.nestedIntro = LocalDate.parse(tmp);
			}
			return this;
		}

		public Builder outro(final LocalDate outro) {
			this.nestedOutro = outro;
			return this;
		}

		public Builder outro(final String outro) {
			if ((outro == null) || (outro.equals(""))) {
				this.nestedOutro = null;
			} else {
				String tmp = outro.split(" ")[0];
				this.nestedOutro = LocalDate.parse(tmp);
			}
			return this;
		}

		public Builder company(final Company company) {
			this.nestedCompany = company;
			return this;
		}

		public Computer build() {
			return new Computer(nestedId, nestedName, nestedIntro, nestedOutro, nestedCompany);
		}
	}
}
