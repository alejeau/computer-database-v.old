package com.excilys.formation.computerdb.ui;

import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

		Scanner sc = new Scanner(System.in);
		CLI cli = context.getBean(CLI.class);
		cli.setScanner(sc);
		cli.init();
		
		boolean keepOnRocking = true;

		while (keepOnRocking) {
			cli.showMenu();
			cli.makeAChoiceAndChecksIt();
			keepOnRocking = cli.launch();
		}

		sc.close();
		context.close();
	}
}
