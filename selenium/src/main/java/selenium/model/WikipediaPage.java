package selenium.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.Getter;

@Getter
public class WikipediaPage {
	
	@FindBy(css = "div#mw-content-text div a[href^='/wiki']")
	private List<WebElement> links;
	
	@FindBy(css = "div#mw-content-text div p")
	private List<WebElement> paragraphs;
	
	@FindBy(id = "bodyContent")
	private WebElement bodyContent;
	
	public WikipediaPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
//		WebElement contentParent = driver.findElement(By.cssSelector("div#mw-content-text div"));
//		List<WebElement> content = contentParent.findElements(By.cssSelector("*"));
//		links = new ArrayList<>();
//		for (WebElement we : content) {
//			if (!we.getTagName().startsWith("h") && !we.getTagName().equals("div")) {
//				List<WebElement> candidates = we.findElements(By.cssSelector("*"));
//				
//			}
//		}
	}
	
	public Map<WebElement, String> webElementText(List<WebElement> elements) {
		Map<WebElement, String> assoc = new LinkedHashMap<>();
		for (WebElement we : elements) {
			assoc.put(we, we.getText());
		}
		return assoc;
	}

}
