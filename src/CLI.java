import java.sql.SQLException;
import java.util.Scanner;

import model.Queries;


public class CLI {
	
	public static void showMenu(){
		System.out.println("1) List computers");
		System.out.println("2) List companies");
		System.out.println("3) Show computer details");
		System.out.println("4) Create new computer");
		System.out.println("5) Update a computer");
		System.out.println("6) Delete a computer");
		System.out.println("7) Quit");
		
		System.out.println("What do you choose ? ");
	}
	
	public static void main(String[] args){
		
		Queries q = new Queries();
		Scanner sc = new Scanner(System.in);
		int choice;
		boolean keepOnRocking = true;
		
		while (keepOnRocking){
			CLI.showMenu();
			
			choice = sc.nextInt();
			System.out.println(choice);
			
			switch(choice){
			case 7:
				keepOnRocking = false;
				break;
			default:
				break;
			}
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
