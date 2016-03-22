package com.excilys.formation.computerdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

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
	protected final Logger logger = Logger.getLogger(ComputerDAOImpl.class);
	protected CompanyDAOImpl companyDAOImpl;
	protected List<Computer> list = null;

	private ComputerDAOImpl() {
		this.connectionFactory = ConnectionFactoryImpl.getInstance();
		this.companyDAOImpl = CompanyDAOImpl.INSTANCE;
		this.list = new ArrayList<Computer>();
	}
	
	@Override
	public
	boolean exists(String name){
		boolean exists = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();
		
		list = new ArrayList<>();
		String query = "SELECT * FROM computer WHERE name=? ORDER BY name;";

		try {
			pstmt = connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, name);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		try {
			if (rs.next()) {
				exists = true;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		close(rs, null, pstmt, connection, logger);

		return exists;
	}
	
	@Override
	public
	boolean exists(Computer computer) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("resource")
	@Override
	public
	List<Computer> getNamedFromTo(String name, int offset, int limit) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();
		
		list = new ArrayList<>();
//		String query = "SELECT * FROM computer WHERE name LIKE ? ORDER BY name LIMIT ?, ?;";
		String query = "SELECT * FROM computer where name LIKE ? OR company_id IN (SELECT id FROM company where name LIKE ?) ORDER BY name LIMIT ? OFFSET ?";

		try {
			pstmt = connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, "%" + name + "%");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(2, "%" + name + "%");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(3, limit);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(4, offset);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		try {
			while (rs.next()) {
				Company c = null;
				Computer computer = null;
				long id = -1;

				try {
					id = rs.getLong("company_id");
				} catch (SQLException e) {
					logger.error(e.getMessage());
					close(rs, null, pstmt, connection, logger);
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(id);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.error(e.getMessage());
					close(rs, null, pstmt, connection, logger);
					throw new DAOException(e.getMessage());
				}

				list.add(computer);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		close(rs, null, pstmt, connection, logger);

		return list;
	}

	@SuppressWarnings("resource")
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
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(1, offset);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(2, limit);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		try {
			while (rs.next()) {
				Company c = null;
				Computer computer = null;
				long id = -1;

				try {
					id = rs.getLong("company_id");
				} catch (SQLException e) {
					logger.error(e.getMessage());
					close(rs, null, pstmt, connection, logger);
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(id);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.error(e.getMessage());
					close(rs, null, pstmt, connection, logger);
					throw new DAOException(e.getMessage());
				}

				list.add(computer);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		close(rs, null, pstmt, connection, logger);

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
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		try {
			if (rs.next()) {
				try {
					nbEntries = rs.getInt("nb_computers");
				} catch (SQLException e) {
					logger.error(e.getMessage());
					close(rs, null, pstmt, connection, logger);
					throw new DAOException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		close(rs, null, pstmt, connection, logger);

		return nbEntries;
	}

	@Override
	public int getNbEntriesNamed(String name) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();
		
		int nbEntries = 0;
//		String query = "SELECT COUNT(*) as nb_computers FROM computer WHERE name LIKE ?;";
		String query = "SELECT count(*) as nb_computers FROM computer where name LIKE ? OR company_id IN (SELECT id FROM company where name LIKE ?)";

		try {
			pstmt = connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, "%" + name + "%");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(2, "%" + name + "%");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		try {
			if (rs.next()) {
				try {
					nbEntries = rs.getInt("nb_computers");
				} catch (SQLException e) {
					logger.error(e.getMessage());
					close(rs, null, pstmt, connection, logger);
					throw new DAOException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		close(rs, null, pstmt, connection, logger);

		return nbEntries;
	}

	@SuppressWarnings("resource")
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
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, stmt, null, connection, logger);
			throw new DAOException(e.getMessage());
		}

		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, stmt, null, connection, logger);
			throw new DAOException(e.getMessage());
		}

		try {
			while (rs.next()) {
				Company c = null;
				Computer computer = null;
				long id = -1;

				try {
					id = rs.getLong("company_id");
				} catch (SQLException e) {
					logger.error(e.getMessage());
					close(rs, stmt, null, connection, logger);
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(id);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.error(e.getMessage());
					close(rs, stmt, null, connection, logger);
					throw new DAOException(e.getMessage());
				}
				list.add(computer);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, stmt, null, connection, logger);
			throw new DAOException(e.getMessage());
		}
		close(rs, stmt, null, connection, logger);

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
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setLong(1, id);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		// si l'entrée existe
		try {
			if (rs.next()) {
				Company c = null;
				long idc = -1;

				try {
					idc = rs.getLong("company_id");
				} catch (SQLException e) {
					logger.error(e.getMessage());
					close(rs, null, pstmt, connection, logger);
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(idc);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.error(e.getMessage());
					close(rs, null, pstmt, connection, logger);
					throw new DAOException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		close(rs, null, pstmt, connection, logger);

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
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, name);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		// si l'entrée existe
		try {
			if (rs.next()) {
				Company c = null;
				long idc = -1;

				try {
					idc = rs.getLong("company_id");
				} catch (SQLException e) {
					logger.error(e.getMessage());
					close(rs, null, pstmt, connection, logger);
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(idc);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.error(e.getMessage());
					close(rs, null, pstmt, connection, logger);
					throw new DAOException(e.getMessage());
				}
			}
			else 
				System.out.println("No entry!");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		close(rs, null, pstmt, connection, logger);
		return computer;
	}

	/**
	 * Tries to insert a computer in the database
	 * @param computer the computer to insert
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
		if (tmp != null){
			nbRow = -1;
		} else {
			String query = "INSERT INTO computer (name, introduced, discontinued) VALUES (?, ?, ?)";
			String intro = (computer.getIntro() != null) ? (computer.getIntro().toString()) : Time.TIMESTAMP_ZERO;
			String outro = (computer.getOutro() != null) ? (computer.getOutro().toString()) : Time.TIMESTAMP_ZERO;
			boolean hasACompany = computer.hasACompany();
			if (hasACompany) {
				query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
			}
			
			try {
				pstmt = connection.prepareStatement(query);
			} catch (SQLException e) {
				logger.error(e.getMessage());
				close(rs, null, pstmt, connection, logger);
				throw new DAOException(e.getMessage());
			}
			try {
				pstmt.setString(1, computer.getName());
			} catch (SQLException e) {
				logger.error(e.getMessage());
				close(rs, null, pstmt, connection, logger);
				throw new DAOException(e.getMessage());
			}
			try {
				pstmt.setString(2, intro);
			} catch (SQLException e) {
				logger.error(e.getMessage());
				close(rs, null, pstmt, connection, logger);
				throw new DAOException(e.getMessage());
			}
			try {
				pstmt.setString(3, outro);
			} catch (SQLException e) {
				logger.error(e.getMessage());
				close(rs, null, pstmt, connection, logger);
				throw new DAOException(e.getMessage());
			}
			if (hasACompany) {
				try {
					pstmt.setLong(4, computer.getCompany().getId());
				} catch (SQLException e) {
					logger.error(e.getMessage());
					close(rs, null, pstmt, connection, logger);
					throw new DAOException(e.getMessage());
				}
			}
			try {
				nbRow = pstmt.executeUpdate();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				close(rs, null, pstmt, connection, logger);
				throw new DAOException(e.getMessage());
			}

			close(rs, null, pstmt, connection, logger);
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
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, computer.getName());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(2, intro);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(3, outro);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		if (hasACompany) {
			try {
				pstmt.setLong(4, computer.getCompany().getId());
			} catch (SQLException e) {
				logger.error(e.getMessage());
				close(rs, null, pstmt, connection, logger);
				throw new DAOException(e.getMessage());
			}
		}

		try {
			pstmt.setLong(champs, computer.getId());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		close(rs, null, pstmt, connection, logger);
	}

	@Override
	public void deleteComputer(long id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();
		
		String query = "DELETE FROM computer WHERE id = ?";

		try {
			pstmt = connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setLong(1, id);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		close(rs, null, pstmt, connection, logger);
	}

	@Override
	public void deleteComputer(String name) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = this.connectionFactory.getConnection();
		
		String query = "DELETE FROM computer WHERE name = ?";

		try {
			pstmt = connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, name);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			close(rs, null, pstmt, connection, logger);
			throw new DAOException(e.getMessage());
		}

		close(rs, null, pstmt, connection, logger);
	}

	private static void close(ResultSet rs, Statement stmt, PreparedStatement pstmt, Connection connection, Logger logger) {
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