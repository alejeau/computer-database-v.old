package com.excilys.formation.computerdb.service.impl;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.formation.computerdb.model.Company;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.Page;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/application-context.xml" })
public class ComputerDatabaseServiceImplTest {

	@Autowired
	ComputerDatabaseServiceImpl services;
	
	@Test
	public void testGetNbCompanies() {
		int nbComp = this.services.getNbCompanies();
		assertEquals(42, nbComp);
	}

	@Test
	public void testGetNbComputers() {
		int nbComp = this.services.getNbComputers();
		assertEquals(574, nbComp);
	}
	
	@Test
	public void testGetNbComputersNamed() {
		int nbComp = this.services.getNbComputersNamed("test");
		assertEquals(3, nbComp);
	}

	@Test
	public void testGetCompanyById() {
		Company c = services.getCompanyById(1L);
		assertEquals("Apple Inc.", c.getName());
	}

	@Test
	public void testGetCompanyByName() {
		Company c = services.getCompanyByName("Apple Inc.");
		assertEquals(1L, c.getId());
	}

	@Test
	public void testGetComputerById() {
		Computer c = services.getComputerById(16L);
		assertEquals("Apple II", c.getName());
	}

	@Test
	public void testGetComputerByName() {
		Computer c = services.getComputerByName("Apple II");
		assertEquals(16L, c.getId());
	}

	@Test
	public void testGetCompanyPage() {
		Page<Company> p = services.getAllCompanies();
		assertEquals(42, p.getPage().size());
	}

	@Test
	@Ignore
	public void testGetComputerSortedPage() {
	}

	@Test
	@Ignore
	public void testGetComputerSearchPage() {
	}

	@Test
	public void testCreateComputer() {
		int oldNb = services.getNbComputers();
		Computer c = new Computer.Builder().name("ComputerXXX").build();
		services.createComputer(c);
		int newNb = services.getNbComputers();
		assertEquals(oldNb+1, newNb);
	}

	@Test
	@Ignore
	public void testCreateComputerWithoutId() {
	}

	@Test
	@Ignore
	public void testCreateComputerWithId() {
	}

	@Test
	@Ignore
	public void testUpdateComputer() {
		final String name = "ComputerXXX";
		Computer c = services.getComputerByName(name);
		final LocalDate intro = LocalDate.parse("2016-17-03");
		c.setIntro(intro);
		services.updateComputer(c, name);
		Computer c2 = services.getComputerByName(name);
		assertEquals(c, c2);
	}

	@Test
	@Ignore
	public void testDeleteComputer() {
		int oldNb = services.getNbComputers();
		final String name = "ComputerXXX";
		final Computer c = services.getComputerByName(name);
		services.deleteComputer(c);
		int newNb = services.getNbComputers();
		assertEquals(oldNb-1, newNb);
	}

	@Test
	@Ignore
	public void testDeleteComputerFromId() {
	}

	@Test
	@Ignore
	public void testDeleteComputerFromName() {
	}

	@Test
	@Ignore
	public void testDeleteComputers() {
	}

	@Test
	@Ignore
	public void testDeleteCompany() {
	}
	
}
