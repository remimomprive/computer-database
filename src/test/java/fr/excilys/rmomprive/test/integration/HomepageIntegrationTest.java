package fr.excilys.rmomprive.test.integration;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.assertEquals;

public class HomepageIntegrationTest {
  @Test
  public void testAppStarted() {
      WebDriver driver = new HtmlUnitDriver();
      driver.get("http://localhost:9090/computer-database/dashboard");
      assertEquals("Computer Database", driver.getTitle());
  }
}
