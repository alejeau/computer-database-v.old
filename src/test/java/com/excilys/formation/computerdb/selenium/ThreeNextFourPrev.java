package com.excilys.formation.computerdb.selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ThreeNextFourPrev {
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
	public void testThreeNextFourPrev() throws Exception {
		driver.get(baseUrl + "/computer-database/access?reset=true");
		driver.findElement(By.xpath("//li[10]/a/span")).click();
		driver.findElement(By.xpath("//li[10]/a/span")).click();
		driver.findElement(By.xpath("//li[10]/a/span")).click();
		driver.findElement(By.linkText("«")).click();
		driver.findElement(By.linkText("«")).click();
		driver.findElement(By.linkText("«")).click();
		driver.findElement(By.linkText("«")).click();
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
