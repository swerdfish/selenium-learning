package selenium.test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.apache.commons.io.FileUtils;

class Demo012_TakeScreenshot {
	
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
	void test() {
		driver.findElement(By.cssSelector("a[href='/signup']")).click();
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		try {
			FileUtils.copyFile(srcFile, new File(System.getProperty("user.home")+"\\Image.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@AfterEach
	public void tearDown() {
		driver.quit();
	}

}
