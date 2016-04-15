package com.excilys.formation.computerdb.mapper.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdb.model.Company;

public class CompanyMapper {
	protected static final Logger LOG = LoggerFactory.getLogger(CompanyMapper.class);

	public static Company map(ResultSet rs) {
		long id = -1;
		String name = null;

		try {
			id = rs.getLong("id");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		try {
			name = rs.getString("name");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return new Company.Builder()
				.id(id)
				.name(name)
				.build();
	}
	
	public static HashMap<Long, String> toHashMap(List<Company> list) {
		HashMap<Long, String> companyMap = new HashMap<>();
		for (Company c : list) {
			companyMap.put(c.getId(), c.getName());
		}
		return companyMap;
	}
}
