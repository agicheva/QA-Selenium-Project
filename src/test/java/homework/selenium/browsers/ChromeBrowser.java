package homework.selenium.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeBrowser implements Browser {
    private final String PROPERTY_NAME = "webdriver.chrome.driver";
    private final String PATH_TO_BINARY = "chromedriver.exe";

    public ChromeBrowser() {
        System.setProperty(PROPERTY_NAME, PATH_TO_BINARY);
    }

    public WebDriver initDriver() {
        return new ChromeDriver();
    }
}