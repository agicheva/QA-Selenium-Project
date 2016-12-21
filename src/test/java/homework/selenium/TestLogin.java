package homework.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import homework.selenium.enums.BROWSER_TYPE;
import homework.selenium.factories.BrowserFactory;
import homework.selenium.page.objects.LoginPage;

/**
 * TestNG class testing the login of http://books.tto.bg. Contains one positive
 * and two negative tests.
 */
@Test
public class TestLogin {
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
	 * Positive test with correct credentials for existing user given using
	 * DataProvider with guest/guest and admin/admin.
	 */
	@Test(dataProvider = "correctCredentials")
	public void testLoginPageWithExistingUser(String username, String password) {
		driver.get(LoginPage.URL);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.register(username, password);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

		loginPage.verifyLogin(driver);
	}

	/**
	 * Negative tests with wrong credentials given using DataProvider. There are
	 * two tests: with invalid username and with empty fields.
	 */
	@Test(dataProvider = "incorrectCredentials")
	public void testLoginPageWithNonExistingUser(String username, String password) {
		driver.get(LoginPage.URL);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.register(username, password);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

		loginPage.verifyErrorLogin(driver);
		loginPage.verifyErrorLoginURL(driver);
	}

	@DataProvider
	public Object[][] correctCredentials() {
		return new Object[][] { 
			{ "admin", "admin" }, 
			{ "guest", "guest" } 
		};
	}

	@DataProvider
	public Object[][] incorrectCredentials() {
		return new Object[][] { 
			{ "admin", "" }, // empty password
			{ "", "admin" }, // empty username
			{ "invalid", "invalid" }, // invalid password and username
			{ "", "" } // empty fields
		};
	}
}