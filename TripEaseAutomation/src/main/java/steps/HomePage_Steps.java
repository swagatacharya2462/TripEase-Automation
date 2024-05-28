package steps;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HomePage_Steps {

	By oneWay = By.xpath("//li[@data-cy='oneWayTrip']");

	By fromCity = By.xpath("//label[@for='fromCity']");
	By fromCityName = By.xpath("//input[@placeholder='From']");
	By kolkata = By.xpath("//p[normalize-space()='Kolkata, India']");

	By toCity = By.xpath("//label[@for='toCity']");
	By toCityName = By.xpath("//input[@placeholder='To']");
	By bhubaneswar = By.xpath("//p[normalize-space()='Bhubaneswar, India']");

	By fromDate = By.xpath("//div[@aria-label='Sun Apr 14 2024']");

	By travellers = By.xpath("//label[@for='travellers']");

	By adult = By.xpath("//ul[@class='guestCounter font12 darkText gbCounter']//li[@data-cy='adults-3']");
	By apply = By.xpath("//button[normalize-space()='APPLY']");

	By search = By.xpath("//a[normalize-space()='Search']");

	By okayGotIt = By.xpath("//button[normalize-space()='OKAY, GOT IT!']");

	By flightsName = By.xpath("//p[@class='boldFont blackText airlineName']");
	By flightsPrice = By.xpath("//div/div[@class='textRight flexOne']");

	WebDriver driver;

	public void Screenshoot(String fileName) throws IOException {
		TakesScreenshot capture = (TakesScreenshot) driver;
		File srcFile = capture.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir") + "/Screenshots/" + fileName + ".png");
		Files.copy(srcFile, destFile);
	}

	public HomePage_Steps(Base base) {
		this.driver = base.getDriver();
	}

	@Given("I am on MakeMyTrip Home Page.")
	public void i_am_on_make_my_trip_home_page() throws Exception {
		driver.get("https://www.makemytrip.com/");
		Thread.sleep(4000);
		Actions actions = new Actions(driver);
		Robot robot = new Robot();
		robot.mouseMove(50, 50);
		actions.click().build().perform();
		robot.mouseMove(200, 70);
		actions.click().build().perform();
	}

	@When("Fill all the user requirement and click on search")
	public void fill_all_the_user_requirement_and_click_on_search() throws Exception {
		driver.findElement(oneWay).click();

		driver.findElement(fromCity).click();
		driver.findElement(fromCityName).sendKeys("Bhubaneswar");
		driver.findElement(bhubaneswar).click();

		driver.findElement(toCity).click();
		driver.findElement(toCityName).sendKeys("Kolkata");
		driver.findElement(kolkata).click();

		driver.findElement(fromDate).click();

		driver.findElement(travellers).click();
		driver.findElement(adult).click();
		driver.findElement(apply).click();

		driver.findElement(search).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(okayGotIt));

		driver.findElement(okayGotIt).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)", "");

		Screenshoot("FlightsResult");

	}

	@Then("Print the minimum fare after discount.")
	public void print_the_minimum_fare_after_discount() {
		List<WebElement> FlightName = driver.findElements(flightsName);
		List<WebElement> FlightPrice = driver.findElements(flightsPrice);

		int count = FlightName.size();

		for (int i = 0; i < count; i++) {
			System.out.println(
					FlightName.get(i).getText() + "\t" + FlightPrice.get(i).getText().replaceAll("per adult", ""));
		}
	}

}
