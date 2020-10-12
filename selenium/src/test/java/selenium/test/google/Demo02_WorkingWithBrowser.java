package selenium.test.google;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

class Demo02_WorkingWithBrowser {
	
	WebDriver driver;
	String url = "https://google.com";
	String geckoDriverPath = "src/main/resources/drivers/geckodriver.exe";
	
	@BeforeEach
	public void setup() {
		System.setProperty("webdriver.gecko.driver", new File(geckoDriverPath).getAbsolutePath());
		driver = new FirefoxDriver();
		driver.get(url);
	}

	@Test
	void test() {
		// Fetch the page title
		String pageTitle = driver.getTitle();
		System.out.println("Page title: " + pageTitle);
	}
	
	@AfterEach
	public void tearDown() {
		driver.close();
	}

}
