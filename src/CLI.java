import java.sql.SQLException;

import model.Queries;

public class CLI {
	public static void main(String[] args){
		
		Queries q = new Queries();
		
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
		//*
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
