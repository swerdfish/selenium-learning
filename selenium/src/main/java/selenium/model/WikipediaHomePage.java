package selenium.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WikipediaHomePage {
	
	public final String url = "https://wikipedia.org";
	
	@FindBy(id = "searchInput")
	private WebElement searchBar;
	
	public WikipediaHomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void relocate(WebDriver driver) {
		driver.get(url);
	}
	
	public void search(String input) {
		searchBar.sendKeys(input);
		searchBar.submit();
	}

}
