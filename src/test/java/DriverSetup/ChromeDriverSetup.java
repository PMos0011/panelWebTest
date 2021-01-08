package DriverSetup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeDriverSetup {

    private WebDriver webDriver;
    private WebDriverWait webDriverWait;

    public ChromeDriverSetup(String appUrl) {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriverWait = new WebDriverWait(webDriver, 2);
        webDriver.get(appUrl);
        webDriver.manage().window().maximize();
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }
}
