package ui;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import model.Queries;


public class CLI {

	Queries q = null;
	Scanner sc = null;
	int choice = -1;
	int activePageNumber = 0;

	/**
	 * Creates a CLI using a Queries and a Scanner
	 * @param q
	 * @param sc
	 */
	public CLI(Queries q, Scanner sc){
		this.q = q;
		this.sc = sc;
	}

	/**
	 * Displays the main menu
	 */
	public void showMenu(){
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
	public void makeAChoiceAndChecskIt(){
		String tmpChoice;
		tmpChoice = sc.next();
		sc.nextLine();
		System.out.println(tmpChoice);
		String j = "";
		if (tmpChoice.length() == 1){
			for (int i = 1; i < 10; i++){
				j = String.valueOf(i);
				if (j.equals(tmpChoice)){
					choice = i;
					break;
				}
			}
		}
		else 
			System.out.println("Choice not in menu!");
	}

	/**
	 * Launches the right activity given the user's choice 
	 * @return whether to quit or not
	 */
	public boolean launch(){
		boolean b = true;
		switch(this.choice){
		case 1: // List all computers
			listAll(Queries.COMPUTER);
			break;
		case 2: // List computers by pages
			listByPages(Queries.COMPUTER);
			break;
		case 3: // List all companies
			listAll(Queries.COMPANY);
			break;
		case 4: // List companies by pages
			listByPages(Queries.COMPANY);
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
	 * @param type Queries.COMPUTER or Queries.COMPANY
	 */
	protected void listAll(String type){
		try {
			q.listAll(type);
		} catch (SQLException e) {
			System.out.println("Couldn't list " + type + "!");
			e.printStackTrace();
		}
	}

	/**
	 * Displays a list of Computer or Companies by pages
	 * @param type Queries.COMPUTER or Queries.COMPANY
	 */
	protected void listByPages(String type){
		boolean keepAtIt = true;
		int c = -1;
		while (keepAtIt){
			c = getPageChoice();
			if (c == 5)
				break;
			else {
				switch (c){
				case 1: // First page
					this.activePageNumber = 0;
					break;
				case 2: // Previous page
					this.activePageNumber--;
					if (this.activePageNumber < 0)
						this.activePageNumber = 0;
					break;
				case 3: // Previous page
					this.activePageNumber++;
					break;
				case 4:
					this.activePageNumber = getPageNumber();
					break;
				default:
					break;
				}
				try {
					q.listByPages(type, activePageNumber);
				} catch (SQLException e) {
					System.out.println("Could'nt list computers!");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Gets the user's choice of page
	 * @return the user's choice
	 */
	protected int getPageChoice(){
		int c = -1;
		System.out.println("\n*** Page selection menu ***");
		System.out.println("1) First page");
		System.out.println("2) Previous page");
		System.out.println("3) Next page");
		System.out.println("4) Custom page number");
		System.out.println("5) Quit");
		System.out.println("Current selected page : " + this.activePageNumber);

		c = sc.nextInt();
		sc.nextLine();

		return c;
	}

	/**
	 * Gets the user's choice for page number
	 * @return
	 */
	protected int getPageNumber(){
		int p = -1;
		System.out.println("To which page would you like to go?");
		p = sc.nextInt();
		sc.nextLine();
		return p;
	}

	/**
	 * Displays the infos of the desired computer
	 */
	protected void whichComputer(){
		System.out.println("Which computer would you like to be detailed?");
		String name = null;
		name = this.sc.nextLine();
		System.out.println();

		// We get the id of the computer
		int id = -1;
		try {
			id = q.getComputerId(name);
		} catch (SQLException e) {
			System.out.println("Couldn't get computerId");
			e.printStackTrace();
		}

		// if there's no id, there's nothing to show
		if (id == -1){
			System.out.println("The computer does not exists!");
		}
		else { // else, we print the infos out
			try {
				q.showDetails(name);
			} catch (SQLException e) {
				System.out.println("Error while getting computer \"" + name + "\" details!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates a computer
	 */
	protected void createComputer(){
		System.out.println("Computer creation menu");
		String[] infos = getInfo();
		if (infos[0].length() == 0)
			System.out.println("Error! No name given!");
		else
			q.createComputer(infos[0], infos[1], infos[2], infos[3]);
	}

	/**
	 * Updates a given computer
	 */
	protected void updateComputer(){
		System.out.println("Computer update menu");
		String[] infos = getInfo();
		int id = -1;
		try {
			id = q.getComputerId(infos[0]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (id != -1)
			q.updateComputer(id, infos[0], infos[1], infos[2], infos[3]);
		else
			System.out.println("Computer doesn't exists in the database!");
	}

	/**
	 * Delete a given computer
	 */
	protected void deleteComputer(){
		System.out.println("Computer deletion menu");
		System.out.println("Please specify the name of the computer you want to delete");
		String name = sc.nextLine();
		q.deleteComputer(name);

	}

	/**
	 * Gets infos about a computer from user's input :<br>
	 * Name<br>
	 * Date of introduction<br>
	 * Date of discontinuation<br>
	 * Date of company name
	 * @return an array of String containing the previously stated infos
	 */
	protected String[] getInfo(){
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
	 * @return the date or Queries.TIMESTAMP_ZERO if the date's wrong
	 */
	protected static String validateDate(String date){
		LocalDate ld = null;
		if (date.length() == 10){
			String[] s = date.split("-");
			if (s.length != 3){
				System.out.println("Wrong date format!\nThe date will be set to null.");
				date = Queries.TIMESTAMP_ZERO;
			}
			else if (!date.equals(Queries.TIMESTAMP_ZERO)){
				int year 	= Integer.valueOf(s[0]);
				int month 	= Integer.valueOf(s[1]);
				int day 	= Integer.valueOf(s[2]);
				ld = LocalDate.of(year, month, day);
				date = ld.toString();
			}
		}
		return date;
	}



	/**
	 * Checks whether a date is in a valid format or not
	 * @param date a String representing the date
	 * @return the date or Queries.TIMESTAMP_ZERO if the date's wrong
	 */
	/*
	protected static String validateDate(String date){
		String d = Queries.TIMESTAMP_ZERO;;
		if (date.length() == 10){
			String[] s = date.split("-");
			if ((s.length == 3) && (s[0].length() == 4) && (s[1].length() == 2) && (s[2].length() == 2)){
				if (s[0].compareTo("1822") < 0)
					d = Queries.TIMESTAMP_ZERO;
				else if ((s[1].compareTo("12") > 0) || (s[1].compareTo("1") < 0))
					d = Queries.TIMESTAMP_ZERO;
				else if ((s[2].compareTo("31") > 0) || (s[2].compareTo("1") > 0))
					d = Queries.TIMESTAMP_ZERO;
				else
					d = date;
			}
		}
		if (d.equals(Queries.TIMESTAMP_ZERO))
			System.out.println("Wrong date format!");
		return d;
	}
	//*/

	//*/

	public static void main(String[] args){
		//*
		Queries q = new Queries();
		Scanner sc = new Scanner(System.in);
		CLI cli = new CLI(q, sc);
		boolean keepOnRocking = true;

		while (keepOnRocking){
			cli.showMenu();
			cli.makeAChoiceAndChecskIt();
			keepOnRocking = cli.launch();
		}
		sc.close();
		//*/
		/*
		SimpleDateFormat smd = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		try {
			d = smd.parse("1992-04-13");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(d.toString());
		Timestamp tmstp = new Timestamp(d.getTime());try {
			d = smd.parse("1992-13-32");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(d.toString());
		java.time.format.DateTimeFormatter jdt = new java.time.format.DateTimeFormatter(); 
		//*/
		/*
		LocalDate ld = LocalDate.of(1992, 04, 12);
		System.out.println(ld.toString());
		//*/

	}
}
