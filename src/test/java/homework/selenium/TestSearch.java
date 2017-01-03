package homework.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import homework.selenium.enums.BROWSER_TYPE;
import homework.selenium.factories.BrowserFactory;
import homework.selenium.page.objects.SearchPage;

/**
 * TODO TestNG class testing the login of http://books.tto.bg. Contains one positive
 * and two negative tests.
 */
@Test
public class TestSearch {
	private WebDriver driver;

	/**
	 * Before every test we start a web driver. Using factory design pattern to
	 * start different browser.
	 */
	@BeforeMethod
	public void setUp() {
		driver = BrowserFactory.getBrowser(BROWSER_TYPE.CHROME);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}

	/**
	 * After every test, we stop the web driver.
	 */
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	/**
	 * Positive test with correct search data using DataProvider.
	 */
	@Test(dataProvider = "correctSearch")
	public void testSearchWithCorrectData(String category, String name) {
		driver.get(SearchPage.URL);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

		SearchPage searchPage = PageFactory.initElements(driver, SearchPage.class);
		searchPage.search(category, name);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
		
		Assert.assertTrue(driver.findElement(By.id("Search_category_id")).getAttribute("value").equals("1"));
		searchPage.verifySearch(driver);
	}

	/**
	 * Negative tests with wrong credentials given using DataProvider. There are
	 * two tests: with invalid username and with empty fields.
	 */
	@Test(dataProvider = "incorrectSearch")
	public void testSearchWithIncorrectData(String category, String name) {
		driver.get(SearchPage.URL);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

		SearchPage searchPage = PageFactory.initElements(driver, SearchPage.class);
		searchPage.search(category, name);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

		searchPage.verifyErrorSearch(driver);
	}

	@DataProvider
	public Object[][] correctSearch() {
		return new Object[][] { 
			{ "Programming", "" }, 
			{ "Programming", "Beginning Active Server Pages 3.0" } 
		};
	}

	@DataProvider
	public Object[][] incorrectSearch() {
		return new Object[][] { 
			{ "", "admin" }, // wrong name of book
		};
	}
}