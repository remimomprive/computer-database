package fr.excilys.rmomprive.test.integration;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import fr.excilys.rmomprive.persistence.Database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;

public class AddComputerNoJsIntegrationTest {
  @Test
  public void testAppStarted() {
      WebDriver driver = new HtmlUnitDriver();
      driver.get("http://localhost:9090/computer-database/dashboard");
      assertEquals("Computer Database", driver.getTitle());
  }
  
  @Test
  public void validCreation() {
    // Get the page
    WebDriver driver = new HtmlUnitDriver();
    driver.get("http://localhost:9090/computer-database/addComputer");

    WebElement computerName = driver.findElement(By.id("computerName"));
    WebElement introduced = driver.findElement(By.id("introduced"));
    WebElement discontinued = driver.findElement(By.id("discontinued"));
    Select company = new Select(driver.findElement(By.id("companyId")));
    
    computerName.sendKeys("computer namme");
    introduced.sendKeys("2018-01-10");
    discontinued.sendKeys("2019-01-10");
    company.selectByIndex(1);
    
    WebElement button = driver.findElement(By.cssSelector("input[type*=\"submit\"]"));
    button.submit();
    
    assertTrue(driver.getPageSource().contains("ComputerDto"));
  }
  
  @Test
  public void invalidComputerName() {
    // Get the page
    WebDriver driver = new HtmlUnitDriver();
    driver.get("http://localhost:9090/computer-database/addComputer");

    WebElement computerName = driver.findElement(By.id("computerName"));
    WebElement introduced = driver.findElement(By.id("introduced"));
    WebElement discontinued = driver.findElement(By.id("discontinued"));
    Select company = new Select(driver.findElement(By.id("companyId")));
    
    computerName.sendKeys("");
    introduced.sendKeys("2018-01-10");
    discontinued.sendKeys("2019-01-10");
    company.selectByIndex(1);
    
    WebElement button = driver.findElement(By.cssSelector("input[type*=\"submit\"]"));
    button.submit();
    
    assertTrue(driver.getPageSource().contains("Error 500"));
  }
  
  @Test
  public void invalidDates() {
    // Get the page
    WebDriver driver = new HtmlUnitDriver();
    driver.get("http://localhost:9090/computer-database/addComputer");

    WebElement computerName = driver.findElement(By.id("computerName"));
    WebElement introduced = driver.findElement(By.id("introduced"));
    WebElement discontinued = driver.findElement(By.id("discontinued"));
    Select company = new Select(driver.findElement(By.id("companyId")));
    
    computerName.sendKeys("computer name");
    introduced.sendKeys("2019-01-10");
    discontinued.sendKeys("2018-01-10");
    company.selectByIndex(1);
    
    WebElement button = driver.findElement(By.cssSelector("input[type*=\"submit\"]"));
    button.submit();
    
    assertTrue(driver.getPageSource().contains("Error 500"));
  }
}
