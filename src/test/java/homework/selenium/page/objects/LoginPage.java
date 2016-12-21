package homework.selenium.page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Class for the login page on http://books.tto.bg, implementation for Page
 * Object Design Pattern. Contains the page URL, the web elements needed to
 * login and methods for asserting correct and incorrect login.
 */
public class LoginPage {

	public static final String URL = "http://books.tto.bg/Login.aspx";

	private WebElement Login_name;

	private WebElement Login_password;

	private WebElement Login_login;

	/**
	 * Method that sets the values of the web elements to make correct login.
	 * The login needs a username in this case is the email of the user and
	 * correct password.
	 *
	 * @param username
	 *            The email of the user.
	 * @param password
	 *            The password of the user.
	 */
	public void register(String Login_name, String Login_password) {
		this.Login_name.sendKeys(Login_name);
		this.Login_password.sendKeys(Login_password);

		this.Login_login.click();
	}

	/**
	 * If the URL we are redirected to is
	 * "http://books.tto.bg/ShoppingCart.aspx?" that means we are in the user's
	 * account page and we are logged in successfully.
	 */
	public void verifyLogin(WebDriver driver) {
		Assert.assertTrue(driver.getCurrentUrl().equals("http://books.tto.bg/ShoppingCart.aspx?"));
	}

	/**
	 * If the error message is visible that means we are not logged in.
	 */
	public void verifyErrorLogin(WebDriver driver) {
		Assert.assertTrue(
				driver.findElement(By.id("Login_message")).getText().equals("Login or Password is incorrect."));
	}

	/**
	 * If the URL stays the same that means we are
	 * not logged in.
	 */
	public void verifyErrorLoginURL(WebDriver driver) {
		Assert.assertTrue(driver.getCurrentUrl().equals(LoginPage.URL));
	}
}