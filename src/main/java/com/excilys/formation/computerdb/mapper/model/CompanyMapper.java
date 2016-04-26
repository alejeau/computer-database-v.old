package com.excilys.formation.computerdb.mapper.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.computerdb.model.Company;

public class CompanyMapper implements RowMapper<Company> {
	protected static final Logger LOG = LoggerFactory.getLogger(CompanyMapper.class);

	/**
	 * Converts a List of Company to a HashMap<ID, Name>
	 * 
	 * @param list
	 * @return
	 */
	public static HashMap<Long, String> toHashMap(List<Company> list) {
		HashMap<Long, String> companyMap = new HashMap<>();
		for (Company c : list) {
			companyMap.put(c.getId(), c.getName());
		}
		return companyMap;
	}

	/**
	 * Maps ResultSet into a Company
	 */
	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		long id = -1;
		String name = null;

		id = rs.getLong("id");
		name = rs.getString("name");

		return new Company.Builder().id(id).name(name).build();
	}
}
