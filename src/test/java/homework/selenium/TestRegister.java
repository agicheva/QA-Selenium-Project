package homework.selenium;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import homework.selenium.enums.BROWSER_TYPE;
import homework.selenium.factories.BrowserFactory;
import homework.selenium.page.objects.RegistrationPage;

/**
 * TestNG class testing the registration of http://books.tto.bg/. Contains one
 * positive and three negative tests.
 */
@Test
public class TestRegister {
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
	 * Positive test with correct credentials given using DataProvider. Every
	 * time time the email is different, generated with random numbers.
	 */
	@Test(dataProvider = "correctCredentials")
	public void testRegistrationPageWithCorrectCredentials(String Reg_member_login, String Reg_member_password,
			String Reg_member_password2, String Reg_first_name, String Reg_last_name, String Reg_email,
			String Reg_address, String Reg_phone, String Reg_card_type_id, String Reg_card_number, Boolean insert) {
		driver.get(RegistrationPage.URL);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

		RegistrationPage registrationPage = PageFactory.initElements(driver, RegistrationPage.class);
		registrationPage.register(Reg_member_login, Reg_member_password, Reg_member_password2, Reg_first_name,
				Reg_last_name, Reg_email, Reg_address, Reg_phone, Reg_card_type_id, Reg_card_number, insert);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
		registrationPage.verifyRegistertion(driver);
	}

	/**
	 * Negative tests with wrong credentials given using DataProvider. There are
	 * three tests: with invalid email, with incorrect confirm password and with
	 * empty fields.
	 */
	@Test(dataProvider = "incorrectCredentials")
	public void testRegistrationPageWithIncorrectCredentials(String Reg_member_login, String Reg_member_password,
			String Reg_member_password2, String Reg_first_name, String Reg_last_name, String Reg_email,
			String Reg_address, String Reg_phone, String Reg_card_type_id, String Reg_card_number, Boolean insert) {
		driver.get(RegistrationPage.URL);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

		RegistrationPage registrationPage = PageFactory.initElements(driver, RegistrationPage.class);
		registrationPage.register(Reg_member_login, Reg_member_password, Reg_member_password2, Reg_first_name,
				Reg_last_name, Reg_email, Reg_address, Reg_phone, Reg_card_type_id, Reg_card_number, insert);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
		registrationPage.verifyErrorRegisteration(driver);
		registrationPage.verifyErrorRegistertionURL(driver);
	}
	
	/**
	 * Positive test when clicked canceld button.
	 */
	@Test(dataProvider = "correctCredentialsForCancel")
	public void testRegistrationPageWihenClickedCancel(String Reg_member_login, String Reg_member_password,
			String Reg_member_password2, String Reg_first_name, String Reg_last_name, String Reg_email,
			String Reg_address, String Reg_phone, String Reg_card_type_id, String Reg_card_number, Boolean insert) {
		driver.get(RegistrationPage.URL);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

		RegistrationPage registrationPage = PageFactory.initElements(driver, RegistrationPage.class);
		registrationPage.register(Reg_member_login, Reg_member_password, Reg_member_password2, Reg_first_name,
				Reg_last_name, Reg_email, Reg_address, Reg_phone, Reg_card_type_id, Reg_card_number, insert);
		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

		driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
		registrationPage.verifyCancel(driver);
	}

	@DataProvider
	public Object[][] correctCredentials() {
		return new Object[][] {
				{ generateUsername(), "password", "password", "first", "last", generateUsername() + "@abv.bg", "address", "0888112233", 
					"American Express", "4532807611719060", true }, // full registration
				{ generateUsername(), "password", "password", "first", "last", generateUsername() + "@abv.bg", "", "", "", "", true } // only mandatory fields
		};
	}

	@DataProvider
	public Object[][] incorrectCredentials() {
		return new Object[][] { 
			    { "username", "password", "password2", "first", "last", "username@abv.bg", "", "", "", "", true  }, // incorrect confirm password
				{ "", "password", "password2", "first", "last", "username@abv.bg", "", "", "", "", true  }, // empty username
				{ "username", "", "password", "first", "last", "username@abv.bg", "", "", "", "", true  }, // empty password
				{ "username", "password", "", "first", "last", "username@abv.bg", "", "", "", "", true  }, // empty confirm password
				{ "username", "password", "password", "", "last", "username@abv.bg", "", "", "", "", true  }, // empty first name
				{ "username", "password", "password", "first", "", "username@abv.bg", "", "", "", "", true  }, // empty last name
				{ "username", "password", "password", "first", "last", "", "", "", "", "", true  }, // empty email
				{ "", "", "", "", "", "", "", "", "", "", true  }, // all fields empty
				{ "admin", "password", "password", "first", "last", "username@abv.bg", "", "", "", "", true  }, // exsiting user
				{ "username", "password", "password", "first", "last", "username@abv", "", "", "", "", true  }, // invalid email without .com, .bg
				{ "username", "password", "password", "first", "last", "username", "", "", "", "", true  }, // invalid email without @
				{ generateUsername(), "password", "password", "first", "last", generateUsername() + "@abv.bg", "address", "0888112233", 
					"American Express", "4539620972105198", true }, // selected american express, visa card number
				{ generateUsername(), "password", "password", "first", "last", generateUsername() + "@abv.bg", "address", "0888112233", 
						"Visa", "4532807611719060", true } // selected visa, american express card number
		};
	}
	
	@DataProvider
	public Object[][] correctCredentialsForCancel() {
		return new Object[][] {
			{ "", "", "", "", "", "", "", "", "", "", false  }
		};
	}

	/**
	 * Generates different usernames staring with "login_username_12" and finishing with
	 * random number. This is done so that every time we run the tests we don't
	 * register the same user because the tests won't pass after the second run.
	 *
	 * @return The generated email as String.
	 */
	private String generateUsername() {
		return "login_username_12" + generateRandonNumber();
	}

	/**
	 * Generates random number from 1 to 99. Used to generate different emails.
	 *
	 * @return Random integer.
	 */
	private int generateRandonNumber() {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(100);
	}
}
