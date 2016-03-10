package com.exclilys.formation.computerdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.exclilys.formation.computerdb.model.Company;
import com.exclilys.formation.computerdb.model.Computer;

public class ComputerMapper {

	public static Computer map(ResultSet rs, Company company) throws SQLException{
		long id = rs.getLong("id");
		String name = rs.getString("name");
		String intro = rs.getString("introduced");
		String outro = rs.getString("discontinued");
		
		return new Computer(id, name, intro, outro, company);
	}
}
