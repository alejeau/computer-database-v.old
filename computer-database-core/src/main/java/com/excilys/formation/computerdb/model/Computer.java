package com.excilys.formation.computerdb.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.excilys.formation.computerdb.constants.Time;

@Entity
@Table(name = "computer")
public class Computer implements Comparable<Computer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id = -1;
	protected String name = null;
	protected LocalDate introduced = null;
	protected LocalDate discontinued = null;
	
	@ManyToOne
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
		this.introduced = intro;
		this.discontinued = outro;
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
		this.introduced = intro;
		this.discontinued = outro;
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
			this.introduced = LocalDate.parse(intro);
		} else {
			intro = null;
		}

		if (outro != null) {
			outro = outro.split(" ")[0];
			this.discontinued = LocalDate.parse(outro);
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
			this.introduced = LocalDate.parse(intro);
		} else {
			intro = null;
		}

		if (outro != null) {
			outro = outro.split(" ")[0];
			this.discontinued = LocalDate.parse(outro);
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
		return introduced;
	}

	public void setIntro(LocalDate intro) {
		this.introduced = intro;
	}

	/**
	 * If the param is null, the LocalDate will be set to null
	 * 
	 * @param intro the date of start of production
	 */
	public void setIntro(String intro) {
		if (intro != null) {
			intro = intro.split(" ")[0];
			this.introduced = LocalDate.parse(intro);
		} else {
			intro = null;
		}
	}

	public LocalDate getOutro() {
		return discontinued;
	}

	public void setOutro(LocalDate outro) {
		this.discontinued = outro;
	}

	/**
	 * If the param is null, the LocalDate will be set to null
	 * 
	 * @param outro the date of start of production
	 */
	public void setOutro(String outro) {
		if (outro != null) {
			outro = outro.split(" ")[0];
			this.discontinued = LocalDate.parse(outro);
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
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
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
		if (introduced == null) {
			if (other.introduced != null) {
				return false;
			}
		} else if (!introduced.equals(other.introduced)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (discontinued == null) {
			if (other.discontinued != null) {
				return false;
			}
		} else if (!discontinued.equals(other.discontinued)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String s = "\"" + name + "\"";
		if (introduced != null) {
			s += ", introduced in " + introduced;
		}
		if (discontinued != null) {
			s += ", discontinued in " + discontinued;
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
