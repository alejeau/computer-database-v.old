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
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.persistence.ComputerDAO;
import com.excilys.formation.computerdb.persistence.ConnectionFactory;
import com.excilys.formation.computerdb.persistence.mapper.ComputerMapper;

public enum ComputerDAOImpl implements ComputerDAO {
	INSTANCE;
	//*
	private ConnectionFactory connectionFactory;
	protected final Logger logger = Logger.getLogger(ComputerDAOImpl.class);
	protected CompanyDAOImpl companyDAOImpl;
	protected Connection connection = null;
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
	protected List<Computer> list = null;

	private ComputerDAOImpl() {
		this.connectionFactory = ConnectionFactory.getInstance();
		this.connection = this.connectionFactory.getConnection();
		this.companyDAOImpl = CompanyDAOImpl.INSTANCE;
		this.list = new ArrayList<Computer>();
	}

	@Override
	public List<Computer> getFromTo(int from, int to) {
		list = new ArrayList<>();

		String query = "SELECT * FROM computer LIMIT ?, ?";

		try {
			pstmt = this.connection.prepareStatement(query);
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
				Company c = null;
				Computer computer = null;
				long id = -1;

				try {
					id = rs.getLong("company_id");
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(id);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}

				list.add(computer);
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
		int nbEntries = 0;
		String query = "SELECT COUNT(*) as nb_computers FROM computer";

		try {
			stmt = this.connection.createStatement();
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
					nbEntries = rs.getInt("nb_computers");
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
	public List<Computer> getAll() {
		list = new ArrayList<>();
		String query = "SELECT * FROM computer";

		try {
			stmt = this.connection.createStatement();
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
				Company c = null;
				Computer computer = null;
				long id = -1;

				try {
					id = rs.getLong("company_id");
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					rs.close();
					this.closeAll();
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(id);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}
				list.add(computer);
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
	public Computer getComputerById(long id) {
		Computer computer = null;
		String query = "SELECT * FROM computer WHERE id = ?";

		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setLong(1, id);
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

		// si l'entrée existe
		try {
			if (rs.next()) {
				Company c = null;
				long idc = -1;

				try {
					idc = rs.getLong("company_id");
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(idc);
				try {
					computer = ComputerMapper.map(rs, c);
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

		return computer;
	}

	public Computer getComputerByName(String name) {
		Computer computer = null;
		String query = "SELECT * FROM computer WHERE name = ?";

		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, name);
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

		// si l'entrée existe
		try {
			if (rs.next()) {
				Company c = null;
				long idc = -1;

				try {
					idc = rs.getLong("company_id");
				} catch (SQLException e) {
					logger.fatal(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(idc);
				try {
					computer = ComputerMapper.map(rs, c);
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

		return computer;
	}

	@Override
	public int createComputer(Computer computer) {
		int nbRow = 0;
		String query = "INSERT INTO computer (name, introduced, discontinued) VALUES (?, ?, ?)";
		String intro = (computer.getIntro() != null) ? (computer.getIntro().toString()) : Computer.TIMESTAMP_ZERO;
		String outro = (computer.getOutro() != null) ? (computer.getOutro().toString()) : Computer.TIMESTAMP_ZERO;
		boolean hasACompany = computer.hasACompany();
		if (hasACompany) {
			query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
		}


		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, computer.getName());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(2, intro);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(3, outro);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		if (hasACompany) {
			try {
				pstmt.setLong(4, computer.getCompany().getId());
			} catch (SQLException e) {
				logger.fatal(e.getMessage());
				this.closeAll();
				throw new DAOException(e.getMessage());
			}
		}
		try {
			nbRow = pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		this.closeAll();
		return nbRow;
	}

	@Override
	public void updateComputer(Computer computer) {
		String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?";
		int champs = 4;
		String intro = (computer.getIntro() != null) ? (computer.getIntro().toString()) : Computer.TIMESTAMP_ZERO;
		String outro = (computer.getOutro() != null) ? (computer.getOutro().toString()) : Computer.TIMESTAMP_ZERO;
		boolean hasACompany = computer.hasACompany();
		if (hasACompany) {
			query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
			champs = 5;
		}

		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, computer.getName());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(2, intro);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(3, outro);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		if (hasACompany) {
			try {
				pstmt.setLong(4, computer.getCompany().getId());
			} catch (SQLException e) {
				logger.fatal(e.getMessage());
				this.closeAll();
				throw new DAOException(e.getMessage());
			}
		}

		try {
			pstmt.setLong(champs, computer.getId());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		this.closeAll();
	}

	@Override
	public void deleteComputer(long id) {
		String query = "DELETE FROM computer WHERE id = ?";

		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setLong(1, id);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		this.closeAll();
	}

	@Override
	public void deleteComputer(String name) {
		String query = "DELETE FROM computer WHERE name = ?";

		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, name);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		this.closeAll();
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
}