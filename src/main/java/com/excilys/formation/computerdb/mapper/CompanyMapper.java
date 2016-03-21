package com.excilys.formation.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.computerdb.model.Company;

public class CompanyMapper {

	public static Company map(ResultSet rs) {
		long id = -1;
		String name = null;

		try {
			id = rs.getLong("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			name = rs.getString("name");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Company.Builder()
				.id(id)
				.name(name)
				.build();
	}
}
