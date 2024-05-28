package steps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.google.common.io.Files;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HolidayPackages_Steps {

	By fromCity = By.xpath("//label[@for='fromCity']");
	By EnterCity = By.xpath("//input[@placeholder='Enter City']");
	By kolkata = By.xpath("//li[@class='makeFlex column']");

	By toCity = By.xpath("//label[@for='toCity']");
	By toPlace = By.xpath("//input[@placeholder='To']");
	By bhubaneswar = By.xpath("//div[normalize-space()='Bhubaneshwar']");

	By date = By.xpath("//div[@aria-label='Mon Apr 15 2024']");

	By adultPlus = By.xpath("//div[@data-testid='adult-increment-counter']");
	By childrenPlus = By.xpath("//div[@data-testid='child-increment-counter']");
	By age = By.xpath("//li[contains(text(),'3')]");
	By apply = By.xpath("//button[normalize-space()='APPLY']");

	By sliderx = By.xpath("(//div[@class='rc-slider-handle rc-slider-handle-1'])[1]");
	By star = By.xpath("//ul/li[contains(.,'4 ★')]");
	By withFlight = By.xpath("//div/div/ul/li[contains(.,'With Flights')]");

	By search = By.xpath("//button[@id='search_button']");

	By closeButton = By.xpath("//span[@class='close closeIcon']");
	By dismiss = By.xpath("//button[normalize-space()='DISMISS']");

	By restaurant = By.xpath("//div/div/p[@class='packageHead']");

	WebDriver driver;

	public void Screenshoot(String fileName) throws IOException {
		TakesScreenshot capture = (TakesScreenshot) driver;
		File srcFile = capture.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir") + "/Screenshots/" + fileName + ".png");
		Files.copy(srcFile, destFile);
	}

	public HolidayPackages_Steps(Base base) {
		this.driver = base.getDriver();
	}

	@Given("I am on MakeMyTrip Holiday Packages page")
	public void i_am_on_make_my_trip_holiday_packages_page() {
		driver.get("https://www.makemytrip.com/holidays-india/");
	}

	@When("Fill all the Requirements and click on search")
	public void fill_all_the_requirements_and_click_on_search() throws Exception {
		driver.findElement(fromCity).click();
		Thread.sleep(2000);
		driver.findElement(EnterCity).clear();
		Thread.sleep(1000);
		driver.findElement(EnterCity).sendKeys("Kolkata");
		driver.findElement(kolkata).click();

		driver.findElement(toCity).click();
		Thread.sleep(2000);
		driver.findElement(toPlace).clear();
		Thread.sleep(1000);
		driver.findElement(toPlace).sendKeys("Bhubaneshwar");
		Thread.sleep(1000);
		driver.findElement(bhubaneswar).click();

		driver.findElement(date).click();

		for (int i = 2; i <= 3; i++) {
			driver.findElement(adultPlus).click();
		}
		driver.findElement(childrenPlus).click();
		driver.findElement(age).click();
		Thread.sleep(1000);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("window.scrollBy(0,200)", "");
		Thread.sleep(1000);
		driver.findElement(apply).click();

		WebElement slider = driver.findElement(sliderx);
		Actions move = new Actions(driver);
		Action action = (Action) move.dragAndDropBy(slider, 100, 0).build();
		action.perform();
		Thread.sleep(2000);
		driver.findElement(star).click();
		driver.findElement(withFlight).click();
		driver.findElement(apply).click();

		driver.findElement(search).click();
		Thread.sleep(2000);

	}

	@Then("Print the results in excel sheet")
	public void print_the_results_in_excel_sheet() throws Exception {

		driver.findElement(closeButton).click();
		List<WebElement> data = driver.findElements(restaurant);

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Data");
		try {
			for (int i = 0; i < data.size(); i++) {
				String extractData = data.get(i).getText();
				Row row = sheet.createRow(i);
				Cell cell = row.createCell(0);
				cell.setCellValue(extractData);
			}
			FileOutputStream fileOut = new FileOutputStream("data.xlsx");
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			System.out.println("Data has been written to data.xlsx successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
