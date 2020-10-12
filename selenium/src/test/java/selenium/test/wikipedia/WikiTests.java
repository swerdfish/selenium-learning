package selenium.test.wikipedia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import selenium.DriverUtils;
import selenium.model.WikipediaHomePage;

public class WikiTests {
	
	public static WebDriver driver;
	public static WikipediaHomePage wikiHome;
	
	@BeforeAll
	public static void init() {
		driver = DriverUtils.getChromeDriver();
		wikiHome = new WikipediaHomePage(driver);
		driver.get(wikiHome.url);
	}
	
	@BeforeEach
	public void refresh() {
		driver.navigate().refresh();
	}

	@Test
	void doAnything() {
		wikiHome.search("Hello World");
		assertEquals("\"Hello, World!\" program - Wikipedia", driver.getTitle());
	}
	
	@AfterAll
	public static void shutdown() {
		driver.quit();
	}

}
