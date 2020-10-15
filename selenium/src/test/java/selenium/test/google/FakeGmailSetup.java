package selenium.test.google;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.DriverUtils;

class FakeGmailSetup {
	
	public static final String URL = "https://accounts.google.com/signup/v2/webcreateaccount?flowName=GlifWebSignIn&flowEntry=SignUp";
	public static final String TEST_CSV_PATH = System.getProperty("user.home") + "\\OneDrive\\Documents\\Infosys\\gmailTestCases.csv";
	public static final String SAVE_CSV_PATH = System.getProperty("user.home") + "\\OneDrive\\Documents\\Infosys\\gmailTestCases-results.csv";
	
	private static WebDriver driver;
	
	private static List<CSVRecord> csvData;
	private static List<String> headers;
	
	@BeforeAll
	public static void init() throws IOException {
		driver = DriverUtils.getChromeDriver();
		driver.get(URL);
		try (
				Reader reader = Files.newBufferedReader(Paths.get(TEST_CSV_PATH));
				CSVParser csvParser = CSVParser.parse(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
		) {
			csvData = csvParser.getRecords();
			headers = csvParser.getHeaderNames();
		}
	}
	
	@BeforeEach
	public void refresh() {
		driver.navigate().refresh();
	}
	
	public WebElement findElementBySplitHeader(String[] header) {
		WebElement we = null;
		if (header[0].equals("id")) {
			try {
				we = driver.findElement(By.id(header[1]));
			} catch (NoSuchElementException e) {
				we = null;
			}
		} else if (header[0].equals("name")) {
			try {
				we = driver.findElement(By.name(header[1]));
			} catch (NoSuchElementException e) {
				we = null;
			}
		}
		return we;
	}

	@Test
	void test() {
		for (CSVRecord rec : csvData) {
			boolean progressed = false;
			for (int i=1; i<rec.size()-2; i++) {
				String[] header = headers.get(i).split(" ");
				WebElement we = findElementBySplitHeader(header);
				if (we == null) {
					if (!progressed) {
						driver.findElement(By.tagName("form")).submit();
						progressed = true;
					}
					WebDriverWait wait = new WebDriverWait(driver, 5);
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id("phoneNumberId")));
					we = findElementBySplitHeader(header);
					if (we == null) {
						System.out.println("Test failed");
						System.out.println("Could not find field associated with "+headers.get(i));
					} else {
						we.sendKeys(rec.get(i));
						driver.findElements(By.tagName("button")).get(1).click();
						System.out.println("Test passed");
					}
				} else {
					we.sendKeys(rec.get(i));
				}
			}
//			WebElement firstName = driver.findElement(By.id("firstName"));
//			firstName.sendKeys(rec.get("id firstName"));
//			WebElement lastName = driver.findElement(By.id("lastName"));
//			lastName.sendKeys(rec.get("id lastName"));
//			WebElement username = driver.findElement(By.id("username"));
//			username.sendKeys(rec.get("id username"));
//			WebElement password = driver.findElement(By.name("Passwd"));
//			password.sendKeys(rec.get("name Passwd"));
//			WebElement confPassword = driver.findElement(By.name("ConfirmPasswd"));
//			confPassword.sendKeys(rec.get("name ConfirmPasswd"));
		}
	}
	
	@AfterAll
	public static void shutdown() {
		driver.quit();
	}

}
