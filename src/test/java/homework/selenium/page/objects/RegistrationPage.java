package homework.selenium.page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Class for the registration page on http://books.tto.bg, implementation for
 * Page Object Design Pattern. Contains the page URL, the web elements needed to
 * registrate user and methods for asserting correct and incorrect registration.
 */
public class RegistrationPage {

	public static final String URL = "http://books.tto.bg/Registration.aspx";

	private static final String DEFAULT_PAGE = "http://books.tto.bg/Default.aspx?";

	private WebElement Reg_member_login;

	private WebElement Reg_member_password;

	private WebElement Reg_member_password2;

	private WebElement Reg_first_name;

	private WebElement Reg_last_name;

	private WebElement Reg_email;

	private WebElement Reg_address;

	private WebElement Reg_phone;

	private WebElement Reg_card_type_id;

	private WebElement Reg_card_number;

	private WebElement Reg_insert;

	private WebElement Reg_cancel;

	/**
	 * Method that sets the values of the web elements to make correct
	 * registration. The registration needs a first and last name, phone, email,
	 * password and to confirm the given password, etc.
	 */
	public void register(String Reg_member_login, String Reg_member_password, String Reg_member_password2,
			String Reg_first_name, String Reg_last_name, String Reg_email, String Reg_address, String Reg_phone,
			String Reg_card_type_id, String Reg_card_number, Boolean insert) {
		this.Reg_member_login.sendKeys(Reg_member_login);
		this.Reg_member_password.sendKeys(Reg_member_password);
		this.Reg_member_password2.sendKeys(Reg_member_password2);
		this.Reg_first_name.sendKeys(Reg_first_name);
		this.Reg_last_name.sendKeys(Reg_last_name);
		this.Reg_email.sendKeys(Reg_email);
		this.Reg_address.sendKeys(Reg_address);
		this.Reg_phone.sendKeys(Reg_phone);
		this.Reg_card_type_id.sendKeys(Reg_card_type_id);
		this.Reg_card_number.sendKeys(Reg_card_number);
		
		if (insert) {
			this.Reg_insert.click();
		} else {
			this.Reg_cancel.click();
		}
	}

	/**
	 * If the URL we are redirected to the main page, we are registrated successfully and can log in.
	 */
	public void verifyRegistertion(WebDriver driver) {
		Assert.assertTrue(driver.getCurrentUrl().equals(DEFAULT_PAGE));
	}

	/**
	 * If the error message is visible that means the registration went
	 * wrong and we are not logged in.
	 */
	public void verifyErrorRegisteration(WebDriver driver) {
		Assert.assertFalse(driver.findElement(By.id("Reg_ValidationSummary")).getText().isEmpty());
	}

	/**
	 * If the URL stays the same that means the registration went
	 * wrong and we are not logged in.
	 */
	public void verifyErrorRegistertionURL(WebDriver driver) {
		Assert.assertTrue(driver.getCurrentUrl().equals(RegistrationPage.URL));
	}

	/**
	 * When clicked Cancel we should be redirected to default page.
	 */
	public void verifyCancel(WebDriver driver) {
		Assert.assertTrue(driver.getCurrentUrl().equals(DEFAULT_PAGE));
	}
}
