package com.excilys.formation.computerdb.ui;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.CompanyPager;
import com.excilys.formation.computerdb.pagination.ComputerPager;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;


public class CLI {
	public static final String COMPUTER = "computer";
	public static final String COMPANY = "company";
	public static final String TIMESTAMP_ZERO = "0000-00-00";

	Scanner sc = null;

	ComputerDatabaseServiceImpl service;

	private int choice = -1;
	private ComputerPager computerPager = null;
	private CompanyPager  companyPager  = null;
	private List<Computer> computerList = null;
	private List<Company>  companyList  = null;

	/**
	 * Creates a CLI using a Scanner
	 * @param sc a Scanner
	 */
	public CLI(Scanner sc) {
		this.sc = sc;
		this.service = ComputerDatabaseServiceImpl.INSTANCE;
		this.companyPager = new CompanyPager(10);
		this.computerPager = new ComputerPager(10);
		
	}

	/**
	 * Displays the main menu
	 */
	public void showMenu() {
		System.out.println("\n\n");
		System.out.println("***************************");
		System.out.println("******** Main Menu ********");
		System.out.println("***************************");
		System.out.println("1) List all computers");
		System.out.println("2) List computers by pages");
		System.out.println("3) List all companies");
		System.out.println("4) List companies by pages");
		System.out.println("5) Show computer details");
		System.out.println("6) Create new computer");
		System.out.println("7) Update a computer");
		System.out.println("8) Delete a computer");
		System.out.println("9) Quit");

		System.out.println("What do you choose ? ");
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
		if (tmpChoice.length() == 1) {
			for (int i = 1; i < 10; i++) {
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
	 * @return whether to quit or not
	 */
	public boolean launch() {
		boolean b = true;
		switch (this.choice) {
		case 1: // List all computers
			displayAllComputers();
			break;
		case 2: // List computers by pages
			displayComputersByPages();
			break;
		case 3: // List all companies
			displayAllCompanies();
			break;
		case 4: // List companies by pages
			displayCompaniesByPages();
			break;
		case 5: // Show computer details
			whichComputer();
			break;
		case 6: // Create new computer
			createComputer();
			break;
		case 7: // Update a computer
			updateComputer();
			break;
		case 8: // Delete a computer
			deleteComputer();
			break;
		case 9: // Quit
			b = false;
			break;
		default:
			break;
		}
		this.choice = -1;
		return b;
	}

	/**
	 * Displays the list of all Computer or Companies
	 */
	protected void displayAllComputers() {
		List<Computer> comps = null;
		comps = service.getAllComputers();
		System.out.println("comps.size() : " + comps.size());
		for (Computer comp : comps) {
			System.out.println(comp.toString());
		}
	}

	/**
	 * Displays a list of Computer by pages
	 */
	protected void displayComputersByPages() {
		boolean keepAtIt = true;
		int c = -1;
		while (keepAtIt) {
			c = getPageChoice();
			if (c == 5) {
				break;
			} else {
				switch (c) {
				case 1: // First page
					try {
						this.computerPager.goToPageNumber(0);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					this.computerList = this.computerPager.getCurrentPage();
					break;
				case 2: // Previous page
					this.computerList = this.computerPager.getPreviousPage();
					break;
				case 3: // Next page
					this.computerList = this.computerPager.getNextPage();
					break;
				case 4:
					int pageNumber = getPageNumber();
					this.computerPager.goToPageNumber(pageNumber);
					this.computerList = this.computerPager.getCurrentPage();
					break;
				default:
					break;
				}

				for (Computer comp : this.computerList) {
					System.out.println(comp.toString());
				}
			}
		}
	}

	protected void displayAllCompanies() {
		List<Company> comps = service.getAllCompanies();
		for (Company comp : comps) {
			System.out.println(comp.toString());
		}
	}

	/**
	 * Displays a list of Company by pages
	 */
	protected void displayCompaniesByPages() {
		boolean keepAtIt = true;
		int c = -1;
		while (keepAtIt) {
			c = getPageChoice();
			if (c == 5) {
				break;
			} else {
				switch (c) {
				case 1: // First page
					this.companyPager.goToPageNumber(0);
					this.companyList = this.companyPager.getCurrentPage();
					break;
				case 2: // Previous page
					this.companyList = this.companyPager.getPreviousPage();
					break;
				case 3: // Next page
					this.companyList = this.companyPager.getNextPage();
					break;
				case 4:
					int pageNumber = getPageNumber();
					this.companyPager.goToPageNumber(pageNumber);
					this.companyList = this.companyPager.getCurrentPage();
					break;
				default:
					break;
				}

				for (Company comp : this.companyList) {
					System.out.println(comp.toString());
				}
			}
		}
	}

	/**
	 * Gets the user's choice of page
	 * @return the user's choice
	 */
	protected int getPageChoice() {
		int c = -1;
		System.out.println("\n*** Page selection menu ***");
		System.out.println("1) First page");
		System.out.println("2) Previous page");
		System.out.println("3) Next page");
		System.out.println("4) Custom page number");
		System.out.println("5) Quit");

		c = sc.nextInt();
		sc.nextLine();

		return c;
	}

	/**
	 * Gets the user's choice for page number
	 * @return the number of pages
	 */
	protected int getPageNumber() {
		int p = -1;
		System.out.println("To which page would you like to go?");
		p = sc.nextInt();
		sc.nextLine();
		return p;
	}

	/**
	 * Displays the infos of the desired computer
	 */
	protected void whichComputer() {
		System.out.println("Which computer would you like to be detailed?");
		String name = null;
		name = this.sc.nextLine();
		System.out.println();
		Computer c = null;
		c = service.getComputerByName(name);
		if (c != null) {
			System.out.println(c.toString());
		} else {
			System.out.println("The specicified computer does not exists!");
		}
	}

	/**
	 * Creates a computer
	 */
	protected void createComputer() {
		System.out.println("Computer creation menu");
		String[] infos = getInfo();
		if (infos[0].length() == 0) {
			System.out.println("Error! No name given!");
		} else {
			Company cy = service.getCompanyByName(infos[3]);
			service.createComputer(new Computer(infos[0], infos[1], infos[2], cy));
		}
	}

	/**
	 * Updates a given computer
	 */
	protected void updateComputer() {
		System.out.println("Computer update menu");
		String[] infos = getInfo();
		Computer computer = null;

		if (infos[0].length() == 0) {
			System.out.println("Error! No name given!");
		} else {
			computer = this.service.getComputerByName(infos[0]);
			computer.setIntro(infos[1]);
			computer.setOutro(infos[2]);
			Company cy = this.service.getCompanyByName(infos[3]);
			computer.setCompany(cy);
			this.service.updateComputer(computer);
		}
	}

	/**
	 * Deletes a given computer
	 */
	protected void deleteComputer() {
		System.out.println("Computer deletion menu");
		System.out.println("Please specify the name of the computer you want to delete");
		String name = sc.nextLine();
		this.service.deleteComputer(name);

	}

	/**
	 * Gets infos about a computer from user's input :<br>
	 * Name<br>
	 * Date of introduction<br>
	 * Date of production stop<br>
	 * Company name
	 * @return an array of String containing the previously stated infos
	 */
	protected String[] getInfo() {
		String[] infos = new String[4];
		System.out.println("Please enter computer name :");
		infos[0] = sc.nextLine();

		System.out.println("Please enter computer computer introduction date (YYYY-MM-DD) :");
		infos[1] = sc.nextLine();
		infos[1] = CLI.validateDate(infos[1]);

		System.out.println("Please enter computer computer discontinuation date (YYYY-MM-DD) :");
		infos[2] = sc.nextLine();
		infos[2] = CLI.validateDate(infos[2]);

		System.out.println("Please enter company name :");
		infos[3] = sc.nextLine();

		return infos;
	}

	/**
	 * Checks whether a date is in a valid format or not
	 * @param date a String representing the date
	 * @return the date or CLI2.TIMESTAMP_ZERO if the date's wrong
	 */
	protected static String validateDate(String date) {
		LocalDate ld = null;
		if (date.length() == 10) {
			String[] s = date.split("-");
			if (s.length != 3) {
				System.out.println("Wrong date format!\nThe date will be set to null.");
				date = CLI.TIMESTAMP_ZERO;
			} else if (!date.equals(CLI.TIMESTAMP_ZERO)) {
				int year 	= Integer.valueOf(s[0]);
				int month 	= Integer.valueOf(s[1]);
				int day 	= Integer.valueOf(s[2]);
				ld = LocalDate.of(year, month, day);
				date = ld.toString();
			}
		}
		return date;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		CLI cli = new CLI(sc);
		boolean keepOnRocking = true;

		while (keepOnRocking) {
			cli.showMenu();
			cli.makeAChoiceAndChecksIt();
			keepOnRocking = cli.launch();
		}
		sc.close();
	}
}
