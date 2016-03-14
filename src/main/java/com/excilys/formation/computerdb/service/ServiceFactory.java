package com.excilys.formation.computerdb.service;

import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public enum ServiceFactory {
	INSTANCE;

	ComputerDatabaseServiceImpl computerDatabaseServiceImpl;

	private ServiceFactory() {
		computerDatabaseServiceImpl = ComputerDatabaseServiceImpl.INSTANCE;
	}

	public ComputerDatabaseService getService() {
		return computerDatabaseServiceImpl;
	}

}