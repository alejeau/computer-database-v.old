package com.excilys.formation.computerdb.ui;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.excilys.formation.computerdb.constants.Fields;
import com.excilys.formation.computerdb.dto.model.ComputerDto;
import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.model.pagination.Page;
import com.excilys.formation.computerdb.model.pagination.SortedPage;
import com.excilys.formation.computerdb.ui.pager.CompanyPager;
import com.excilys.formation.computerdb.ui.pager.SortedPageComputer;


@Controller
public class RestCLI {
	public static final String COMPUTER = "computer";
	public static final String COMPANY = "company";
	public static final String TIMESTAMP_ZERO = "0000-00-00";
	public static final String BASE_URL = "http://localhost:8080/computer-database";
	
	

	@Autowired
	SortedPageComputer spc;

	@Autowired
	CompanyPager cpager;

	protected Scanner sc = null;
	protected int choice = -1;
	protected SortedPage<Computer> computerSortedPage = null;
	protected Page<Company> companyPage = null;
	protected List<Computer> computerList = null;
	protected List<Company> companyList = null;
	protected ClientConfig config = null;
	protected Client client = null;
	protected WebTarget services = null;

	/**
	 * Creates a CLI using a Scanner
	 * 
	 * @param sc a Scanner
	 */
	public RestCLI() {
	}
	
	public void init() {
		config = new ClientConfig().register(JacksonFeature.class);
		client = ClientBuilder.newClient(config);
		services = client.target(getBaseURI());
		
		int objectsPerPage = 10;
		if (this.services == null) {
			System.out.println("this.service is null");
			throw new RuntimeException("this.service is null");
		}
		
		int nbEntries = getObject("/computers/total", Integer.class);
		
		int mpn = maxPageNumber(objectsPerPage, nbEntries);
		this.computerSortedPage = new SortedPage<>(null, 0, mpn, objectsPerPage, nbEntries, Fields.NAME, true);

		nbEntries = getObject("/companies/total", Integer.class);
		mpn = maxPageNumber(objectsPerPage, nbEntries);
		companyPage = new Page<>(companyList, 0, mpn, objectsPerPage, nbEntries);
	}

	protected int maxPageNumber(int objectsPerPage, int nbEntries) {
		int maxPageNumber = -1;
		double opp = (double) objectsPerPage;
		double nbe = (double) nbEntries;
		maxPageNumber = (int) Math.ceil(nbe / opp);
		return maxPageNumber;
	}

	/**
	 * Displays the main menu
	 */
	public void showMenu() {
		System.out.println("\n\n");
		System.out.println("***************************");
		System.out.println("******** Main Menu ********");
		System.out.println("***************************");
		System.out.println(" 1) List all computers");
		System.out.println(" 2) List computers by pages");
		System.out.println(" 3) List all companies");
		System.out.println(" 4) List companies by pages");
		System.out.println(" 5) Show computer details");
		System.out.println(" 6) Create new computer");
		System.out.println(" 7) Update a computer");
		System.out.println(" 8) Delete a computer");
		System.out.println(" 9) Delete a company and all its computers");
		System.out.println("10) Quit");

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
		int len = tmpChoice.length();
		if ((len > 0) && (len < 3)) {
			for (int i = 1; i < 11; i++) {
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
		case 9: // Delete a company and all computers related to it
			deleteCompany();
			break;
		case 10: // Quit
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
		List<ComputerDto> list = null;
		list = getObjectList("/computers/all", ComputerDto.class);
		
		for (ComputerDto c : list) {
			System.out.println(c.toString());
		}
	}

	/**
	 * Displays a list of Computer by pages
	 */
	protected void displayComputersByPages() {
		String[] params = getPage();

		final String URL = new StringBuilder("/computers/page").append(params[0]).append(params[1]).toString();
		
		List<ComputerDto> list = null;
		list = getObjectList(URL, ComputerDto.class);
		
		for (ComputerDto c : list) {
			System.out.println(c.toString());
		}
	}

	protected void displayAllCompanies() {
		List<Company> list = null;
		list = getObjectList("/companies/all", Company.class);
		
		for (Company c : list) {
			System.out.println(c.toString());
		}
	}

	/**
	 * Displays a list of Company by pages
	 */
	protected void displayCompaniesByPages() {
		String[] params = getPage();

		final String URL = createUrl("/companies/page", params);
		
		List<Company> list = null;
		list = getObjectList(URL, Company.class);
		
		for (Company c : list) {
			System.out.println(c.toString());
		}
	}

	/**
	 * Displays the infos of the desired computer
	 */
	protected void whichComputer() {
		String name = null;
		
		System.out.println("Which computer would you like to be detailed?");
		name = this.sc.nextLine();
		System.out.println();

		final String URL = createUrl("/computer/name", name);
		
		ComputerDto c = null;
		c = getObject(URL, ComputerDto.class);
		
		if (c != null) {
			System.out.println(c.toString());
		} else {
			System.out.println("The specified computer does not exists!");
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
			ComputerDto cdto = new ComputerDto(infos);
			boolean success = addComputer("/computer/add", cdto);
			if (success) {
				System.out.println("Computer successfully created!");
			} else {
				System.out.println("Error while creating!");
			}
		}
	}

	/**
	 * Updates a given computer
	 */
	protected void updateComputer() {
		System.out.println("Computer update menu");
		String[] infos = getInfo();
		if (infos[0].length() == 0) {
			System.out.println("Error! No name given!");
		} else {
			ComputerDto cdto = new ComputerDto(infos);
			boolean success = updateComputer("/computer/update", cdto);
			if (success) {
				System.out.println("Computer successfully created!");
			} else {
				System.out.println("Error while creating!");
			}
		}
	}

	/**
	 * Deletes a given computer
	 */
	protected void deleteComputer() {
		System.out.println("Computer deletion menu");
		System.out.println("Please specify the ID of the computer you want to delete");
		String id = sc.nextLine();
		String URL = createUrl("/computer/del", id);
		delObject(URL);
	}

	protected void deleteCompany() {
		System.out.println("Company deletion menu");
		System.out.println("Please specify the ID of the company you want to delete"
				+ " (and all its affiliated computers)");
		String id = sc.nextLine();
		String URL = createUrl("/company/del", id);
		delObject(URL);
	}

	/**
	 * Gets infos about a computer from user's input :<br>
	 * Name<br>
	 * Date of introduction<br>
	 * Date of production stop<br>
	 * Company name
	 * 
	 * @return an array of String containing the previously stated infos
	 */
	protected String[] getInfo() {
		String[] infos = new String[4];
		System.out.println("Please enter computer name :");
		infos[0] = sc.nextLine();

		System.out.println("Please enter computer computer introduction date (YYYY-MM-DD) :");
		infos[1] = sc.nextLine();
		infos[1] = RestCLI.validateDate(infos[1]);

		System.out.println("Please enter computer computer discontinuation date (YYYY-MM-DD) :");
		infos[2] = sc.nextLine();
		infos[2] = RestCLI.validateDate(infos[2]);

		System.out.println("Please enter company name :");
		infos[3] = sc.nextLine();

		return infos;
	}
	
	protected String[] getPage() {
		String[] infos = new String[2];
		do {
			System.out.println("Please enter page number :");
			infos[0] = sc.nextLine();
		} while (infos[0].length() == 0);

		do {
			System.out.println("Please enter the number of objects per page :");
			infos[1] = sc.nextLine();
		} while (infos[1].length() == 0);

		return infos;
	}

	public void setScanner(Scanner sc) {
		this.sc = sc;
	}

	/**
	 * Checks whether a date is in a valid format or not
	 * 
	 * @param date a String representing the date
	 * @return the date or null if the date's wrong
	 */
	protected static LocalDate mapDate(String date) {
		LocalDate ld = null;

		if ((date != null) && (date.length() == 10)) {
			String[] s = date.split("-");
			if (s.length != 3) {
				System.out.println("Wrong date format!\nThe date will be set to null.");
			} else if (!date.equals(RestCLI.TIMESTAMP_ZERO)) {
				int year = Integer.valueOf(s[0]);
				int month = Integer.valueOf(s[1]);
				int day = Integer.valueOf(s[2]);
				ld = LocalDate.of(year, month, day);
			}
		}

		return ld;
	}

	protected static String validateDate(String date) {
		LocalDate ld = null;

		if ((date != null) && (date.length() == 10)) {
			String[] s = date.split("-");
			if (s.length != 3) {
				System.out.println("Wrong date format!\nThe date will be set to null.");
				date = "";
			} else if (!date.equals(RestCLI.TIMESTAMP_ZERO)) {
				int year = Integer.valueOf(s[0]);
				int month = Integer.valueOf(s[1]);
				int day = Integer.valueOf(s[2]);
				ld = LocalDate.of(year, month, day);
				date = ld.toString();
			}
		}

		return date;
	}
	
	private String createUrl(final String base, final String... params) {
		StringBuilder sb = new StringBuilder(base);
		
		for (String s : params) {
			sb.append("/");
			sb.append(s);
		}
		
		return sb.toString(); 
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri(BASE_URL).build();
	}
	
	private <T> T getObject(String url, Class<T> type) {
		this.services = client.target(BASE_URL + url);
 
		Builder request = this.services.request(MediaType.APPLICATION_JSON);
		Response response = request.get();
		
		if (response.getStatus() == 200) {
			return response.readEntity(type);
		}
		
		return null;
	}

	
	private <T> List<T> getObjectList(String url, Class<T> type) {
		this.services = client.target(BASE_URL + url);
 
		Builder request = this.services.request(MediaType.APPLICATION_JSON);
		Response response = request.get();
		
		if (response.getStatus() == 200) {
			return response.readEntity(new GenericList<T>() {});
		}
		
		return null;
	}
	
	/**
	 * Class to represent a Generic List of T.
	 *  
	 * @author Aurélien LEJEAU
	 *
	 * @param <T> the List type 
	 */
	private class GenericList<T> extends GenericType<List<T>> {
		public GenericList(){
			super();
		}
	}
	
	private boolean addComputer(String url, ComputerDto cdto) {
		this.services = client.target(BASE_URL + url);
 
		Builder request = this.services.request();
		Entity<ComputerDto> e = Entity.entity(cdto, MediaType.APPLICATION_JSON);
		Response response = request.post(e, Response.class);
		
		if (response.getStatus() == 200) {
			return true;
		}
		
		return false;
	}
	
	private boolean updateComputer(String url, ComputerDto cdto) {
		this.services = client.target(BASE_URL + url);
 
		Builder request = this.services.request();
		Entity<ComputerDto> e = Entity.entity(cdto, MediaType.APPLICATION_JSON);
		Response response = request.put(e, Response.class);
		
		if (response.getStatus() == 200) {
			return true;
		}
		
		return false;
	}
	
	private void delObject(String url) {
		this.services = client.target(BASE_URL + url);
		this.services.request().delete();
	}
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("file:../computer-database-persistence/src/main/resources/persistence-application-context.xml");

		Scanner sc = new Scanner(System.in);
		RestCLI cli = context.getBean(RestCLI.class);
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
