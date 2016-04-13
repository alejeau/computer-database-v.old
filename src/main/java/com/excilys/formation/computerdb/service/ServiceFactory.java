package com.excilys.formation.computerdb.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

public enum ServiceFactory {
	INSTANCE;

	@Autowired
	ComputerDatabaseServiceImpl services;

	private ServiceFactory() {}

	public ComputerDatabaseService getService() {
		return services;
	}

}