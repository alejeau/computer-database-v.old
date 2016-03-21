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
	protected Connection connection = null;
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
	protected List<Computer> list = null;

	private ComputerDAOImpl() {
		this.connectionFactory = ConnectionFactoryImpl.getInstance();
		this.connection = this.connectionFactory.getConnection();
		this.companyDAOImpl = CompanyDAOImpl.INSTANCE;
		this.list = new ArrayList<Computer>();
	}
	
	@Override
	public
	boolean exists(String name){
		boolean exists = false;
		this.connection = this.connectionFactory.getConnection();
		
		list = new ArrayList<>();
		String query = "SELECT * FROM computer WHERE name=? ORDER BY name;";

		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, name);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		try {
			if (rs.next()) {
				exists = true;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		this.closeAll();

		return exists;
	}
	
	@Override
	public
	boolean exists(Computer computer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public
	List<Computer> getNamedFromTo(String name, int offset, int limit) {
		this.connection = this.connectionFactory.getConnection();
		
		list = new ArrayList<>();
		String query = "SELECT * FROM computer WHERE name LIKE ? ORDER BY name LIMIT ?, ?;";

		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, "%" + name + "%");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(2, offset);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(3, limit);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage());
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
					logger.error(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(id);
				try {
					this.closeAll();
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.error(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}

				list.add(computer);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		this.closeAll();

		return list;
	}

	@Override
	public List<Computer> getFromTo(int offset, int limit) {
		this.connection = this.connectionFactory.getConnection();
		
		list = new ArrayList<>();

		String query = "SELECT * FROM computer ORDER BY name LIMIT ?, ?";

		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(1, offset);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setInt(2, limit);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage());
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
					logger.error(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(id);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.error(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}

				list.add(computer);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		this.closeAll();

		return list;
	}



	@Override
	public int getNbEntries() {
		this.connection = this.connectionFactory.getConnection();
		
		int nbEntries = 0;
		String query = "SELECT COUNT(*) as nb_computers FROM computer";

		try {
			stmt = this.connection.createStatement();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		try {
			if (rs.next()) {
				try {
					nbEntries = rs.getInt("nb_computers");
				} catch (SQLException e) {
					logger.error(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		this.closeAll();

		return nbEntries;
	}

	@Override
	public int getNbEntriesNamed(String name) {
		this.connection = this.connectionFactory.getConnection();
		
		int nbEntries = 0;
		String query = "SELECT COUNT(*) as nb_computers FROM computer WHERE name LIKE ?;";

		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, "%" + name + "%");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		try {
			if (rs.next()) {
				try {
					nbEntries = rs.getInt("nb_computers");
				} catch (SQLException e) {
					logger.error(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		this.closeAll();

		return nbEntries;
	}

	@Override
	public List<Computer> getAll() {
		this.connection = this.connectionFactory.getConnection();
		
		list = new ArrayList<>();
		String query = "SELECT * FROM computer ORDER BY name";

		try {
			stmt = this.connection.createStatement();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
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
					logger.error(e.getMessage());
					rs.close();
					this.closeAll();
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(id);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.error(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}
				list.add(computer);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		this.closeAll();

		return list;
	}

	@Override
	public Computer getComputerById(long id) {
		this.connection = this.connectionFactory.getConnection();
		
		Computer computer = null;
		String query = "SELECT * FROM computer WHERE id = ?";

		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setLong(1, id);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage());
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
					logger.error(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(idc);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.error(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		this.closeAll();

		return computer;
	}

	public Computer getComputerByName(String name) {
		this.connection = this.connectionFactory.getConnection();
		
		Computer computer = null;
		String query = "SELECT * FROM computer WHERE name = ?";

		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, name);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage());
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
					logger.error(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}

				c = this.companyDAOImpl.getCompanyById(idc);
				try {
					computer = ComputerMapper.map(rs, c);
				} catch (SQLException e) {
					logger.error(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		this.closeAll();

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
		this.connection = this.connectionFactory.getConnection();
		
		int nbRow = 0;
		Computer tmp = null;		
		tmp = getComputerByName(computer.getName());
		
		if (tmp == null){
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
				pstmt = this.connection.prepareStatement(query);
			} catch (SQLException e) {
				logger.error(e.getMessage());
				this.closeAll();
				throw new DAOException(e.getMessage());
			}
			try {
				pstmt.setString(1, computer.getName());
			} catch (SQLException e) {
				logger.error(e.getMessage());
				this.closeAll();
				throw new DAOException(e.getMessage());
			}
			try {
				pstmt.setString(2, intro);
			} catch (SQLException e) {
				logger.error(e.getMessage());
				this.closeAll();
				throw new DAOException(e.getMessage());
			}
			try {
				pstmt.setString(3, outro);
			} catch (SQLException e) {
				logger.error(e.getMessage());
				this.closeAll();
				throw new DAOException(e.getMessage());
			}
			if (hasACompany) {
				try {
					pstmt.setLong(4, computer.getCompany().getId());
				} catch (SQLException e) {
					logger.error(e.getMessage());
					this.closeAll();
					throw new DAOException(e.getMessage());
				}
			}
			try {
				nbRow = pstmt.executeUpdate();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				this.closeAll();
				throw new DAOException(e.getMessage());
			}

			this.closeAll();
		}
		return nbRow;
	}

	@Override
	public void updateComputer(Computer computer) {
		this.connection = this.connectionFactory.getConnection();
		
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
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, computer.getName());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(2, intro);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(3, outro);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		if (hasACompany) {
			try {
				pstmt.setLong(4, computer.getCompany().getId());
			} catch (SQLException e) {
				logger.error(e.getMessage());
				this.closeAll();
				throw new DAOException(e.getMessage());
			}
		}

		try {
			pstmt.setLong(champs, computer.getId());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		this.closeAll();
	}

	@Override
	public void deleteComputer(long id) {
		this.connection = this.connectionFactory.getConnection();
		
		String query = "DELETE FROM computer WHERE id = ?";

		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setLong(1, id);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		this.closeAll();
	}

	@Override
	public void deleteComputer(String name) {
		this.connection = this.connectionFactory.getConnection();
		
		String query = "DELETE FROM computer WHERE name = ?";

		try {
			pstmt = this.connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.setString(1, name);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}
		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			this.closeAll();
			throw new DAOException(e.getMessage());
		}

		this.closeAll();
	}

	protected void closeAll() {
		if (this.rs != null) {
			try {
				this.rs.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			this.rs = null;
		}

		if (this.stmt != null) {
			try {
				this.stmt.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			this.stmt = null;
		}

		if (this.pstmt != null) {
			try {
				this.pstmt.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			this.pstmt = null;
		}
		
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			this.connection = null;
		}
	}
}