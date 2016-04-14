package com.excilys.formation.computerdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.computerdb.exceptions.CompanyDaoException;
import com.excilys.formation.computerdb.mapper.model.CompanyMapper;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.persistence.CompanyDao;

@Repository
public class CompanyDaoImpl implements CompanyDao {

	@Autowired
	private DataSource dataSource;
	
	protected final Logger LOG = LoggerFactory.getLogger(CompanyDaoImpl.class);
	
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
	protected List<Company> list = null;
	
	
	protected CompanyDaoImpl() {
		list = new ArrayList<Company>();
	}

	@Override
	public List<Company> getFromTo(int offset, int limit) {
		Connection connection = null;

		list = new ArrayList<>();
		String query = "SELECT * FROM company ORDER BY name LIMIT ?, ? ";

		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Company company = CompanyMapper.map(rs);
				list.add(company);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			CompanyDaoImpl.close(rs, pstmt, connection);
			throw new CompanyDaoException(e.getMessage());
		} finally {
			CompanyDaoImpl.close(rs, pstmt, connection);			
		}
		
		return list;
	}

	@Override
	public int getNbEntries() {
		Connection connection = null;

		int nbEntries = 0;
		String query = "SELECT COUNT(*) as nb_companys FROM company";

		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				nbEntries = rs.getInt("nb_companys");
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			CompanyDaoImpl.close(rs, pstmt, connection);
			throw new CompanyDaoException(e.getMessage());
		} finally {
			CompanyDaoImpl.close(rs, pstmt, connection);			
		}

		return nbEntries;
	}

	@Override
	public List<Company> getAll() {
		Connection connection = null;

		list = new ArrayList<>();
		String query = "SELECT * FROM company ORDER BY name";

		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Company company = CompanyMapper.map(rs);
				list.add(company);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			CompanyDaoImpl.close(rs, pstmt, connection);
			throw new CompanyDaoException(e.getMessage());
		} finally {
			CompanyDaoImpl.close(rs, pstmt, connection);			
		}

		return list;
	}

	public Company getCompanyById(long id) {
		if (id == -1l) {
			return null;
		}

		Connection connection = null;

		pstmt = null;
		rs = null;
		Company company = null;
		String query = "SELECT * FROM company WHERE id = ?";

		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				company = CompanyMapper.map(rs);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			CompanyDaoImpl.close(rs, pstmt, connection);
			throw new CompanyDaoException(e.getMessage());
		} finally {
			CompanyDaoImpl.close(rs, pstmt, connection);			
		}
		return company;
	}

	public Company getCompanyByName(String name) {
		Connection connection = null;

		pstmt = null;
		rs = null;
		Company company = null;
		String query = "SELECT * FROM company WHERE name = ?";

		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				company = CompanyMapper.map(rs);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			CompanyDaoImpl.close(rs, pstmt, connection);
			throw new CompanyDaoException(e.getMessage());
		} finally {
			CompanyDaoImpl.close(rs, pstmt, connection);			
		}
		return company;
	}
	
	@Override
	public void deleteCompany(long id, Connection connection) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LOG.info("Company deletion: \"DELETE FROM company WHERE id = " + id + "\"");
		String query = "DELETE FROM company WHERE id = ?";
		pstmt = connection.prepareStatement(query);
		pstmt.setLong(1, id);
		pstmt.executeUpdate();
	
		close(rs, pstmt, null);
	}

	private static void close(ResultSet rs, PreparedStatement pstmt, Connection connec) {
		final Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new CompanyDaoException(e.getMessage());
			}
			rs = null;
		}

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new CompanyDaoException(e.getMessage());
			}
			pstmt = null;
		}
		if (connec != null){
			try {
				connec.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new CompanyDaoException(e.getMessage());
			}
			connec = null;
		}
	}
}