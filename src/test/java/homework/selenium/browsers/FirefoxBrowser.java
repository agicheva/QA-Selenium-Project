package homework.selenium.browsers;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class FirefoxBrowser implements Browser {
    private final String PATH_TO_BINARY = "firefoxdriver.exe";

    private FirefoxBinary firefoxBinary;

    public FirefoxBrowser() {
        File firefoxFile = new File(PATH_TO_BINARY);
        firefoxBinary = new FirefoxBinary(firefoxFile);
    }

    public WebDriver initDriver() {
        return new FirefoxDriver(this.firefoxBinary, new FirefoxProfile());
    }
}
