package com.excilys.formation.computerdb.ui;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import com.excilys.formation.computerdb.model.User;
import com.excilys.formation.computerdb.service.impl.UserServiceImpl;

@Controller
public class UserManagement {

	@Autowired
	UserServiceImpl service;
	protected Scanner sc = null;
	protected int choice = -1;
	
	public UserManagement() {}
	

	public void setScanner(Scanner sc) {
		this.sc = sc;
	}
	

	/**
	 * Displays the main menu
	 */
	public void showMenu() {
		System.out.println("\n\n");
		System.out.println("***************************");
		System.out.println("******** Main Menu ********");
		System.out.println("***************************");
		System.out.println("1) Show user");
		System.out.println("2) Create user");
		System.out.println("3) Delete user");
		System.out.println("4) Quit");

		System.out.println("What do you chose ? ");
	}
	

	/**
	 * Waits for the user's choice
	 */
	public void makeAChoiceAndChecksIt() {
		String tmpChoice;
		tmpChoice = sc.next();
		sc.nextLine();
		System.out.println(tmpChoice);
		String j = "";
		int len = tmpChoice.length();
		if ((len > 0) && (len < 3)) {
			for (int i = 1; i < 5; i++) {
				j = String.valueOf(i);
				if (j.equals(tmpChoice)) {
					choice = i;
					break;
				}
			}
		} else {
			System.out.println("Choice not in menu!");
		}
	}

	/**
	 * Launches the right activity given the user's choice
	 * 
	 * @return whether to quit or not
	 * @throws NoSuchAlgorithmException 
	 */
	public boolean launch() throws NoSuchAlgorithmException {
		boolean b = true;
		switch (this.choice) {
		case 1: // List all computers
			showUser();
			break;
		case 2: // List computers by pages
			createUser();
			break;
		case 3: // List all companies
			deleteUser();
			break;
		case 4: // Quit
			b = false;
			break;
		default:
			break;
		}
		this.choice = -1;
		return b;
	}
	
	public void showUser() {
		String login = getLogin();
		User u = this.service.getUser(login);
		System.out.println(u);
	}

	public void createUser() throws NoSuchAlgorithmException {
		String [] creds = getInfo();
		String login = creds[0];
		String password = encode(creds[1]);
		
		User u = new User(login, password);
		this.service.createUser(u);
	}
	
	public void deleteUser() {
		String login = getLogin();
		this.service.deleteUser(login);
	}

	/**
	 * Gets infos about a computer from user's input :<br>
	 * Login<br>
	 * Password
	 * 
	 * @return an array of String containing the previously stated infos
	 */
	protected String getLogin() {
		String login = "";
		while (login.length() < 8) {
			System.out.println("Please enter user login (must be eight characters long minimum):");
			login = sc.nextLine();
		}

		return login;
	}

	/**
	 * Gets infos about a computer from user's input :<br>
	 * Login<br>
	 * Password
	 * 
	 * @return an array of String containing the previously stated infos
	 */
	protected String[] getInfo() {
		String[] infos = new String[]{"", ""};
		while (infos[0].length() < 8) {
			System.out.println("Please enter user login (must be eight characters long minimum):");
			infos[0] = sc.nextLine();
		}

		while (infos[1].length() < 8) {
			System.out.println("Please enter user password (must be eight characters long minimum):");
			infos[1] = sc.nextLine();
		}

		return infos;
	}
	
	private static String encode(final String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
	
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("file:../computer-database-webapp/src/main/resources/application-context.xml");

		Scanner sc = new Scanner(System.in);
		UserManagement manager = context.getBean(UserManagement.class);
		manager.setScanner(sc);

		boolean keepOnRocking = true;

		while (keepOnRocking) {
			manager.showMenu();
			manager.makeAChoiceAndChecksIt();
			try {
				keepOnRocking = manager.launch();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		sc.close();
		context.close();
	}

}
