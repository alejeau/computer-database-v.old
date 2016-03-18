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
import com.excilys.formation.computerdb.persistence.ConnectionFactory;

public enum CompanyDAOImpl implements CompanyDAO {
	INSTANCE;

	private ConnectionFactory connectionFactory;
	protected final Logger logger = Logger.getLogger(CompanyDAOImpl.class);
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
	protected List<Company> list = null;

	private CompanyDAOImpl() {
		connectionFactory = ConnectionFactory.getInstance();
		this.list = new ArrayList<Company>();
	}

	@Override
	public List<Company> getFromTo(int from, int to) {
		Connection connection = null;
		connection = connectionFactory.getConnection();
	
		list = new ArrayList<>();
		String query = "SELECT * FROM company ORDER BY name LIMIT ?, ? ";

		try {
			pstmt = connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(1, from);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(2, to);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		try {
			while (rs.next()) {
				Company company = CompanyMapper.map(rs);
				list.add(company);
			}
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		this.closeAll();
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
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		try {
			if (rs.next()) {
				try {
					nbEntries = rs.getInt("nb_companys");
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		this.closeAll();
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
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		try {
			while (rs.next()) {
				Company company = CompanyMapper.map(rs);
				list.add(company);
			}
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		this.closeAll();

		return list;
	}

	public void closeAll() {
		if (this.rs != null) {
			try {
				this.rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.rs = null;
		}

		if (this.stmt != null) {
			try {
				this.stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.stmt = null;
		}

		if (this.pstmt != null) {
			try {
				this.pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.pstmt = null;
		}

		//this.list = new ArrayList<>();
	}

	public Company getCompanyById(long id) {
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