package com.exclilys.formation.computerdb.service;

import com.exclilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

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