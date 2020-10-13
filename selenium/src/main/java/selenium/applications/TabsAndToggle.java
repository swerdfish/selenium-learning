package selenium.applications;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import selenium.DriverUtils;
import selenium.model.WikipediaHomePage;
import selenium.model.WikipediaPage;

public class TabsAndToggle {
	
	private static WebDriver driver;
	
	public static void main(String[] args) {
		driver = DriverUtils.getChromeDriver();
		WikipediaHomePage wikiHome = new WikipediaHomePage(driver);
		driver.get(wikiHome.url);
		wikiHome.search("Yo");
		WikipediaPage wikiPage_yo = new WikipediaPage(driver);
//		wikiPage_yo.getLinks().get(1).sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
//		List<String> openTabs = new ArrayList<>(driver.getWindowHandles());
//		driver.switchTo().window(openTabs.get(1));
//		WikipediaPage wikiPage_slang = new WikipediaPage(driver);
//		wikiPage_slang.getLinks().forEach(l -> System.out.println(l.getText()));
		List<String> deg6Pages = degreesOfFirstSeparation(6, wikiPage_yo);
		System.out.println(deg6Pages);
		driver.quit();
	}
	
	public static List<String> degreesOfFirstSeparation(int n, WikipediaPage wp) {
		List<String> pageTitles = new ArrayList<>();
		for (int i=0; i<n; i++) {
			wp.getLinks().get(1).sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
			List<String> openTabs = new ArrayList<>(driver.getWindowHandles());
			driver.switchTo().window(openTabs.get(openTabs.size()-1));
			pageTitles.add(driver.getTitle());
			wp = new WikipediaPage(driver);
		}
		return pageTitles;
	}

}
