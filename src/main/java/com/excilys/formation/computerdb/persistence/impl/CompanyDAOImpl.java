package com.excilys.formation.computerdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.excilys.formation.computerdb.exceptions.DAOException;
import com.excilys.formation.computerdb.mapper.CompanyMapper;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.persistence.CompanyDAO;

public enum CompanyDAOImpl implements CompanyDAO {
	INSTANCE;

	private ConnectionFactoryImpl connectionFactory;
	protected final Logger logger = Logger.getLogger(CompanyDAOImpl.class);
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
	protected List<Company> list = null;

	private CompanyDAOImpl() {
		connectionFactory = ConnectionFactoryImpl.getInstance();
		this.list = new ArrayList<Company>();
	}

	@Override
	public List<Company> getFromTo(int offset, int limit) {
		Connection connection = null;
		connection = connectionFactory.getConnection();

		list = new ArrayList<>();
		String query = "SELECT * FROM company ORDER BY name LIMIT ?, ? ";

		try {
			pstmt = connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(1, offset);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(2, limit);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}

		try {
			while (rs.next()) {
				Company company = CompanyMapper.map(rs);
				list.add(company);
			}
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}

		CompanyDAOImpl.close(rs, pstmt, connection);
		return list;
	}

	@Override
	public int getNbEntries() {
		Connection connection = null;
		connection = connectionFactory.getConnection();

		int nbEntries = 0;
		String query = "SELECT COUNT(*) as nb_companys FROM company";

		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}

		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}

		try {
			if (rs.next()) {
				try {
					nbEntries = rs.getInt("nb_companys");
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					CompanyDAOImpl.close(rs, pstmt, connection);
					throw new DAOException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}

		CompanyDAOImpl.close(rs, pstmt, connection);
		return nbEntries;
	}

	@Override
	public List<Company> getAll() {
		Connection connection = null;
		connection = connectionFactory.getConnection();

		list = new ArrayList<>();
		String query = "SELECT * FROM company ORDER BY name";

		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}

		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}

		try {
			while (rs.next()) {
				Company company = CompanyMapper.map(rs);
				list.add(company);
			}
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}

		CompanyDAOImpl.close(rs, pstmt, connection);

		return list;
	}

	public Company getCompanyById(long id) {
		if (id == -1l) {
			return null;
		}

		Connection connection = null;
		connection = connectionFactory.getConnection();

		pstmt = null;
		rs = null;
		Company company = null;
		String query = "SELECT * FROM company WHERE id = ?";

		try {
			pstmt = connection.prepareStatement(query);
		} catch (SQLException e) {
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setLong(1, id);
		} catch (SQLException e) {
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}

		// si l'entrée existe
		try {
			if (rs.next()) {
				company = CompanyMapper.map(rs);
			}
		} catch (SQLException e) {
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}

		CompanyDAOImpl.close(rs, pstmt, connection);
		return company;
	}

	public Company getCompanyByName(String name) {
		Connection connection = null;
		connection = connectionFactory.getConnection();

		pstmt = null;
		rs = null;
		Company company = null;
		String query = "SELECT * FROM company WHERE name = ?";

		try {
			pstmt = connection.prepareStatement(query);
		} catch (SQLException e) {
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, name);
		} catch (SQLException e) {
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}

		// si l'entrée existe
		try {
			if (rs.next()) {
				company = CompanyMapper.map(rs);
			}
		} catch (SQLException e) {
			CompanyDAOImpl.close(rs, pstmt, connection);
			throw new DAOException(e.getMessage());
		}

		CompanyDAOImpl.close(rs, pstmt, connection);
		return company;
	}

	private static void close(ResultSet rs, PreparedStatement pstmt, Connection connec) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pstmt = null;
		}
		if (connec != null){
			try {
				connec.close();
			} catch (SQLException e) {
			}
			connec = null;
		}
	}
}