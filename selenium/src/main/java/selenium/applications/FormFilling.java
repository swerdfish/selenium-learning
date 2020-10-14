package selenium.applications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import selenium.DriverUtils;

public class FormFilling {
	
	private static Map<String, String> form1;
	static {
		form1 = new HashMap<>();
		form1.put("title", "Mr.");
		form1.put("firstName", "Aaron");
		form1.put("lastName", "Bartlett");
		form1.put("gender", "M");
		form1.put("dob", "01/01/2000");
		form1.put("ssn", "555-443-2211");
		form1.put("emailAddress", "aaron.bartlett@gmail.com");
		form1.put("password", "AB12cd34");
	}
	private static Map<String, String> form2;
	static {
		form2 = new HashMap<>();
		form2.put("address", "123 Digital Lane");
		form2.put("locality", "Internet City");
		form2.put("region", "CA");
		form2.put("postalCode", "94302");
		form2.put("country", "US");
		form2.put("homePhone", "(547) 392-5436");
		form2.put("mobilePhone", "(547) 392-5436");
		form2.put("workPhone", "(547) 392-5436");
	}
	
	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = DriverUtils.getChromeDriver();
		driver.get("http://dbankdemo.com/signup");
		WebElement form = driver.findElement(By.cssSelector("form"));
		List<WebElement> selects = form.findElements(By.cssSelector("select"));
		for (WebElement select : selects) {
			String entry = form1.get(select.getAttribute("name"));
			if (entry != null) {
				Select dropdown = new Select(select);
				dropdown.selectByVisibleText(entry);
			}
		}
//		selects.forEach(s -> System.out.println(s.getText()));
		List<WebElement> inputs = form.findElements(By.cssSelector("input:not([type='hidden'])"));
		inputs.forEach(i -> {
//			System.out.println(i.getAttribute("type"));
//			System.out.println(i.getAttribute("id"));
			String id = i.getAttribute("id");
			if (id.equals("confirmPassword")) id = "password";
			String entry = form1.get(id);
			if (entry != null) i.sendKeys(entry);
		});
//		List<WebElement> formGroups = form.findElements(By.cssSelector("div[class^='form-']"));
//		formGroups.forEach(fg -> System.out.println(fg.getText()));
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
		form.submit();
		form = driver.findElement(By.cssSelector("form"));
		inputs = form.findElements(By.cssSelector("input:not([type='hidden'])"));
		inputs.forEach(i -> {
			if (i.getAttribute("type").equals("checkbox"))
				i.click();
			else {
				String entry = form2.get(i.getAttribute("id"));
				if (entry != null) i.sendKeys(entry);
			}
		});
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
		driver.quit();
	}

}
