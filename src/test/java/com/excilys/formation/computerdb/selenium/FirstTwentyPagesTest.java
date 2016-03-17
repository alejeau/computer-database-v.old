package com.excilys.formation.computerdb.selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirstTwentyPagesTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testFirstTwentyPages() throws Exception {
		driver.get(baseUrl + "/computer-database/access");
		driver.findElement(By.linkText("2")).click();
		driver.findElement(By.linkText("3")).click();
		driver.findElement(By.linkText("4")).click();
		driver.findElement(By.linkText("5")).click();
		driver.findElement(By.linkText("6")).click();
		driver.findElement(By.linkText("7")).click();
		driver.findElement(By.linkText("8")).click();
		driver.findElement(By.linkText("9")).click();
		driver.findElement(By.linkText("10")).click();
		driver.findElement(By.linkText("11")).click();
		driver.findElement(By.linkText("12")).click();
		driver.findElement(By.linkText("13")).click();
		driver.findElement(By.linkText("14")).click();
		driver.findElement(By.linkText("15")).click();
		driver.findElement(By.linkText("16")).click();
		driver.findElement(By.linkText("17")).click();
		driver.findElement(By.linkText("18")).click();
		driver.findElement(By.linkText("19")).click();
		driver.findElement(By.linkText("20")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
