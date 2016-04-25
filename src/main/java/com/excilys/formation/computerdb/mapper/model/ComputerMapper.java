package com.excilys.formation.computerdb.mapper.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;

@Component
public class ComputerMapper implements RowMapper<Computer> {

	public ComputerMapper() {
	}
	
	/**
	 * Maps a ResultSet into a Computer.
	 * @param rs the ResultSet to map
	 * @param company the Company to add to the Computer
	 * @return a Computer
	 * @throws SQLException
	 */
	public Computer map(ResultSet rs, Company company) throws SQLException {
		long id 	 = rs.getLong("id");
		String name  = rs.getString("name");
		String intro = rs.getString("introduced");
		String outro = rs.getString("discontinued");

		return new Computer.Builder()
			.id(id)
			.name(name)
			.intro(intro)
			.outro(outro)
			.company(company)
			.build();
	}
	
	/**
	 * Maps a ResultSet into a Company ID.
	 * @param rs the ResultSet to map
	 * @return the Company ID
	 */
	public static long getCompanyId(ResultSet rs) {
		long id = -1;
		try {
		id = rs.getLong("company_id");
		} catch (SQLException e) {
			
		}
		return id;
	}

	/**
	 * Maps a ResultSet into a Computer.
	 * WARNING: the company will not be set and so has to be set manually after.
	 */
	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		long id 	 = rs.getLong("id");
		String name  = rs.getString("name");
		String intro = rs.getString("introduced");
		String outro = rs.getString("discontinued");
		long cid	 = rs.getLong("company_id");
		Company c = new Company.Builder()
						.id(cid)
						.build();

		return new Computer.Builder()
			.id(id)
			.name(name)
			.intro(intro)
			.outro(outro)
			.company(c)
			.build();
	}
}
