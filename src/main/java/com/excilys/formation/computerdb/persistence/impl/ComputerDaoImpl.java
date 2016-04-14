package com.excilys.formation.computerdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.computerdb.constants.Fields;
import com.excilys.formation.computerdb.constants.Time;
import com.excilys.formation.computerdb.exceptions.CompanyDaoException;
import com.excilys.formation.computerdb.exceptions.ComputerCreationException;
import com.excilys.formation.computerdb.mapper.model.ComputerMapper;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.persistence.ComputerDao;

@Repository
public class ComputerDaoImpl implements ComputerDao {
	@Autowired
	private DataSource dataSource;
	
	protected final Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);
	protected List<Computer> list = null;
	
	@Autowired
	protected ComputerMapper compMap;
	
	@Autowired
	protected CompanyDaoImpl companyDAOImpl;
	
	protected ComputerDaoImpl() {
	}

	@Override
	public boolean exists(String name) {
		boolean exists = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		list = new ArrayList<>();
		String query = "SELECT * FROM computer WHERE name=? ORDER BY name;";
		logger.debug(query);

		try {
			connection = this.dataSource.getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				exists = true;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new CompanyDaoException(e.getMessage());
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
		return getNamedFromToSortedBy(name, offset, limit, Fields.NONE, true);
	}

	@Override
	public List<Computer> getNamedFromToSortedBy(String name, int offset, int limit, Fields field, boolean ascending) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int offsetNbr = 1;
		int limitNbr = 2;

		list = new ArrayList<>();
		String query = "SELECT * FROM computer WHERE name LIKE ? OR company_id IN (SELECT id FROM company WHERE name LIKE ?)";
		if (field != Fields.NONE) {
			String order = (ascending) ? "ASC" : "DESC";
			if (field == Fields.COMPANY) {
				query = "SELECT * FROM computer LEFT JOIN company on computer.company_id=company.id WHERE computer.name LIKE ? OR company_id IN (SELECT id FROM company WHERE name LIKE ?)";
			}
			query += " ORDER BY " + field.toString() + " " + order;

			offsetNbr = 3;
			limitNbr = 4;
		}
		query += " LIMIT ?, ?;";
		logger.debug(query);

		try {
			connection = this.dataSource.getConnection();
			pstmt = connection.prepareStatement(query);
			if (field != Fields.NONE) {
				pstmt.setString(1, "%" + name + "%");
				pstmt.setString(2, "%" + name + "%");
			}
			pstmt.setInt(offsetNbr, offset);
			pstmt.setInt(limitNbr, limit);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Company c = null;
				Computer computer = null;
				long id = -1;
				id = rs.getLong("company_id");

				c = this.companyDAOImpl.getCompanyById(id);
				computer = compMap.map(rs, c);

				list.add(computer);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new CompanyDaoException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

		return list;
	}

	@Override
	public List<Computer> getFromTo(int offset, int limit) {
		return getFromToSortedBy(offset, limit, Fields.NONE, true);
	}

	@Override
	public List<Computer> getFromToSortedBy(int offset, int limit, Fields field, boolean ascending) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		list = new ArrayList<>();

		String query = "SELECT * FROM computer";
		if (field != Fields.NONE) {
			String order = (ascending) ? "ASC" : "DESC";
			if (field == Fields.COMPANY) {
				query += " LEFT JOIN company on computer.company_id=company.id";
			}
			query += " ORDER BY " + field.toString() + " " + order;
		}
		query += " LIMIT ?, ?;";
		logger.debug(query);

		try {
			connection = this.dataSource.getConnection();
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
				computer = compMap.map(rs, c);

				list.add(computer);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new CompanyDaoException(e.getMessage());
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

		int nbEntries = 0;
		String query = "SELECT COUNT(*) as nb_computers FROM computer";
		logger.debug(query);

		try {
			connection = this.dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				nbEntries = rs.getInt("nb_computers");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new CompanyDaoException(e.getMessage());
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

		int nbEntries = 0;
		String query = "SELECT count(*) as nb_computers FROM computer where name LIKE ? OR company_id IN (SELECT id FROM company where name LIKE ?)";
		logger.debug(query);

		try {
			connection = this.dataSource.getConnection();
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
			throw new CompanyDaoException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

		return nbEntries;
	}

	@Override
	public List<Computer> getAll() {
		return getAllSortedBy(Fields.NONE, true);
	}

	@Override
	public List<Computer> getAllSortedBy(Fields field, boolean ascending) {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		list = new ArrayList<>();
		String query = "SELECT * FROM computer";
		if (field != Fields.NONE) {
			String order = (ascending) ? "ASC" : "DESC";
			if (field == Fields.COMPANY) {
				query += " LEFT JOIN company on computer.company_id=company.id";
			}
			query += " ORDER BY " + field.toString() + " " + order + ";";
		}
		logger.debug(query);

		try {
			connection = this.dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Company c = null;
				Computer computer = null;
				long id = -1;
				id = rs.getLong("company_id");

				c = this.companyDAOImpl.getCompanyById(id);
				computer = compMap.map(rs, c);
				list.add(computer);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, stmt, null, connection, logger);
			throw new CompanyDaoException(e.getMessage());
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

		Computer computer = null;
		String query = "SELECT * FROM computer WHERE id = ?";
		logger.debug(query);

		try {
			connection = this.dataSource.getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Company c = null;
				long idc = -1;
				idc = rs.getLong("company_id");

				c = this.companyDAOImpl.getCompanyById(idc);
				computer = compMap.map(rs, c);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new CompanyDaoException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

		return computer;
	}

	public Computer getComputerByName(String name) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Computer computer = null;
		String query = "SELECT * FROM computer WHERE name = ?";
		logger.debug(query);

		try {
			connection = this.dataSource.getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Company c = null;
				long idc = -1;

				idc = rs.getLong("company_id");

				c = this.companyDAOImpl.getCompanyById(idc);
				computer = compMap.map(rs, c);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new CompanyDaoException(e.getMessage());
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

		int nbRow = 0;
		Computer tmp = null;
		tmp = getComputerByName(computer.getName());
		if (tmp != null) {
			nbRow = -1;
		} else {
			String query = "INSERT INTO computer (name, introduced, discontinued) VALUES (?, ?, ?)";
			String intro = (computer.getIntro() != null) ? (computer.getIntro().toString()) : Time.TIMESTAMP_ZERO;
			String outro = (computer.getOutro() != null) ? (computer.getOutro().toString()) : Time.TIMESTAMP_ZERO;
			logger.info("Computer creation: \"INSERT INTO computer (name, introduced, discontinued) VALUES ("
					+ computer.getName() + ", " + intro + ", " + outro + ")\"");
			boolean hasACompany = computer.hasACompany();
			if (hasACompany) {
				query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
				logger.info("Computer creation: \"INSERT INTO computer (name, introduced, discontinued) VALUES ("
						+ computer.getName() + ", " + intro + ", " + outro + ", " + computer.getCompany().getId()
						+ ")\"");
			}
			logger.debug(query);

			try {
				connection = this.dataSource.getConnection();
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
				throw new CompanyDaoException(e.getMessage());
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

		String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?";
		int champs = 4;
		String intro = (computer.getIntro() != null) ? (computer.getIntro().toString()) : Time.TIMESTAMP_ZERO;
		String outro = (computer.getOutro() != null) ? (computer.getOutro().toString()) : Time.TIMESTAMP_ZERO;
		boolean hasACompany = computer.hasACompany();
		if (hasACompany) {
			query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
			champs = 5;
		}
		logger.debug(query);

		try {
			connection = this.dataSource.getConnection();
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
			throw new CompanyDaoException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

	}

	@Override
	public void deleteComputer(long id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.info("Computer deletion: \"DELETE FROM computer WHERE id = " + id + "\"");

		String query = "DELETE FROM computer WHERE id = ?";
		logger.debug(query);

		try {
			connection = this.dataSource.getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new CompanyDaoException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

	}

	@Override
	public void deleteComputer(String name) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.info("Computer deletion: \"DELETE FROM computer WHERE name = " + name + "\"");

		String query = "DELETE FROM computer WHERE name = ?";
		logger.debug(query);

		try {
			connection = this.dataSource.getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new CompanyDaoException(e.getMessage());
		} finally {
			close(rs, null, pstmt, connection, logger);
		}

	}

	@Override
	@Transactional
	public void deleteComputers(long[] listId) throws SQLException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "DELETE FROM computer WHERE id = ?";
		logger.debug(query);

		connection = this.dataSource.getConnection();
		pstmt = connection.prepareStatement(query);
		for (long id : listId) {
			logger.info("Computer deletion: \"DELETE FROM computer WHERE id = " + id + "\"");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		}

		close(rs, null, pstmt, null, logger);
	}

	@Override
	@Transactional
	public void deleteComputersWhereCompanyIdEquals(long id) throws SQLException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		logger.info("Computer deletion: \"DELETE FROM computer WHERE company_id = " + id + "\"");
		String query = "DELETE FROM computer WHERE company_id = ?";
		logger.debug(query);

		connection = this.dataSource.getConnection();
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