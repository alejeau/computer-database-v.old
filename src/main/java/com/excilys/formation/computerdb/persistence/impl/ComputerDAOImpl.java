package com.excilys.formation.computerdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdb.constants.Time;
import com.excilys.formation.computerdb.exceptions.ComputerCreationException;
import com.excilys.formation.computerdb.exceptions.DAOException;
import com.excilys.formation.computerdb.mapper.ComputerMapper;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.persistence.ComputerDAO;

public enum ComputerDAOImpl implements ComputerDAO {
	INSTANCE;

	private ConnectionFactoryImpl connectionFactory;
	protected final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	protected CompanyDAOImpl companyDAOImpl;
	protected List<Computer> list = null;

	private ComputerDAOImpl() {
		this.connectionFactory = ConnectionFactoryImpl.INSTANCE;
		this.companyDAOImpl = CompanyDAOImpl.INSTANCE;
	}

	@Override
	public boolean exists(String name) {
		boolean exists = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();

		list = new ArrayList<>();
		String query = "SELECT * FROM computer WHERE name=? ORDER BY name;";

		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				exists = true;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

		return exists;
	}

	@Override
	public boolean exists(Computer computer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Computer> getNamedFromTo(String name, int offset, int limit) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();

		list = new ArrayList<>();
		String query = "SELECT * FROM computer where name LIKE ? OR company_id IN (SELECT id FROM company where name LIKE ?) ORDER BY name LIMIT ? OFFSET ?";

		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "%" + name + "%");
			pstmt.setString(2, "%" + name + "%");
			pstmt.setInt(3, limit);
			pstmt.setInt(4, offset);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Company c = null;
				Computer computer = null;
				long id = -1;
				id = rs.getLong("company_id");

				c = this.companyDAOImpl.getCompanyById(id);
				computer = ComputerMapper.map(rs, c);

				list.add(computer);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

		return list;
	}

	@Override
	public List<Computer> getFromTo(int offset, int limit) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();

		list = new ArrayList<>();

		String query = "SELECT * FROM computer ORDER BY name LIMIT ?, ?";

		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Company c = null;
				Computer computer = null;
				long id = -1;
				id = rs.getLong("company_id");

				c = this.companyDAOImpl.getCompanyById(id);
				computer = ComputerMapper.map(rs, c);

				list.add(computer);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

		return list;
	}

	@Override
	public int getNbEntries() {
		Connection connection = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();

		int nbEntries = 0;
		String query = "SELECT COUNT(*) as nb_computers FROM computer";

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				nbEntries = rs.getInt("nb_computers");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

		return nbEntries;
	}

	@Override
	public int getNbEntriesNamed(String name) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();

		int nbEntries = 0;
		String query = "SELECT count(*) as nb_computers FROM computer where name LIKE ? OR company_id IN (SELECT id FROM company where name LIKE ?)";

		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "%" + name + "%");
			pstmt.setString(2, "%" + name + "%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				nbEntries = rs.getInt("nb_computers");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

		return nbEntries;
	}

	@Override
	public List<Computer> getAll() {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();

		list = new ArrayList<>();
		String query = "SELECT * FROM computer ORDER BY name";

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Company c = null;
				Computer computer = null;
				long id = -1;
				id = rs.getLong("company_id");

				c = this.companyDAOImpl.getCompanyById(id);
				computer = ComputerMapper.map(rs, c);
				list.add(computer);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, stmt, null, connection, logger);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs, stmt, null, connection, logger);
		}

		return list;
	}

	@Override
	public Computer getComputerById(long id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();

		Computer computer = null;
		String query = "SELECT * FROM computer WHERE id = ?";

		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Company c = null;
				long idc = -1;
				idc = rs.getLong("company_id");

				c = this.companyDAOImpl.getCompanyById(idc);
				computer = ComputerMapper.map(rs, c);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

		return computer;
	}

	public Computer getComputerByName(String name) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();

		Computer computer = null;
		String query = "SELECT * FROM computer WHERE name = ?";

		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Company c = null;
				long idc = -1;

				idc = rs.getLong("company_id");

				c = this.companyDAOImpl.getCompanyById(idc);
				computer = ComputerMapper.map(rs, c);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}
		return computer;
	}

	/**
	 * Tries to insert a computer in the database
	 * 
	 * @param computer
	 *            the computer to insert
	 * @return
	 * @throws ComputerCreationException
	 */
	@Override
	public int createComputer(Computer computer) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();

		int nbRow = 0;
		Computer tmp = null;
		tmp = getComputerByName(computer.getName());
		if (tmp != null) {
			nbRow = -1;
		} else {
			String query = "INSERT INTO computer (name, introduced, discontinued) VALUES (?, ?, ?)";
			String intro = (computer.getIntro() != null) ? (computer.getIntro().toString()) : Time.TIMESTAMP_ZERO;
			String outro = (computer.getOutro() != null) ? (computer.getOutro().toString()) : Time.TIMESTAMP_ZERO;
			logger.info("Computer creation: \"INSERT INTO computer (name, introduced, discontinued) VALUES (" + computer.getName() + ", " + intro + ", " + outro + ")\"");
			boolean hasACompany = computer.hasACompany();
			if (hasACompany) {
				query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
				logger.info("Computer creation: \"INSERT INTO computer (name, introduced, discontinued) VALUES (" + computer.getName() + ", " + intro + ", " + outro + ", " + computer.getCompany().getId() + ")\"");
			}

			try {
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, computer.getName());
				pstmt.setString(2, intro);
				pstmt.setString(3, outro);
				if (hasACompany) {
					pstmt.setLong(4, computer.getCompany().getId());
				}
				nbRow = pstmt.executeUpdate();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				close(rs, null, pstmt, connection, logger);
				throw new DAOException(e.getMessage());
			} finally {
				close(rs, null, pstmt, connection, logger);
			}

		}
		return nbRow;
	}

	@Override
	public void updateComputer(Computer computer) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();

		String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?";
		int champs = 4;
		String intro = (computer.getIntro() != null) ? (computer.getIntro().toString()) : Time.TIMESTAMP_ZERO;
		String outro = (computer.getOutro() != null) ? (computer.getOutro().toString()) : Time.TIMESTAMP_ZERO;
		boolean hasACompany = computer.hasACompany();
		if (hasACompany) {
			query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
			champs = 5;
		}

		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, computer.getName());
			pstmt.setString(2, intro);
			pstmt.setString(3, outro);
			if (hasACompany) {
				pstmt.setLong(4, computer.getCompany().getId());
			}
			pstmt.setLong(champs, computer.getId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

	}

	@Override
	public void deleteComputer(long id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();
		logger.info("Computer deletion: \"DELETE FROM computer WHERE id = " + id + "\"");

		String query = "DELETE FROM computer WHERE id = ?";

		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

	}

	@Override
	public void deleteComputer(String name) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();
		logger.info("Computer deletion: \"DELETE FROM computer WHERE name = " + name + "\"");

		String query = "DELETE FROM computer WHERE name = ?";

		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

	}

	@Override
	public void deleteComputers(long[] listId, Connection connection) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "DELETE FROM computer WHERE id = ?";

		pstmt = connection.prepareStatement(query);
		for (long id : listId) {
			logger.info("Computer deletion: \"DELETE FROM computer WHERE id = " + id + "\"");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		}

		close(rs, null, pstmt, null, logger);
	}

	@Override
	public void deleteComputersWhereCompanyIdEquals(long id, Connection connection) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		logger.info("Computer deletion: \"DELETE FROM computer WHERE company_id = " + id + "\"");
		String query = "DELETE FROM computer WHERE company_id = ?";

		pstmt = connection.prepareStatement(query);
		pstmt.setLong(1, id);
		pstmt.executeUpdate();

		close(rs, null, pstmt, null, logger);
	}

	private static void close(ResultSet rs, Statement stmt, PreparedStatement pstmt, Connection connection,
			Logger logger) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			rs = null;
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			stmt = null;
		}

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			pstmt = null;
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			connection = null;
		}
	}
}