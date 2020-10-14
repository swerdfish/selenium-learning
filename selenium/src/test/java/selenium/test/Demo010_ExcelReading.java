package selenium.test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

class Demo010_ExcelReading {
	
	private WebDriver driver;
	private final String url = "http://dbankdemo.com/login";
	private final String geckoDriverPath = "src/main/resources/drivers/geckodriver.exe";
	
	@BeforeEach
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", new File(geckoDriverPath).getAbsolutePath());
		driver = new FirefoxDriver();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
	}

	@Test
	void test() throws EncryptedDocumentException, IOException {
		String excelFilePath = System.getProperty("user.home")+"\\Downloads\\IVSIS_Cred_26May17_1802.xlsx";
//		FileInputStream excelFileStream = new FileInputStream(excelFilePath);
		Workbook wkbk = WorkbookFactory.create(new File(excelFilePath));
		Sheet sheet = wkbk.getSheetAt(0);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
		System.out.println(rowCount);
		Iterator<Row> sheetIt = sheet.iterator();
		sheetIt.next();
		WebElement usernameField = driver.findElement(By.id("username"));
		WebElement passwordField = driver.findElement(By.id("password"));
		while (sheetIt.hasNext()) {
			Row row = sheetIt.next();
			usernameField.sendKeys(row.getCell(0).getStringCellValue());
			passwordField.sendKeys(row.getCell(0).getStringCellValue());
			driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
			usernameField.clear();
			passwordField.clear();
			Cell cell = row.getCell(2);
			cell.setCellValue("Tested");
		}
		OutputStream fileOutputStream = new FileOutputStream(
				String.join("", Arrays.copyOfRange(
						excelFilePath.split("\\."), 0, excelFilePath.split("\\.").length - 1
				)
			) + "_Test.xlsx"
		);
		wkbk.write(fileOutputStream);
		wkbk.close();
	}
	
	@AfterEach
	public void tearDown() {
		driver.quit();
	}

}
