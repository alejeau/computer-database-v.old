package com.excilys.formation.computerdb.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.service.impl.ComputerDatabaseServiceImpl;

@Controller
public class MainTmp {
	@Autowired
	ComputerDatabaseServiceImpl service;
	
	MainTmp() {}
	
	int getNb(){
		return service.getNbCompanies();
	}
	
	public List<Computer> test() {
		return service.test();
	}
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		MainTmp tmp = context.getBean(MainTmp.class);
//		int nb = tmp.getNb();
//		System.out.println(nb);
		List<Computer> list = tmp.test();
		
		System.out.println(list);
		
		context.close();
	}

}
