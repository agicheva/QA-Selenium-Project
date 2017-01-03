package homework.selenium.page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Class for the search page on http://books.tto.bg, implementation for Page
 * Object Design Pattern. Contains the page URL, the web elements needed to
 * login and methods for asserting correct and incorrect login.
 */
public class SearchPage {

	public static final String URL = "http://books.tto.bg/";

	private WebElement Search_category_id;

	private WebElement Search_name;

	private WebElement Search_search_button;

	/**
	 * Method that sets the values of the web elements to make correct search.
	 * The search needs a category and name.
	 *
	 * @param category
	 *            The category is one of the options from the dropdown.
	 * @param name
	 *            The name is the name of the searched book.
	 */
	public void search(String Search_category_id, String Search_name) {
		this.Search_category_id.sendKeys(Search_category_id);
		this.Search_name.sendKeys(Search_name);

		this.Search_search_button.click();
	}

	/**
	 * If the URL we are redirected to is
	 * "http://books.tto.bg/Books.aspx?" that means
	 * the search is successful.
	 */
	public void verifySearch(WebDriver driver) {
		Assert.assertTrue(driver.getCurrentUrl().equals("http://books.tto.bg/Books.aspx?"));
	}

	/**
	 * If the error message is visible that means the search is wrong.
	 */
	public void verifyErrorSearch(WebDriver driver) {
		Assert.assertTrue(
				driver.findElement(By.id("Results_no_records")).getText().equals("No records"));
	}
}