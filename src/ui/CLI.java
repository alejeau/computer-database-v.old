package ui;
import java.sql.SQLException;
import java.util.Scanner;

import model.Queries;


public class CLI {

	Queries q = null;
	Scanner sc = null;
	int choice = -1;

	public CLI(Queries q, Scanner sc){
		this.q = q;
		this.sc = sc;
	}

	public void showMenu(){
		System.out.println("\n\n");
		System.out.println("***************************");
		System.out.println("******** Main Menu ********");
		System.out.println("***************************");
		System.out.println("1) List computers");
		System.out.println("2) List companies");
		System.out.println("3) Show computer details");
		System.out.println("4) Create new computer");
		System.out.println("5) Update a computer");
		System.out.println("6) Delete a computer");
		System.out.println("7) Quit");

		System.out.println("What do you choose ? ");
	}

	public void makeAChoiceAndCheckIt(){
		String tmpChoice;
		tmpChoice = sc.next();
		sc.nextLine();
		System.out.println(tmpChoice);
		String j = "";
		if (tmpChoice.length() == 1){
			for (int i = 1; i < 8; i++){
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

	public boolean launch(){
		boolean b = true;
		switch(this.choice){
		case 1: // List computers
			try {
				q.listComputers();
			} catch (SQLException e) {
				System.out.println("Could'nt list computers!");
				e.printStackTrace();
			}
			break;
		case 2: // List companies
			try {
				q.listCompanies();
			} catch (SQLException e) {
				System.out.println("Could'nt list companies!");
				e.printStackTrace();
			}
			break;
		case 3: // Show computer details
			whichComputer();
			break;
		case 4: // Create new computer
			createComputer();
			break;
		case 5: // Update a computer
			updateComputer();
			break;
		case 6: // Delete a computer
			deleteComputer();
			break;
		case 7: // Quit
			b = false;
			break;
		default:
			break;
		}
		return b;
	}

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
			// TODO Auto-generated catch block
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

	protected void createComputer(){
		System.out.println("Computer creation menu");
		String[] infos = getInfo();
		if (infos[0].length() == 0)
			System.out.println("Error! No name given!");
		else
			q.createComputer(infos[0], infos[1], infos[2], infos[3]);
	}

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

	protected void deleteComputer(){
		System.out.println("Computer deletion menu");
		System.out.println("Please specify the name of the computer you want to delete");
		String name = sc.nextLine();
		q.deleteComputer(name);

	}

	protected String[] getInfo(){
		String[] infos = new String[4];
		System.out.println("Please enter computer name :");
		infos[0] = sc.nextLine();

		System.out.println("Please enter computer computer introduction date (YYYY-MM-DD) :");
		infos[1] = sc.nextLine();

		System.out.println("Please enter computer computer discontinuation date (YYYY-MM-DD) :");
		infos[2] = sc.nextLine();

		System.out.println("Please enter company name :");
		infos[3] = sc.nextLine();

		return infos;
	}

	public static void main(String[] args){

		Queries q = new Queries();
		Scanner sc = new Scanner(System.in);
		CLI cli = new CLI(q, sc);
		boolean keepOnRocking = true;

		while (keepOnRocking){
			cli.showMenu();
			cli.makeAChoiceAndCheckIt();
			keepOnRocking = cli.launch();
		}
		sc.close();
		/*
		try {
			q.listCompanies();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
//		q.showDetails("\"Apple II\"");
		try {
			q.showDetails("iMac");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			q.showDetails("Apple II");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			System.out.println("Company id : " + q.getCompanyId("test"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//*/
		/*
		int id = -1;
		try {
			id = q.getComputerId("Testouille");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int res = -1;
		if (id != -1)
			res = q.updateComputer(id, "Testouille", "2016-03-09", "2016-03-10", "Testy");
		System.out.println("res = " + res);
		try {
			System.out.println("Company id 'Testy': " + q.getCompanyId("Testy"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			q.showDetails("Testouille");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//*/

	}
}
