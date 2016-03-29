package com.excilys.formation.computerdb.thread;

import java.sql.Connection;

public enum ThreadLocalConnection {
	INSTANCE;
	
	private ThreadLocal<Connection> threadLocal = null;
	
	private ThreadLocalConnection() {
		threadLocal = new ThreadLocal<>();
	}
	
	public static ThreadLocalConnection getInstance() {
		return INSTANCE;
	}
	
	public void set(Connection connection) {
		this.threadLocal.set(connection);
	}
	
	public Connection get() {
		return this.threadLocal.get();
	}
	
	public void remove() {
		this.threadLocal.remove();
	}
}
