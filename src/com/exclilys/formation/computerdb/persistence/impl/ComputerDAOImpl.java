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
import com.exclilys.formation.computerdb.model.Computer;
import com.exclilys.formation.computerdb.persistence.ComputerDAO;
import com.exclilys.formation.computerdb.persistence.ConnectionFactory;
import com.exclilys.formation.computerdb.persistence.mapper.ComputerMapper;

public class ComputerDAOImpl implements ComputerDAO {
	//*
	private ConnectionFactory daoFactory;
	protected final Logger logger = Logger.getLogger(ComputerDAOImpl.class);

	public ComputerDAOImpl(ConnectionFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	//*/

	@Override
	public List<Computer> getFromTo(int from, int to) {
		Connection connexion = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Computer> liste = new ArrayList<Computer>();

		String query = "SELECT * FROM computer LIMIT ?, ?";
		try {
			connexion = daoFactory.getConnection();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt = connexion.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(1, from);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(2, to);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}

		try {
			while (rs.next()){
				Company c = null;
				Computer computer = null;
				long id = -1;

				try {
					id = rs.getLong("company_id");
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					daoFactory.close(connexion, rs, null, pstmt);
					throw new DAOException(e.getMessage());
				}

				c = CompanyDAOImpl.getCompany(id);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					daoFactory.close(connexion, rs, null, pstmt);
					throw new DAOException(e.getMessage());
				}

				liste.add(computer);
			}
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}

		daoFactory.close(connexion, rs, null, pstmt);

		return liste;
	}



	@Override
	public int getNbEntries() {
		Connection connexion = null;
		Statement stmt = null;
		ResultSet rs = null;
		int nbEntries = 0;
		String query = "SELECT COUNT(*) as nb_computers FROM computer";

		try {
			connexion = daoFactory.getConnection();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}

		try {
			stmt = connexion.createStatement();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}

		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}

		try {
			if (rs.next()){
				try {
					nbEntries = rs.getInt("nb_computers");
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					daoFactory.close(connexion, rs, stmt, null);
					throw new DAOException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}

		daoFactory.close(connexion, rs, stmt, null);

		return nbEntries;
	}

	@Override
	public List<Computer> getAll() {
		Connection connexion = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Computer> list = new ArrayList<Computer>();
		String query = "SELECT * FROM computer";

		try {
			connexion = daoFactory.getConnection();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		try {
			stmt = connexion.createStatement();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}

		try {
			while (rs.next()){
				Company c = null;
				Computer computer = null;
				long id = -1;

				try {
					id = rs.getLong("company_id");
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					daoFactory.close(connexion, rs, stmt, null);
					throw new DAOException(e.getMessage());
				}

				c = CompanyDAOImpl.getCompany(id);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					daoFactory.close(connexion, rs, stmt, null);
					throw new DAOException(e.getMessage());
				}
				list.add(computer);
			}
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, stmt, null);
			throw new DAOException(e.getMessage());
		}
		daoFactory.close(connexion, rs, stmt, null);

		return list;
	}

	@Override
	public Computer getComputerById(long id) {
		Connection connexion = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Computer computer = null;
		String query = "SELECT * FROM computer WHERE id = ?";

		try {
			connexion = daoFactory.getConnection();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt = connexion.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setLong(1, id);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}

		// si l'entrée existe
		try {
			if (rs.next()){
				Company c = null;
				long idc = -1;

				try {
					idc = rs.getLong("company_id");
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					daoFactory.close(connexion, rs, null, pstmt);
					throw new DAOException(e.getMessage());
				}

				c = CompanyDAOImpl.getCompany(idc);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					daoFactory.close(connexion, rs, null, pstmt);
					throw new DAOException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, rs, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		daoFactory.close(connexion, rs, null, pstmt);

		return computer;
	}

	@Override
	public int createComputer(Computer computer) {
		Connection connexion = null;
		PreparedStatement pstmt = null;
		int nbRow = 0;
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";

		try {
			connexion = daoFactory.getConnection();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt = connexion.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, computer.getName());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(2, computer.getIntro().toString());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(3, computer.getOutro().toString());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setLong(4, computer.getCompany().getId());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			nbRow = pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}

		daoFactory.close(connexion, null, null, pstmt);
		return nbRow;
	}

	@Override
	public void updateComputer(Computer computer) {

		Connection connexion = null;
		PreparedStatement pstmt = null;
		String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";

		try {
			connexion = daoFactory.getConnection();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt = connexion.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, computer.getName());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(2, computer.getIntro().toString());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(3, computer.getOutro().toString());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setLong(4, computer.getCompany().getId());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}

		daoFactory.close(connexion, null, null, pstmt);
	}

	@Override
	public void deleteComputer(long id) {
		Connection connexion = null;
		PreparedStatement pstmt = null;
		String query = "DELETE FROM computer WHERE id = ?";

		try {
			connexion = daoFactory.getConnection();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt = connexion.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setLong(1, id);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			daoFactory.close(connexion, null, null, pstmt);
			throw new DAOException(e.getMessage());
		}

		daoFactory.close(connexion, null, null, pstmt);

	}

}