package homework.selenium.factories;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import homework.selenium.browsers.Browser;
import homework.selenium.browsers.ChromeBrowser;
import homework.selenium.browsers.FirefoxBrowser;
import homework.selenium.enums.BROWSER_TYPE;
import homework.selenium.exceptions.BrowserNotFoundException;

public class BrowserFactory implements Cloneable {
    private static Map<BROWSER_TYPE, Browser> browsersMap;

    static {
        initBrowsers();
    }

    private static void initBrowsers() {
        browsersMap = new HashMap<>();
        browsersMap.put(BROWSER_TYPE.FIREFOX, new FirefoxBrowser());
        browsersMap.put(BROWSER_TYPE.CHROME, new ChromeBrowser());
    }

    public static WebDriver getBrowser(BROWSER_TYPE type) {
        try {
            checkLoadedBrowsers(type);
        } catch (BrowserNotFoundException e) {
            handleBrowserNotFoundExcpetion(e);
        }
        return browsersMap.get(type).initDriver();
    }

    public static void tearDownBrowser(BROWSER_TYPE type) {
        browsersMap.remove(type);
    }

    private static void checkLoadedBrowsers(BROWSER_TYPE type) throws BrowserNotFoundException {
        if (!browsersMap.containsKey(type)) {
            throw new BrowserNotFoundException("No such browser type found in factory");
        }
    }

    private static void handleBrowserNotFoundExcpetion(Exception ex) {
        ex.printStackTrace();
    }
}