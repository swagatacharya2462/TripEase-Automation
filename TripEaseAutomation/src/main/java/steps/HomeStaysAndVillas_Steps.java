package steps;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.io.Files;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HomeStaysAndVillas_Steps {

	By city = By.xpath("//label[@for='city']");
	By cityName = By.xpath("//input[@placeholder='Where do you want to stay?']");
	By gokarna = By.xpath("//p[.='Gokarna, City in Karnataka']");

	By checkInDate = By.xpath("//div[@aria-label='Mon Apr 15 2024']");
	By checkOutDate = By.xpath("//div[@aria-label='Fri Apr 19 2024']");

	By adultClick = By
			.xpath("//div[contains(@class,'rmsGst__row')][contains(.,'Adults')]//div[@data-cy='GuestSelect$$_324']");
	By click3 = By.xpath("//li[normalize-space()='03']");
	By apply = By.xpath("//button[normalize-space()='Apply']");

	By travelPrice = By.xpath("//label[@for='travelFor']");
	By travelPriceTag = By.xpath("//li[contains(text(),'₹2500-₹5000')]");

	By searchBtn = By.xpath("//button[@id='hsw_search_button']");

	By freeCancellation = By.xpath("//div[@class='makeFlex column']//span[contains(.,'Free Cancellation')]");
	By rating = By.xpath("//div[@class='makeFlex column']//span[contains(text(),'Very Good: 3.5+')]");
	By bedRoom = By.xpath("//span[contains(text(),'1 Bedroom')]");

	By homeStaysName = By.xpath("//span[@class='wordBreak appendRight10']");
	By homeStaysPrice = By.xpath("//p[@class='priceText latoBlack font22 blackText appendBottom5']");
	By homeStaysRating = By.xpath("//span[contains(@id,'hlistpg_hotel_user_rating')]");

	WebDriver driver;

	public void Screenshoot(String fileName) throws IOException {
		TakesScreenshot capture = (TakesScreenshot) driver;
		File srcFile = capture.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir") + "/Screenshots/" + fileName + ".png");
		Files.copy(srcFile, destFile);
	}

	public HomeStaysAndVillas_Steps(Base base) {
		this.driver = base.getDriver();
	}

	@Given("I am on MakeMyTrip HomeStays&Villas Page")
	public void i_am_on_make_my_trip_home_stays_villas_page() {
		driver.get("https://www.makemytrip.com/homestays/");
	}

	@When("Fill all the requirements and click on search")
	public void fill_all_the_requirements_and_click_on_search() {

		driver.findElement(city).click();
		driver.findElement(cityName).sendKeys("Gokarna");
		driver.findElement(gokarna).click();

		driver.findElement(checkInDate).click();
		driver.findElement(checkOutDate).click();

		driver.findElement(adultClick).click();
		driver.findElement(click3).click();
		driver.findElement(apply).click();

		driver.findElement(travelPrice).click();
		driver.findElement(travelPriceTag).click();

		driver.findElement(searchBtn).click();

	}

	@And("Fill the required filters")
	public void fill_the_required_filters() throws Exception {

		driver.findElement(freeCancellation).click();
		Screenshoot("VillaResult");
		Thread.sleep(2000);
		driver.findElement(rating).click();
		Thread.sleep(2000);
		driver.findElement(bedRoom).click();

	}

	@Then("Print the results in descending orders according to ratings")
	public void print_the_results_in_descending_orders_according_to_ratings() {

		List<WebElement> HomeStaysName = driver.findElements(homeStaysName);
		List<WebElement> HomeStaysPrice = driver.findElements(homeStaysPrice);
		List<WebElement> HomeStaysRatings = driver.findElements(homeStaysRating);

		// Extract ratings, names, and prices
		List<String> ratings = new ArrayList<>();
		List<String> names = new ArrayList<>();
		List<String> prices = new ArrayList<>();

		// Iterate through the home stays once to populate the lists appropriately
		for (int i = 0; i < HomeStaysRatings.size(); i++) {
			String rating = HomeStaysRatings.get(i).getText();
			String name = HomeStaysName.get(i).getText();
			String price = HomeStaysPrice.get(i).getText();

			ratings.add(rating);
			names.add(name);
			prices.add(price);
		}

		List<String> sortedRatings = new ArrayList<>(ratings);
		Collections.sort(sortedRatings, Collections.reverseOrder());

		Set<String> printedNames = new HashSet<>();
		int count = 0;

		for (String sortedRating : sortedRatings) {
			int index = ratings.indexOf(sortedRating);

			if (index != -1 && !printedNames.contains(names.get(index))) {
				printedNames.add(names.get(index));
				System.out.println(sortedRating + "\t" + names.get(index) + "\t" + prices.get(index));
				count++;

				if (count >= 5) {
					break;
				}
			}
		}

	}

}
