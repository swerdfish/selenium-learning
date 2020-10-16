package selenium.test.google;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
	
	@ParameterizedTest
	@CsvFileSource(resources = "/gmailTestCases.csv", numLinesToSkip = 1)
	void csvTest(int testNum, String firstName, String lastName, String username, String password, String confirmPassword, String phoneNumberId) {
		System.out.println(testNum+" "+firstName+" "+lastName+" "+username+" "+password+" "+confirmPassword+" "+phoneNumberId);
		driver.findElement(By.id("firstName")).sendKeys(firstName);
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.name("Passwd")).sendKeys(password);
		driver.findElement(By.name("ConfirmPasswd")).sendKeys(confirmPassword);
		driver.findElement(By.id("accountDetailsNext")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 3);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("phoneNumberId")));
		} catch (TimeoutException e) {
			List<WebElement> errorWebElements = driver.findElements(By.cssSelector("div[id$='Error']"));
			System.out.println("Test "+testNum+" failed");
			System.out.println("Error Message:");
			StringBuilder sb = new StringBuilder();
			errorWebElements.forEach(we -> 
//				System.out.println(we.getTagName()+", "+we.getAttribute("id")+", "+we.getText()+", "+we.getAttribute("value")+", "+we.getAttribute("innerText")+", "+we.getAttribute("innerHTML"))
				{System.out.println(we.getText()); sb.append(we.getText());}
			);
			fail(sb.toString());
		}
		driver.findElement(By.id("phoneNumberId")).sendKeys(phoneNumberId);
		try {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div[id$='Error']")));
		} catch (TimeoutException e) {
			driver.findElements(By.tagName("button")).get(1).click();
			System.out.println("Test "+testNum+" passed");
			return;
		}
		List<WebElement> errorWebElements = driver.findElements(By.cssSelector("div[id$='Error']"));
		System.out.println("Test "+testNum+" failed");
		System.out.println("Error Message:");
		errorWebElements.forEach(we -> System.out.println(we.getText()));
		fail();
	}

//	@Test
//	void test() {
//		for (CSVRecord rec : csvData) {
//			boolean progressed = false;
//			for (int i=1; i<rec.size()-2; i++) {
//				String[] header = headers.get(i).split(" ");
//				System.out.println(Arrays.toString(header));
//				WebElement we = findElementBySplitHeader(header);
//				if (we == null) {
//					if (!progressed) {
//						driver.findElement(By.id("accountDetailsNext")).click();
//						progressed = true;
//						WebDriverWait wait = new WebDriverWait(driver, 3);
//						wait.until(
//								ExpectedConditions.or(
//										ExpectedConditions.presenceOfElementLocated(By.id("phoneNumberId")),
//										ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id$='Error']"))
//								)
//						);
//					}
//					we = findElementBySplitHeader(header);
//					if (we == null) {
//						System.out.println("Test failed");
//						if (driver.findElements(By.cssSelector("div[id$='Error']")).size() > 0) {
//							WebElement error = driver.findElement(By.cssSelector("div[id$='Error']"));
//							System.out.println("Error Message: "+error.getTagName()+" - "+error.getAttribute("innerHTML"));
//						} else {
//							System.out.println("Could not find field associated with "+headers.get(i));
//						}
//					} else {
//						we.clear();
//						we.sendKeys(rec.get(i));
//						driver.findElements(By.tagName("button")).get(1).click();
//						WebDriverWait wait = new WebDriverWait(driver, 5);
//						wait.until(ExpectedConditions.presenceOfElementLocated(By.id("accountDetailsNext")));
//						System.out.println("Test passed");
//						progressed = false;
//					}
//				} else {
//					System.out.println(we.getTagName()+" "+we.getAttribute("id"));
//					System.out.println(we.getAttribute("value"));
//					Keys[] backSpaces = new Keys[we.getAttribute("value").length()];
//					Arrays.fill(backSpaces, Keys.BACK_SPACE);
//					we.sendKeys(Keys.chord(backSpaces));
//					System.out.println(we.getAttribute("value"));
//					we.sendKeys(rec.get(i));
//				}
//			}
////			WebElement firstName = driver.findElement(By.id("firstName"));
////			firstName.sendKeys(rec.get("id firstName"));
////			WebElement lastName = driver.findElement(By.id("lastName"));
////			lastName.sendKeys(rec.get("id lastName"));
////			WebElement username = driver.findElement(By.id("username"));
////			username.sendKeys(rec.get("id username"));
////			WebElement password = driver.findElement(By.name("Passwd"));
////			password.sendKeys(rec.get("name Passwd"));
////			WebElement confPassword = driver.findElement(By.name("ConfirmPasswd"));
////			confPassword.sendKeys(rec.get("name ConfirmPasswd"));
//		}
//	}
	
	@AfterAll
	public static void shutdown() {
		driver.quit();
	}

}
