package selenium.applications;

import org.openqa.selenium.WebDriver;
import selenium.DriverUtils;
import selenium.model.WikipediaHomePage;
import selenium.model.WikipediaPage;

public class WikipediaGame {
	
	public static void main(String[] args) {
		WebDriver driver = DriverUtils.getChromeDriver();
		WikipediaHomePage wikiHome = new WikipediaHomePage(driver);
		driver.get(wikiHome.url);
		wikiHome.search("Yo");
		WikipediaPage wikiPage_yo = new WikipediaPage(driver);
		driver.quit();
	}

}
