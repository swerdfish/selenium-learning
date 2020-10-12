package selenium;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverUtils {
	
	public static final String driverDirectory = "src/main/resources/drivers/";
	
	private static WebDriver chromeDriver;
	private static WebDriver firefoxDriver;
	
	public static WebDriver getChromeDriver() {
		if (System.getProperty("webdriver.chrome.driver")==null) {
			System.setProperty(
					"webdriver.chrome.driver",
					new File(driverDirectory+"chromedriver.exe").getAbsolutePath()
			);
		}
		if (chromeDriver == null) {
			chromeDriver = new ChromeDriver();
		}
		return chromeDriver;
	}
	
	public static WebDriver getFirefoxDriver() {
		if (System.getProperty("webdriver.gecko.driver")==null) {
			System.setProperty(
					"webdriver.gecko.driver",
					new File(driverDirectory+"geckodriver.exe").getAbsolutePath()
			);
		}
		if (firefoxDriver == null) {
			firefoxDriver = new FirefoxDriver();
		}
		return firefoxDriver;
	}

}
