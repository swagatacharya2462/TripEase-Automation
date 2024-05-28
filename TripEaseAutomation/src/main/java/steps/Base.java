package steps;

import java.time.Duration;
import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.Before;

public class Base {

	public WebDriver driver;

	@SuppressWarnings("resource")
	@Before
	public void browserSetUp() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your preference(Chrome/Edge/Firefox):");
		String string = scanner.nextLine();
		if (string.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (string.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else if (string.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else {
			System.out.println("Write browser name correctly!");
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}

	//@After
	public void browserClose() {
		driver.quit();
	}

	public WebDriver getDriver() {
		return driver;
	}

}
