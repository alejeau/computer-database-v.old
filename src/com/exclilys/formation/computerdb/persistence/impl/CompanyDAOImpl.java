package com.exclilys.formation.computerdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.exclilys.formation.computerdb.exception.DAOException;
import com.exclilys.formation.computerdb.model.Company;
import com.exclilys.formation.computerdb.model.Queries;
import com.exclilys.formation.computerdb.persistence.CompanyDAO;
import com.exclilys.formation.computerdb.persistence.ConnectionFactory;
import com.exclilys.formation.computerdb.persistence.mapper.CompanyMapper;

public class CompanyDAOImpl implements CompanyDAO {

	private ConnectionFactory connectionFactory;
	protected final Logger logger = Logger.getLogger(CompanyDAOImpl.class);

	public CompanyDAOImpl(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	@Override
	public List<Company> getAll() {
		List<Company> liste = new ArrayList<>();
		String query = "SELECT * FROM company";
		
		Connection connexion = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			connexion = connectionFactory.getConnection();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		try {
			stmt = connexion.createStatement();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			connectionFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			connectionFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}

		try {
			while (rs.next()) {
				Company company = null;
				company = CompanyMapper.map(rs);
				liste.add(company);
			}
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			connectionFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}
		
		connectionFactory.close(connexion, rs, stmt, null);
		return liste;
	}

	@Override
	public int getNbEntries() {
		Connection connexion = null;
		Statement stmt = null;
		ResultSet rs = null;
		int nbEntries = 0;
		String query = "SELECT COUNT(*) as nb_companies FROM company";

		try {
			connexion = connectionFactory.getConnection();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			connectionFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}
		try {
			stmt = connexion.createStatement();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			connectionFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			connectionFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}

		try {
			if (rs.next()){
				try {
					nbEntries = rs.getInt("nb_companies");
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					connectionFactory.close(connexion, rs, stmt, null);
					throw new DAOException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			connectionFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}

		connectionFactory.close(connexion, rs, stmt, null);
		return nbEntries;
	}

	@Override
	public List<Company> getFromTo(int from, int nb) {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Company> liste = new ArrayList<Company>();
		String query = "SELECT * FROM company LIMIT ?, ?";

		try {
			connexion = connectionFactory.getConnection();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt = connexion.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			connectionFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(1, from);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			connectionFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(2, nb);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			connectionFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			connectionFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}

		try {
			while (rs.next()){
				Company company = null;
				company = CompanyMapper.map(rs);
				liste.add(company);
			}
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			connectionFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}

		return liste;
	}
	
	public static Company getCompany(long id){
		ConnectionFactory cf = ConnectionFactory.getInstance();

		String query = "select name from company where id = '" + id + "'";
		ResultSet rs = null;
		String name = null;

		Connection connexion = null;
		Statement stmt = null;

		try {
			connexion = cf.getConnection();
		} catch (SQLException e) {
			cf.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}
		try {
			stmt = connexion.createStatement();
		} catch (SQLException e) {
			cf.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}

		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			cf.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}
		
		try {
			if (rs.next()){
				try {
					name = rs.getString("name");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return new Company(id, name);
	}

}